/**
 * @author AchiyaZigi
 * A trivial example for starting the server and running all needed commands
 */
import java.io.IOException;
import java.util.*;

public class StudentCode {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String graphStr = client.getGraph();
        System.out.println(graphStr); // we need to upload the string graph in order to create a new graph
        GraphAlgo graph = new GraphAlgo(); // i added in order to save the graph.
        graph.load(graphStr);
        client.addAgent("{\"id\":0}");
        String agentsStr = client.getAgents();
        System.out.println(agentsStr); // we need to upload the string of the agents and save it in some kind of list
        LinkedList <Agent> agents = Agent.load(agentsStr); // i added in order to save the agents. array because we don't add an agent in the middle
        String pokemonsStr = client.getPokemons();
        System.out.println(pokemonsStr); // the same in here
        Queue<Pokemon> pokemons = new PriorityQueue<>((v1, v2) -> (int) (v1.getValue() - v2.getValue()));
        pokemons = Pokemon.load(pokemonsStr); // add the pokemons to a queue because every time we need to add and remove the pokemon if we reach to him
        String isRunningStr = client.isRunning();
        System.out.println(isRunningStr);
        // BEFORE THE START WE NEED TO ALLOCATE THE AGENTS IN A POSITION


        client.start();

        while (client.isRunning().equals("true")) { // we need to update this
            client.move();
            System.out.println(client.getAgents());
            System.out.println(client.timeToEnd());

            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter the next dest: ");
            int next = keyboard.nextInt();
            client.chooseNextEdge("{\"agent_id\":0, \"next_node_id\": " + next + "}");

        }


    }

    public void CatchPokemon(GraphAlgo graph, LinkedList<Agent> agents,Queue<Pokemon> pokemons){
        Queue path = new LinkedList();
        double min = Double.MAX_VALUE;
        int AgentID = 0;
        for (Pokemon p :pokemons){
            int[] P_pos = p.findEdge(graph.getGraph());
            if(p.isCaptured()==false){
                for(Agent a : agents){
                    if (a.getSource()==-1){
                       List<Integer> l = graph.shortestPath(a.getSource(),P_pos[0]);
                       double temp = graph.calculateLength(l)/a.getSpeed();
                       if(temp<min){
                           min = temp;
                           AgentID = a.getId();
                           path = l;
                       }
                    }
                }
                path = path.add(p_pos[1]);
                agents.get(AgentID).setPath(path);
                p.setCaptured(true);
            }
        }
    }




}
