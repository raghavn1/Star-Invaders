//Class to start the game and create the main window
import javax.swing.JFrame;

public class Main {
  //The width and height dimensions for the game window
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  //The main starting point for the whole program
  public static void main(String[] args) throws Exception {

        //Create the window frame and set the title to "Star Invaders"
        JFrame frame = new JFrame("Star Invaders");
        
        MainPanel main = new MainPanel();
        frame.setContentPane(main);
        frame.setSize(WIDTH,HEIGHT);
        frame.pack();
        
        //Make the window appear in the middle
        frame.setLocationRelativeTo(null);
        
        //Make sure the program stops running when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Make the window visible so the player can see it
        frame.setVisible(true);
        
        //Request focus so key inputs are detected
        frame.requestFocusInWindow();
    }  
}