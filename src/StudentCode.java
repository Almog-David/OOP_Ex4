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
        // we get the graph from the client and upload it to an Graph object //
        String graphStr = client.getGraph();
        //System.out.println(graphStr); // we need to upload the string graph in order to create a new graph
        GraphAlgo graph = new GraphAlgo(); // i added in order to save the graph.
        graph.load(client.getGraph());

        // we get the number of agents from the client and create a new agents according to the information //
        String info = client.getInfo();
        org.json.JSONObject o = new org.json.JSONObject(info);
        org.json.JSONObject ob = o.getJSONObject("GameServer");
        int numofagents = ob.getInt("agents");

        int center = graph.center();
        for (int i = 0; i < numofagents; i++) { // before the first run we would like to put all the agents in the center.
            client.addAgent("{\"id\":" + center + "}");
        }

        String agentsStr = client.getAgents();
        System.out.println(agentsStr);
        HashMap<Integer, Agent> agents = Agent.load(agentsStr);

        // we get the number of pokemons from the client and create a new agents according to the information //
        String pokemonsStr = client.getPokemons();
        System.out.println(pokemonsStr); // the same in here
        Queue<Pokemon> pokemons = new PriorityQueue<Pokemon>((v1, v2) -> (int) (v1.getValue() - v2.getValue()));
        pokemons = Pokemon.load(pokemonsStr); // add the pokemons to a queue because every time we need to add and remove the pokemon if we reach to him
        String isRunningStr = client.isRunning();

        client.start();

        while (client.isRunning().equals("true")) {
            agentsStr = client.getAgents();
            agents = Agent.load(agentsStr);
            pokemonsStr = client.getPokemons();
            pokemons = Pokemon.load(pokemonsStr);
            LinkedList path = new LinkedList();
            double min = Double.MAX_VALUE;
            int AgentID = -1;
            for (Pokemon p : pokemons) { // we will go through the poekmons we got to see which agents is best for every one.
                int[] P_pos = p.findEdge(graph.getGraph());
                Iterator<HashMap.Entry<Integer, Agent>> A = agents.entrySet().iterator();
                while (A.hasNext()) {
                    HashMap.Entry<Integer, Agent> v = A.next();
                    if (v.getValue().isTag() == false && v.getValue().getDest() == -1) {
                        List<Integer> l = graph.shortestPath(v.getValue().getSource(), P_pos[0]); // do the shortest path on it
                        double temp = graph.calculateLength(l) / v.getValue().getSpeed(); // calculate the amount o time it will take him to execute the catch
                        if (temp < min) {
                            min = temp;
                            AgentID = v.getValue().getId();
                            path = (LinkedList) l;
                        }
                    }
                }

                if (AgentID != -1) {
                    if(path.size()>1)
                        client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + path.get(1) + "}");
                    else if(path.size()==1 && agents.get(AgentID).getSource()==  P_pos[0] )
                        client.chooseNextEdge("{\"agent_id\":" + AgentID + ", \"next_node_id\": " + P_pos[1] + "}");

                    //System.out.println(agentsStr);
                    agents.get(AgentID).setTag(true);
                }

            }
            client.move();
            info = client.getInfo();
            o = new org.json.JSONObject(info);
            ob = o.getJSONObject("GameServer");
            int value = ob.getInt("grade");
            int moves = ob.getInt("moves");
            System.out.println(""+ value +" , "+ moves + "");
        }
    }
}
