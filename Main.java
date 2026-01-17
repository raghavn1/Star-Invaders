import javax.swing.JFrame;

public class Main {
     public static final int WIDTH = 800;
  public static final int HEIGHT = 600;
  public static void main(String[] args) throws Exception {

     
        JFrame frame = new JFrame("Star Invaders");
        MainPanel main = new MainPanel();
       
        frame.setContentPane(main);
        frame.setSize(WIDTH,HEIGHT);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }  

}
