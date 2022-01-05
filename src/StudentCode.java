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
        LinkedList<Agent> agents = new LinkedList<>();
        agents = Agent.load(agentsStr);


        // we get the number of pokemons from the client and create a new agents according to the information //
        String pokemonsStr = client.getPokemons();
        System.out.println(pokemonsStr); // the same in here
        Queue<Pokemon> pokemons = new PriorityQueue<Pokemon>((v1, v2) -> (int) (v1.getValue() - v2.getValue()));
        pokemons = Pokemon.load(pokemonsStr); // add the pokemons to a queue because every time we need to add and remove the pokemon if we reach to him
        String isRunningStr = client.isRunning();
        //System.out.println(isRunningStr);

        client.start();

        while (client.isRunning().equals("true")) {
            //graph.load(client.getGraph());
            agents = Agent.load(agentsStr);
            pokemons = Pokemon.load(pokemonsStr);
            CatchPokemon(graph, agents, pokemons);
            for (int i = 0; i < agents.size(); i++) {
                Agent a = agents.get(i);
                if (a.getPath().size() > 0) {
                    if (a.getDest() == -1) {
                        a.getPath().remove();
                        System.out.println(a.getPath());
                        client.chooseNextEdge("{\"agent_id\":" + a.getId() + ", \"next_node_id\": " + a.getPath().peek() + "}");
                        client.move();
//                        System.out.println("{\"agent_id\":" + a.getId() + ", \"next_node_id\": " + a.getPath().peek() + "}");
                    }
                }
            }
            client.move();
//            System.out.println(client.getAgents());
//            System.out.println(client.timeToEnd());


        }
    }


    public static void CatchPokemon(GraphAlgo graph, LinkedList<Agent> agents,Queue<Pokemon> pokemons){
        LinkedList path = new LinkedList();
        double min = Double.MAX_VALUE;
        int AgentID = -1;
        for (Pokemon p :pokemons){
            int[] P_pos = p.findEdge(graph.getGraph());
            if(!p.isCaptured()) {
                for (Agent a : agents) {
                    if (a.getDest() == -1 && a.getPath().size() == 0) { // if the agents isn't moving, and he doesn't have a path to go to
                        List<Integer> l = graph.shortestPath(a.getSource(), P_pos[0]); // do the shortest path on it
                        double temp = graph.calculateLength(l) / a.getSpeed(); // calculate the amount o time it will take him to execute the catch
                        if (temp < min) {
                            min = temp;
                            AgentID = a.getId();
                            path = (LinkedList) l;
                        }
                    }
                }
                if (AgentID != -1) {
                    path.add(P_pos[1]);// add the last node of the edge to the path in order to fully go on the edge of the pokemon.
                    agents.get(AgentID).setPath(path);
                    p.setCaptured(true);
                }
            }
        }
    }
}
