import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

public class Panel extends JLabel {
    HashMap<Integer, Agent> agents; // a list of all the agents
    Queue<Pokemon> pokemons; // the queue of all the pokemons
    int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
    int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
    double min_x = Double.MAX_VALUE;
    double min_y = Double.MAX_VALUE;
    double max_x = Double.MIN_VALUE;
    double max_y = Double.MIN_VALUE;
    Graph g = new Graph();
    Client client;

    public Panel() {
    }

    public Panel(Graph graph) {
        Graph g = new Graph(graph);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        setValues(g);
        this.setSize(WIDTH, HEIGHT);
        setVisible(true);
        repaint();
    }


    public void paint(Graphics graphics) { // send to all the draw functions at once.
        graphics.clearRect(0, 0, WIDTH, HEIGHT); // repainting the screen
        setValues(g); // define the limits of the graph
        super.paintComponent(graphics);
        int _width = WIDTH;
        int _height = HEIGHT;
        drawGraph(graphics, _width, _height);
        drawAgents(graphics);
        drawpokemon(graphics);
        drawTimer(graphics);
        drawScore(graphics);
        setVisible(true);
    }

    public void drawGraph(Graphics graphics, int width, int height) {
        Iterator<Node> iterator_node = g.getNodes().values().iterator();

        double xAbs = Math.abs(min_x - max_x);
        double yAbs = Math.abs(min_y - max_y);
        double xScale = (width / xAbs);
        double yScale = (height / yAbs);

        while (iterator_node.hasNext()) {
            //adjusting the graph to the screen scale
            Node e = iterator_node.next();
            double x = (e.getLocation().x - min_x);
            double y = (e.getLocation().y - min_y);
            int X = (int) (x * xScale);
            int Y = (int) (y * yScale);

            graphics.setColor(new Color(20,150,100));
            drawNode(graphics, X + 20, Y + 20, e.getId());

            Iterator<Integer> src_edge = g.getOut_edges().keySet().iterator();
            while (src_edge.hasNext()) {
                int src = src_edge.next();
                Iterator<Integer> dest_edge = g.getOut_edges().get(src).keySet().iterator();
                while (dest_edge.hasNext()) {
                    int dest = dest_edge.next();
                    double destx = (g.getNode(dest).getLocation().x - min_x);
                    double desty = (g.getNode(dest).getLocation().y - min_y);
                    int destX = (int) (destx * xScale);
                    int destY = (int) (desty * yScale);
//                    int x1 = (int) (x * xScale) + 20;
//                    int x2 = (int) (destx * xScale) + 20;
//                    int y1 = (int) (y * yScale) + 20;
//                    int y2 = (int) (desty * yScale) + 20;
//                    double theta = Math.atan2(y2 - y1, x2 - x1); // for the arrow
                    graphics.setColor(Color.MAGENTA);
                    drawEdge(graphics, (int) X + 20, (int) Y + 20, (int) destX + 20, (int) destY + 20);
                    Graphics2D g = (Graphics2D) graphics;
                    setVisible(true);
                }
            }

        }
    }

    //function that help to find the min and max values of x & y in order to set the scale
    public void setValues(Graph g) {
        ;
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;

        Iterator<Node> Iterator = g.getNodes().values().iterator();
        while ((Iterator.hasNext())) {
            Node temp = Iterator.next();
            if (maxX < temp.getLocation().x)
                maxX = temp.getLocation().x;
            if (maxY < temp.getLocation().y)
                maxY = temp.getLocation().y;
            if (minX > temp.getLocation().x)
                minX = temp.getLocation().x;
            if (minY > temp.getLocation().y)
                minY = temp.getLocation().y;
        }
    }

    public void drawNode(Graphics graphics, int x, int y, int key) {
        graphics.fillOval(x - 5, y - 5, 10, 10);
        graphics.setColor(new Color(20, 150, 100));
        graphics.drawString("" + key, x, y + 15);
    }

    public void drawEdge(Graphics graphics, int xsrc, int ysrc, int xdest, int ydest) {
        graphics.drawLine(xsrc, ysrc, xdest, ydest);
    }

    public void drawAgents(Graphics g) {
        agents = Agent.load(client.getAgents());
        Iterator<HashMap.Entry<Integer, Agent>> A = agents.entrySet().iterator();
        while (A.hasNext()) {
            HashMap.Entry<Integer, Agent> v = A.next();
            Agent a = v.getValue();
            int ax = (int) a.getPos().x;
            int ay = (int) a.getPos().y;
            g.fillOval(ax - 4, ay - 4, 10, 10);
            g.setColor(new Color(180, 250, 250));
            g.drawString("" + a.getId(), ax, ay + 15);
            repaint();
        }
    }

    public void drawpokemon(Graphics g) {
        pokemons = Pokemon.load(client.getPokemons());
        Iterator<Pokemon> itr = pokemons.iterator();
        while (itr.hasNext()) {
            Pokemon p = itr.next();
            int px = (int) p.getPos().x;
            int py = (int) p.getPos().y;
            g.fillOval(px - 4, py - 4, 10, 10);
            g.setColor(new Color(200, 200, 0));
            repaint();
        }
    }

    private void drawTimer(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        int time = Integer.parseInt(client.timeToEnd()) / 1000;
        g.drawString("Time: " + time, 40, 60);
    }

    //draw table of scores
    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        String info = client.getInfo();
        org.json.JSONObject o = new org.json.JSONObject(info);
        org.json.JSONObject ob = o.getJSONObject("GameServer");
        int score = ob.getInt("agents");
        g.drawString("Score: " + score, 40, 80);
    }
}

