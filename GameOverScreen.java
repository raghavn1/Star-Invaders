
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;

public class GameOverScreen extends JPanel{

    private SpringLayout layout = new SpringLayout();// springlayout as the game over pag elayout
    private JButton toMenu = new JButton("Back to Menu");// button that leads back to menu
    private ImageIcon gameOverImg = new ImageIcon(getClass().getResource("gameOver.png"));
    private JLabel GameOver = new JLabel(gameOverImg);// label to show game over to player
    private JLabel score = new JLabel("Score: ");// displays the players score after playing


    public GameOverScreen(){
   
        this.setLayout(layout);// setting this panels layout as spring layout
        this.setBackground(new Color(44, 95, 52));// setting the background as blue
        this.setPreferredSize(new Dimension(900, 800));


        // displaying th ebuttons and lables with spring layout constraints
        layout.putConstraint(SpringLayout.WEST, toMenu, 345, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, toMenu, 350, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, GameOver,310, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, GameOver, 200, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, score,410, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, score, 300, SpringLayout.NORTH, this);

        
        // customizing texts and buttons
        Font customFont = new Font("Comic Sans", Font.BOLD, 50);
        GameOver.setFont(customFont);
        GameOver.setForeground(Color.WHITE);
        toMenu.setForeground(Color.WHITE);
        score.setForeground(Color.WHITE);
        toMenu.setPreferredSize(new Dimension(200,75));
        Border normal = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border hover = BorderFactory.createLineBorder(Color.YELLOW, 5);
        toMenu.setBorder(normal);
        toMenu.setRolloverEnabled(true);
        toMenu.getModel().addChangeListener(e -> {
            if (toMenu.getModel().isRollover()) {
                toMenu.setBorder(hover);
            } else {
            toMenu.setBorder(normal);
            }
        });

        //adding everything to the Screen
        this.add(toMenu);
        this.add(GameOver);
        this.add(score);
    }
        
    //getting the back to menu button
    public JButton getMenuButton(){
        return toMenu;
    }
    //setting the score label text
    public void scoreText(String s){
        score.setText(s);
    }



}