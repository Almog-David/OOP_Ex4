import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    public static void main() {
        Client C = new Client();
        GraphAlgo g = new GraphAlgo();
        //---- first we need to create the settings of the frame ----//
        JFrame frame = new JFrame(); // creates a frame
        frame.setTitle("Ex4 - Pokemon Game"); // sets title of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        frame.setSize(600, 600); // sets the x and y dimension of the frame
        frame.setLayout(new BorderLayout());
        frame.setVisible(true); // make frame visible
        frame.getContentPane().setBackground(Color.white); // change the color of the background
        g.load(C.getGraph());
        Panel p = new Panel(g.getGraph());


        // if we would like to change the icon of the program - use lines 20-21
        ImageIcon image = new ImageIcon("name of image"); // create an ImageIcon
        frame.setIconImage(image.getImage()); // change icon of frame

        // ---- now we would like to create the objects on the frame ---- //
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setPreferredSize(new Dimension(50,50));
        JLabel score = new JLabel("Score:"); // create a label and set the text
        //score.setHorizontalTextPosition(JLabel.LEFT); // set the x position of the label Left,center,right
        //score.setVerticalTextPosition(JLabel.CENTER); // set the y position of the label top,center,bottom
        score.setForeground(Color.darkGray); // sets the color of the label
        score.setFont(new Font("Ariel", Font.PLAIN, 20)); // sets font of text
        score.setBounds(15, 150, 350, 350); // set x, y position within frame as well as dimension

        JLabel time = new JLabel("Time:"); // create a label and set the text
        //time.setHorizontalTextPosition(JLabel.LEFT); // set the x position of the label Left,center,right
        //time.setVerticalTextPosition(JLabel.CENTER); // set the y position of the label top,center,bottom
        time.setForeground(Color.darkGray); // sets the color of the label
        time.setFont(new Font("Ariel", Font.PLAIN, 20)); // sets font of text
        time.setBounds(15, 300, 350, 350); // set x, y position within frame as well as dimension

        JButton stop = new JButton("Stop Game"); // create a new button
        stop.setBounds(100, 100, 200, 100); // set the bounds of the button
        stop.setBackground(Color.red);
        stop.setForeground(Color.black);
        stop.setFont(new Font("Ariel", Font.PLAIN,15));
        stop.setBorder(BorderFactory.createEtchedBorder());
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                C.stop();
                stop.setEnabled(false);
            }
        });

        panel.add(stop);
        panel.add(time);
        panel.add(score);
        frame.add(panel,BorderLayout.NORTH);

    }
}
