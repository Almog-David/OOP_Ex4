//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.geom.Line2D;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Queue;
//
//public class Frame {
//
//    public static void main(String[] args) {
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
//
//
//        //}
//
//        // ---- creating the graph from our information ----//
//
//
//        Iterator<Node> vers; // iterator for all the nodes on the graph,
//        LinkedList<Agent> agents; // a list of all the agents
//        Queue<Pokemon> pokemons; // the queue of all the pokemons
//        Iterator<Map.Entry<Integer, Double>> edges; // the iterator for all the out edges of a giving node
//        double scale_x;
//        double scale_y;
//        double min_x = Double.MAX_VALUE;
//        double min_y = Double.MAX_VALUE;
//        double max_x = Double.MIN_VALUE;
//        double max_y = Double.MIN_VALUE;
//        GraphAlgo g = new GraphAlgo();
//
//
//        @Override
//        protected void paintComponent (Graphics l){
//            super.paintComponent(l);
//            scale_x = 0;
//            scale_y = 0;
//            min_x = Double.MAX_VALUE;
//            min_y = Double.MAX_VALUE;
//            max_x = Double.MIN_VALUE;
//            max_y = Double.MIN_VALUE;
//            Iterator<Node> vers = g.getGraph().nodeIter();
//            while (vers.hasNext()) {
//                Vertex counterhelper = (Vertex) vers.next();
//                if (counterhelper.getLocation().x() < min_x) {
//                    min_x = counterhelper.getLocation().x();
//                } else if (counterhelper.getLocation().x() > max_x) {
//                    max_x = counterhelper.getLocation().x();
//                }
//                if (counterhelper.getLocation().y() < min_y) {
//                    min_y = counterhelper.getLocation().y();
//                } else if (counterhelper.getLocation().y() > max_y) {
//                    max_y = counterhelper.getLocation().y();
//                }
//
//            }
//            scale_x = max_x - min_x;
//            scale_y = max_y - min_y;
//            scale_x = width / scale_x;
//            scale_x = scale_x * 0.9;
//            scale_y = height / scale_y;
//            scale_y = scale_y * 0.8;
//
//            Iterator<NodeData> nodeItr = g.getGraph().nodeIter();
//            while (nodeItr.hasNext()) {
//                Vertex n = (Vertex) nodeItr.next();
//                Iterator<EdgeData> itr = g.getGraph().edgeIter(n.getKey());
//                while (itr.hasNext()) {
//                    Edge e = (Edge) itr.next();
//                    Vertex vs = (Vertex) g.getGraph().getNode(e.getSrc());
//                    Vertex vd = (Vertex) g.getGraph().getNode(e.getDest());
//                    double destx = vd.getLocation().x();
//                    destx = ((destx - min_x) * scale_x) + 10;
//                    double desty = vd.getLocation().y();
//                    desty = ((desty - min_y) * scale_y) + 10;
//                    double srcx = vs.getLocation().x();
//                    srcx = ((srcx - min_x) * scale_x) + 10;
//                    double srcy = vs.getLocation().y();
//                    srcy = ((srcy - min_y) * scale_y) + 10;
//
//                    l.setColor(Color.BLACK);
//                    Arrow arrow = new Arrow((int) srcx, (int) srcy, (int) destx, (int) desty, Color.black, 1);
//
//
//                }
//            }
//        }
//    }
//}
//
//    // OLD GUI
////
////
////
////import api.EdgeData;
////import api.NodeData;
////
////import javax.swing.*;
////import java.awt.*;
////import java.awt.geom.Line2D;
////import java.util.ArrayList;
////import java.util.Iterator;
////import java.util.LinkedList;
////
////            public class myPanel extends JPanel {
////
////                Graph graph;
////                GraphAlgorithms graphAlgorithms;
////                private int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
////                private int HEIGHT =(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
////                private double maxX;
////                private double maxY;
////                private double minX;
////                private double minY;
////
////                //for the menubar actions
////                NodeData centerNode;
////                boolean center=false;
////
////
////
////                LinkedList<NodeData> shortestPath;
////                int shortestPathStart;
////                int shortestPathEnd;
////                boolean sp = false;
////
//////    public myPanel(){}
////
////                public myPanel(Graph g){
////                    graphAlgorithms = new GraphAlgorithms();
////                    graphAlgorithms.init(g);
////                    centerNode = graphAlgorithms.center();
////                    shortestPath = null;
////                    this.setLayout(new BorderLayout());
////                    this.setBackground(Color.WHITE);
////                    setValues(graphAlgorithms);
////                    this.setSize(WIDTH,HEIGHT);
////                    setVisible(true);
////                    repaint();
////                }
////
////
////                public void setShortestPathStart(int shortestPathStart) {
////                    this.shortestPathStart = shortestPathStart;
////                }
////
////                public void setShortestPathEnd(int shortestPathEnd) {
////                    this.shortestPathEnd = shortestPathEnd;
////                }
////                public void setShortestPath(LinkedList<NodeData> shortestPath) {
////                    this.shortestPath = shortestPath;
////                }
////
////
////
////
////                @Override
////                protected void paintComponent(Graphics graphics){
////                    graphics.clearRect(0,0,WIDTH, HEIGHT); // repainting the screen
////                    setValues(graphAlgorithms);
////                    super.paintComponent(graphics);
////                    int _width = WIDTH;
////                    int _height= HEIGHT;
////                    drawGraph(graphics, _width, _height);
////                    setVisible(true);
////                }
////
////                public void drawGraph(Graphics graphics, int width, int height) {
////                    graph = (Graph) graphAlgorithms.getGraph();
////                    Iterator<NodeData> iterator_node = graph.nodeIter();
////
////                    double xAbs = Math.abs(minX - maxX);
////                    double yAbs = Math.abs(minY - maxY);
////                    double xScale = (width / xAbs);
////                    double yScale = (height / yAbs);
////
////                    while (iterator_node.hasNext()) {
////                        //adjusting the graph to the screen scale
////                        NodeData e = iterator_node.next();
////                        double x = (e.getLocation().x() - minX);
////                        double y = (e.getLocation().y() - minY);
////                        int X = (int) (x * xScale);
////                        int Y = (int) (y * yScale);
////
////                        graphics.setColor(Color.PINK);
////                        drawNode(graphics, X + 20, Y + 20, e.getKey());
//////            setVisible(true);
////                        Iterator<EdgeData> iterator_edge = graph.edgeIter(e.getKey());
////                        if(iterator_edge == null)
////                            continue;
////                        while (iterator_edge.hasNext()) {
////                            EdgeData edgeData = iterator_edge.next();
////                            double destx = (graph.getNode(edgeData.getDest()).getLocation().x() - minX);
////                            double desty = (graph.getNode(edgeData.getDest()).getLocation().y() - minY);
////                            int destX = (int) (destx * xScale);
////                            int destY = (int) (desty * yScale);
////                            int x1 = (int) (x * xScale) + 20;
////                            int x2 = (int) (destx * xScale) + 20;
////                            int y1 = (int) (y * yScale) + 20;
////                            int y2 = (int) (desty * yScale) + 20;
////                            double theta = Math.atan2(y2 - y1, x2 - x1); // for the arrow
////                            graphics.setColor(Color.MAGENTA);
////                            drawEdge(graphics, (int) X + 20, (int) Y + 20, (int) destX + 20, (int) destY + 20);
////                            Graphics2D g = (Graphics2D) graphics;
////                            drawArrowLine(g, theta, x2, y2);
////                            setVisible(true);
////                        }
////
////                    }
////                    if (center == true) {
////                        centerNode = graphAlgorithms.center();
////                        double x1 = (centerNode.getLocation().x() - minX) * xScale + 20;
////                        double y1 = (centerNode.getLocation().y() - minY) * yScale + 20;
////                        graphics.setColor(new Color(7, 79, 163));
////                        graphics.fillOval((int) x1 - 4, (int) y1 - 4, 10, 10);
////                        int k = centerNode.getKey();
////                        graphics.drawString("This is the center, ID: " + k, (int) x1 - 50, (int) y1 + 30);
////                        center = false;
////                    }
////
////                    if (sp == true && shortestPath != null) {
////                        for (int i = 0; i < shortestPath.size() - 1; i++) {
////                            EdgeData temp = graphAlgorithms.getGraph().getEdge(shortestPath.get(i).getKey(), shortestPath.get(i + 1).getKey());
////                            double xSrc = (graphAlgorithms.getGraph().getNode(temp.getSrc()).getLocation().x() - minX) * xScale;
////                            double ySrc = (graphAlgorithms.getGraph().getNode(temp.getSrc()).getLocation().y() - minY) * yScale;
////                            double xDest = (graphAlgorithms.getGraph().getNode(temp.getDest()).getLocation().x() - minX) * xScale;
////                            double yDest = (graphAlgorithms.getGraph().getNode(temp.getDest()).getLocation().y() - minY) * yScale;
////                            graphics.setColor(new Color(12, 193, 178));
////                            Font f = new Font("ariel", Font.BOLD, 7);
////                            graphics.setFont(f);
////                            graphics.drawLine((int) xSrc + 20, (int) ySrc + 20, (int) xDest + 20, (int) yDest + 20);
////                            graphics.drawString(temp.getWeight()+"", (int)((xSrc+xDest)/2 - 10), (int)((ySrc+yDest)/2 ));
////                            setVisible(true);
////                        }
////                        sp = false;
////                    }
////                }
////
////                //function that help to find the min and max values of x & y in order to set the scale
////                public void setValues(GraphAlgorithms g) {
////                    this.graphAlgorithms=g;
////                    maxX = Integer.MIN_VALUE;
////                    maxY = Integer.MIN_VALUE;
////                    minX = Integer.MAX_VALUE;
////                    minY = Integer.MAX_VALUE;
////
////                    Iterator<NodeData> Iterator = this.graphAlgorithms.getGraph().nodeIter();
////                    while ((Iterator.hasNext())) {
////                        NodeData temp = Iterator.next();
////                        if (maxX < temp.getLocation().x())
////                            maxX = temp.getLocation().x();
////                        if (maxY < temp.getLocation().y())
////                            maxY = temp.getLocation().y();
////                        if (minX > temp.getLocation().x())
////                            minX = temp.getLocation().x();
////                        if (minY > temp.getLocation().y())
////                            minY = temp.getLocation().y();
////                    }
////                }
////
////                public void drawNode(Graphics graphics, int x, int y, int key) {
////                    graphics.fillOval(x-4,y-4,10,10);
////                    graphics.setColor(new Color(134, 28, 81));
////                    graphics.drawString(""+key,x,y+15);
////                }
////
////                public void drawEdge(Graphics graphics,int xsrc, int ysrc, int xdest, int ydest){
////                    graphics.drawLine(xsrc, ysrc, xdest, ydest);
////                }
////
////                // took from https://coderanch.com/t/339505/java/drawing-arrows
////                public void drawArrowLine(Graphics2D g, double theta, double x2, double y2){
////                    int arrow = 15;
////                    double phi = Math.PI / 10;
////                    double x = x2 - arrow * Math.cos(theta + phi);
////                    double y = y2 - arrow * Math.sin(theta + phi);
////                    g.setColor(new Color(210, 36, 180));
////                    g.draw(new Line2D.Double(x2, y2, x, y));
////                    x = x2 - arrow * Math.cos(theta - phi);
////                    y = y2 - arrow * Math.sin(theta - phi);
////                    g.draw(new Line2D.Double(x2, y2, x, y));
////                }
////            }
