import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GUI extends JPanel {

    private GraphAlgo graph;
    private LinkedList<Pokemon> pokemons;
    private HashMap<Integer, Agent> agents;
    private Client client;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    JFrame frame;
    JPanel menu;
    JButton stop;
    JLabel time;
    JLabel score;
    int currtime;
    int currscore;


    public GUI(GraphAlgo g, HashMap<Integer, Agent> a, LinkedList<Pokemon> p, Client c) {
        this.graph = g;
        this.pokemons = p;
        this.agents = a;
        this.client = c;
        setValues();
        frame = new JFrame();
        frame.setTitle("Ex4 - Pokemon Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit the app
        frame.setPreferredSize(new Dimension(600,600));
        this.setBounds(0,100,frame.getWidth()-50,frame.getHeight());
        frame.getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
            }
        });
        frame.add(this,BorderLayout.CENTER);

        this.createMenu();
        frame.repaint();
        frame.setVisible(true); //make frame visible
        frame.setLayout(new FlowLayout());//controls the size of things that we are adding(button)
        frame.getContentPane().setBackground(Color.white);//change the color of background
        frame.pack();
    }

    public void createMenu(){ // creates the panel that saves all the information for the user

        menu = new JPanel();
        menu.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        menu.setPreferredSize(new Dimension(2000,50));
        menu.setVisible(true);
        time = new JLabel();
        time.setForeground(Color.black);
        time.setBounds(5,20,400,40);
        time.setVisible(true);
        score = new JLabel();
        score.setForeground(Color.black);
        score.setBounds(300,20,350,40);
        score.setVisible(true);
        currtime = this.gettime();
        currscore = this.getscore();
        time.setText("Countdown :"+currtime);
        time.setFont(new Font("Ariel",Font.BOLD,20));
        score.setText("Score: "+currscore);
        score.setFont(new Font("Ariel",Font.BOLD,20));
        menu.add(score);
        menu.add(time);
        this.stopButton();
        frame.add(menu,BorderLayout.NORTH);
    }

    public void stopButton(){
        stop = new JButton("Stop");
        stop.addActionListener(e -> client.stop());
        stop.setSize(20,20);
        stop.setForeground(null);
        stop.setBackground(Color.red);
        stop.setFocusable(false);
        stop.setFont(new Font("Ariel",Font.PLAIN,20));
        stop.setBounds(800,20,20,20);
        menu.add(stop,BorderLayout.AFTER_LINE_ENDS);
    }

    public int gettime(){
        int time = Integer.parseInt(client.timeToEnd()) / 1000;
        return time;
    }

    public int getscore(){
        String info = client.getInfo();
        org.json.JSONObject o = new org.json.JSONObject(info);
        org.json.JSONObject ob = o.getJSONObject("GameServer");
        int score = ob.getInt("grade");
        return score;
    }

    private void setValues() {

        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;

        Iterator<Node> Iterator = graph.getGraph().getNodes().values().iterator();
        while ((Iterator.hasNext())) {
            Node n = Iterator.next();
            minX = Math.min(n.getLocation().getX(), minX);
            minY = Math.min(n.getLocation().getY(), minY);
            maxX = Math.max(n.getLocation().getX(), maxX);
            maxY = Math.max(n.getLocation().getY(), maxY);
        }
    }

    private int getXScale(Location pos) {
        return (int) ((((pos.getX() - minX) / (maxX - minX)) * this.getWidth() * 0.9) + (0.05 * this.getWidth()));
    }

    private int getYScale(Location pos) {
        return (int) ((((pos.getY() - minY) * (this.getHeight() - 100) / (maxY - minY)) * 0.9) + (0.05 * (this.getHeight() - 100)));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0,0,this.getWidth(),this.getHeight()); // repainting the screen
        setValues(); // define the limits of the graph

        //---------------- draw the nodes ----------------//
        Iterator<Node> iterator_node = graph.getGraph().getNodes().values().iterator();
        while (iterator_node.hasNext()) {
            Node n = iterator_node.next();
            g.setColor(new Color(20, 150, 100));
            g.fillOval(getXScale(n.getLocation()), getYScale(n.getLocation()), 15, 15);
            g.drawString(n.getId() + "", getXScale(n.getLocation()), getYScale(n.getLocation()));
        }
        //---------------- draw the edges ----------------//
        Iterator<Integer> src_edge = graph.getGraph().getOut_edges().keySet().iterator();
        while (src_edge.hasNext()) {
            int src = src_edge.next();
            Iterator<Integer> dest_edge = graph.getGraph().getOut_edges().get(src).keySet().iterator();
            while (dest_edge.hasNext()) {
                int dest = dest_edge.next();
                Node s = graph.getGraph().getNode(src);
                Node d = graph.getGraph().getNode(dest);
                g.setColor(Color.BLACK);
                g.drawLine(getXScale(s.getLocation()) + 8, getYScale(s.getLocation()) + 8, getXScale(d.getLocation()) + 8, getYScale(d.getLocation()) + 8);
            }
        }
        //---------------- draw the pokemons ----------------//
        for(int p=0; p<pokemons.size();p++){
             Pokemon pokemon = pokemons.get(p);
            if (pokemon.getType() > 0) {
                g.setColor(new Color(200, 200, 0));
                Toolkit t=Toolkit.getDefaultToolkit();
                Image i=t.getImage("src/Pictures/jigglypuff.png");
                g.drawImage(i,getXScale(pokemon.getPos()), getYScale(pokemon.getPos()), 20, 20,this);
            } else {
                g.setColor(new Color(200, 80, 0));
                Toolkit t=Toolkit.getDefaultToolkit();
                Image i=t.getImage("src/Pictures/pikachu.png");
                g.drawImage(i,getXScale(pokemon.getPos()), getYScale(pokemon.getPos()), 20, 20,this);

            }
        }
        //---------------- draw the agents ----------------//
        Iterator<HashMap.Entry<Integer, Agent>> A = agents.entrySet().iterator();
        while (A.hasNext()) {
            HashMap.Entry<Integer, Agent> v = A.next();
            Agent a = v.getValue();
            g.setColor(new Color(200, 0, 0));
            Toolkit t=Toolkit.getDefaultToolkit();
            Image i=t.getImage("src/Pictures/Pok??_Ball.png");
            g.drawImage(i,getXScale(a.getPos()), getYScale(a.getPos()), 20, 20,this);
        }
    }

    public void updateGame(HashMap<Integer, Agent> a, LinkedList<Pokemon> p, Client c) {
        this.pokemons = p;
        this.agents = a;
        this.client = c;
        //currtime = this.gettime();
        //currscore = this.getscore();
        this.time.setText("Countdown :"+this.gettime());
        this.time.paint(this.time.getGraphics());
        this.score.setText("Score: "+this.getscore());
        this.score.paint(this.score.getGraphics());


        this.setBounds(0,100,frame.getWidth(),frame.getHeight()-135);
        repaint();
    }
}


