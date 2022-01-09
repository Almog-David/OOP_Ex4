/**
 * @author AchiyaZigi
 * A trivial example for starting the server and running all needed commands
 */
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.*;

public class StudentCode {
    public static void main(String[] args) throws ParseException {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // we get the graph from the client and upload it to a Graph object //
        GraphAlgo graph = new GraphAlgo(); // i added in order to save the graph.
        graph.load(client.getGraph());

        // we get the number of agents from the client and create a new agents according to the information //
        String info = client.getInfo();
        org.json.JSONObject o = new org.json.JSONObject(info);
        org.json.JSONObject ob = o.getJSONObject("GameServer");
        int numofagents = ob.getInt("agents");

        int center = graph.center();
        if(numofagents==1){
            client.addAgent("{\"id\":" + center + "}");
        }
        else {
            int locate = graph.getGraph().getNodes().size()/numofagents;
            for (int i = 0; i < numofagents; i++) { // before the first run we would like to put all the agents in the center.
                int where = (i+1)*locate;
                client.addAgent("{\"id\":" + where + "}");
            }
        }

        HashMap<Integer, Agent> agents = Agent.load(client.getAgents());

        // we get the number of pokemons from the client and create a new agents according to the information //
        Queue<Pokemon> pokemons = new PriorityQueue<Pokemon>((v1, v2) -> (int) (v1.getValue() - v2.getValue()));
        pokemons = Pokemon.load(client.getPokemons()); // add the pokemons to a queue because every time we need to add and remove the pokemon if we reach to him

        client.start();
        GUI gameGUI = new GUI(graph,agents, pokemons,client); // the first time we draw everything.
        while (client.isRunning().equals("true")) {
            agents = Agent.load(client.getAgents());
            pokemons = Pokemon.load(client.getPokemons());
            gameGUI.updateGame(agents, pokemons,client);
            LinkedList<Integer> path = new LinkedList();
            double min = Double.MAX_VALUE;
            int AgentID = -1;
            Iterator<Pokemon> pokemonIterator = pokemons.iterator();
            while (pokemonIterator.hasNext()){
                Pokemon p = pokemonIterator.next();
                int[] P_pos = p.findEdge(graph.getGraph());
                Iterator<HashMap.Entry<Integer, Agent>> A = agents.entrySet().iterator();
                while (A.hasNext()) {
                    HashMap.Entry<Integer, Agent> v = A.next();
                    int agentkey = v.getKey();
                    if (!agents.get(agentkey).isTag() && agents.get(agentkey).getDest() == -1) {
                        List<Integer> l = graph.shortestPath(v.getValue().getSource(), P_pos[0]); // do the shortest path on it
                        double temp = graph.calculateLength(l) / v.getValue().getSpeed(); // calculate the amount o time it will take him to execute the catch
                        if (temp < min) {
                            min = temp;
                            AgentID = v.getValue().getId();
                            path = (LinkedList<Integer>) l;
                        }
                    }
                }
                if (AgentID != -1) {
                    if(path.size()>1) {
                        client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + path.get(1) + "}");
                        agents.get(AgentID).setTag(true);
                    }
                    else if(path.size()==1 && agents.get(AgentID).getSource()==  P_pos[0]) {
                        client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + P_pos[1] + "}");
                        agents.get(AgentID).setTag(true);
                    }

                }

            }
            System.out.println(client.getAgents());

            MyThread thread = new MyThread();
            thread.run();
            try {
                thread.sleep(100);
            } catch(InterruptedException e) {
                if(true);
            }

            client.move();
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run(){
        return;
    }
}