import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Frame extends JLabel{
    LinkedList<Agent> agents; // a list of all the agents
    Queue<Pokemon> pokemons; // the queue of all the pokemons
    int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
    int HEIGHT =(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
    double min_x = Double.MAX_VALUE;
    double min_y = Double.MAX_VALUE;
    double max_x = Double.MIN_VALUE;
    double max_y = Double.MIN_VALUE;
    Graph g = new Graph();
    Client client;

    public Frame(){}

    public Frame(Graph graph){
        Graph g = new Graph(graph);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        setValues(g);
        this.setSize(WIDTH,HEIGHT);
        setVisible(true);
        repaint();
    }


    protected void paintComponent(Graphics graphics){
        graphics.clearRect(0,0,WIDTH, HEIGHT); // repainting the screen
        setValues(g);
        super.paintComponent(graphics);
        int _width = WIDTH;
        int _height= HEIGHT;
        drawGraph(graphics, _width, _height);
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

            graphics.setColor(Color.PINK);
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
                    int x1 = (int) (x * xScale) + 20;
                    int x2 = (int) (destx * xScale) + 20;
                    int y1 = (int) (y * yScale) + 20;
                    int y2 = (int) (desty * yScale) + 20;
                    double theta = Math.atan2(y2 - y1, x2 - x1); // for the arrow
                    graphics.setColor(Color.MAGENTA);
                    drawEdge(graphics, (int) X + 20, (int) Y + 20, (int) destX + 20, (int) destY + 20);
                    Graphics2D g = (Graphics2D) graphics;
                    drawArrowLine(g, theta, x2, y2);
                    setVisible(true);
                }
            }

        }
    }

    //function that help to find the min and max values of x & y in order to set the scale
    public void setValues(Graph g) {;
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

    public void drawGame(Graphics graphics){
        pokemons = Pokemon.load(client.getPokemons());
        Iterator<Pokemon> itr = pokemons.iterator();
        while (itr.hasNext()){
            Pokemon p = itr.next();
            int x = (int) p.getPos().x;
            int y = (int) p.getPos().y;
            drawpokemon(graphics,x,y);
        }
        agents = Agent.load(client.getAgents());
        Iterator<Agent> agn = agents.iterator();
        while (agn.hasNext()){
            Agent a = agn.next();
            int x = (int) a.getPos().x;
            int y = (int) a.getPos().y;
            drawAgents(graphics,x,y,a.getId());
        }
    }

    public void drawNode(Graphics graphics, int x, int y, int key) {
        graphics.fillOval(x-4,y-4,10,10);
        graphics.setColor(new Color(134, 28, 81));
        graphics.drawString(""+key,x,y+15);
    }

    public void drawEdge(Graphics graphics,int xsrc, int ysrc, int xdest, int ydest){
        graphics.drawLine(xsrc, ysrc, xdest, ydest);
    }

    // took from https://coderanch.com/t/339505/java/drawing-arrows
    public void drawArrowLine(Graphics2D g, double theta, double x2, double y2){
        int arrow = 15;
        double phi = Math.PI / 10;
        double x = x2 - arrow * Math.cos(theta + phi);
        double y = y2 - arrow * Math.sin(theta + phi);
        g.setColor(new Color(210, 36, 180));
        g.draw(new Line2D.Double(x2, y2, x, y));
        x = x2 - arrow * Math.cos(theta - phi);
        y = y2 - arrow * Math.sin(theta - phi);
        g.draw(new Line2D.Double(x2, y2, x, y));
    }



    public void drawAgents(Graphics g, int x, int y, int id){
        g.fillOval(x-4,y-4,10,10);
        g.setColor(new Color(180, 250, 250));
        g.drawString(""+id,x,y+15);
    }

    public void drawpokemon(Graphics g, int x, int y){
        g.fillOval(x-4,y-4,10,10);
        g.setColor(new Color(200, 200, 0));
    }
}


//
//        //---- first we need to create the settings of the frame ----//
//        JFrame frame = new JFrame(); // creates a frame
//        frame.setTitle("Ex4 - Pokemon Game"); // sets title of frame
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
//        frame.setSize(600, 600); // sets the x and y dimension of the frame
//        frame.setLayout(new BorderLayout());
//        frame.setVisible(true); // make frame visible
//        frame.getContentPane().setBackground(Color.white); // change the color of the background
//
//        // if we would like to change the icon of the program - use lines 20-21
//        ImageIcon image = new ImageIcon("name of image"); // create an ImageIcon
//        frame.setIconImage(image.getImage()); // change icon of frame
//
//        // ---- now we would like to create the objects on the frame ---- //
//
//        JLabel score = new JLabel("Score:"); // create a label and set the text
//        //score.setHorizontalTextPosition(JLabel.LEFT); // set the x position of the label Left,center,right
//        //score.setVerticalTextPosition(JLabel.CENTER); // set the y position of the label top,center,bottom
//        score.setForeground(Color.darkGray); // sets the color of the label
//        score.setFont(new Font("Ariel", Font.PLAIN, 20)); // sets font of text
//        score.setBounds(15, 150, 350, 350); // set x, y position within frame as well as dimension
//
//        JLabel time = new JLabel("Time:"); // create a label and set the text
//        //time.setHorizontalTextPosition(JLabel.LEFT); // set the x position of the label Left,center,right
//        //time.setVerticalTextPosition(JLabel.CENTER); // set the y position of the label top,center,bottom
//        time.setForeground(Color.darkGray); // sets the color of the label
//        time.setFont(new Font("Ariel", Font.PLAIN, 20)); // sets font of text
//        time.setBounds(15, 300, 350, 350); // set x, y position within frame as well as dimension
//
//        JButton stop = new JButton(); // create a new button
//        stop.setBounds(100, 100, 200, 100); // set the bounds of the button
//        //stop.addActionListener();

//        // ---- creating the graph from our information ----//