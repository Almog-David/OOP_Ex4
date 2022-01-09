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

        // we get the number of pokemons from the client and create a new agents according to the information //
        LinkedList<Pokemon> pokemons = new LinkedList<>();
        pokemons = Pokemon.load(client.getPokemons()); // add the pokemons to a list in order to go on them from the beginning

        // we get the number of agents from the client and create a new agents according to the information //
        String info = client.getInfo();
        org.json.JSONObject o = new org.json.JSONObject(info);
        org.json.JSONObject ob = o.getJSONObject("GameServer");
        int numofagents = ob.getInt("agents");
        int numofpokemons = ob.getInt("pokemons");

        int center = graph.center();
        for(int i=0; i<numofagents;i++) {
            if (i <= numofpokemons) {
                int[] P_pos = pokemons.get(i).findEdge(graph.getGraph());
                client.addAgent("{\"id\":" + P_pos[0] + "}");
            } else {
                client.addAgent("{\"id\":" + center + "}");
            }
        }

        HashMap<Integer,Agent> agents = Agent.load(client.getAgents());

        client.start();
        GUI gameGUI = new GUI(graph,agents, pokemons,client); // the first time we draw everything.
        while (client.isRunning().equals("true")) {
            agents = Agent.load(client.getAgents());
            pokemons = Pokemon.load(client.getPokemons());
            gameGUI.updateGame(agents, pokemons,client);
            LinkedList<Integer> path = new LinkedList();
            double min = Double.MAX_VALUE;
            int [] P_pos;
            int [] where = new int[2];
            int pokeID = -1;
            int AgentID = -1;
            Iterator<HashMap.Entry<Integer, Agent>> A = agents.entrySet().iterator();
            while (A.hasNext()) {
                HashMap.Entry<Integer, Agent> v = A.next();
                for (int i = 0; i < pokemons.size(); i++) {
                    P_pos = pokemons.get(i).findEdge(graph.getGraph());
                    if (v.getValue().getDest() == -1 && !pokemons.get(i).isCaptured()) {
                        List<Integer> l = graph.shortestPath(v.getValue().getSource(), P_pos[0]); // do the shortest path on it
                        double temp = graph.calculateLength(l) / v.getValue().getSpeed(); // calculate the amount o time it will take him to execute the catch
                        if (temp < min) {
                            min = temp;
                            pokeID = i;
                            where = P_pos;
                            AgentID = v.getKey();
                            path = (LinkedList<Integer>) l;
                        }
                    }
                }
            }
            if (AgentID != -1) {
                if(path.size()>1) {
                    client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + path.get(1) + "}");
                    pokemons.get(pokeID).setCaptured(true);
                }
                else if(path.size()==1 && agents.get(AgentID).getSource()==  where[0]) {
                    client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + where[1] + "}");
                    pokemons.get(pokeID).setCaptured(true);
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