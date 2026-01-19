//Class for the game over screen shown when the player loses
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;

public class GameOverScreen extends JPanel{

    //The layout and components for the game over screen
    private SpringLayout layout = new SpringLayout(); //The layout manager used
    private JButton toMenu = new JButton("Back to Menu"); //Button to go back to the main menu
    private ImageIcon gameOverImg = new ImageIcon(getClass().getResource("gameOver.png")); //Image that says "Game Over"
    private JLabel GameOver = new JLabel(gameOverImg); //Label to hold and display the game over image
    private JLabel score = new JLabel("Score: "); //Label to show the player's final score


    public GameOverScreen(){
   
        this.setLayout(layout); //Setting the layout to spring layout
        this.setBackground(new Color(44, 95, 52)); //Setting the background color to dark green
        this.setPreferredSize(new Dimension(900, 800)); //Setting the size of the screen


        //Setting the position for each button and label using coordinates
        layout.putConstraint(SpringLayout.WEST, toMenu, 345, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, toMenu, 350, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, GameOver,310, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, GameOver, 200, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, score,410, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, score, 300, SpringLayout.NORTH, this);

        
        //Setting the colors, fonts, and hover effects for the buttons and text
        Font customFont = new Font("Comic Sans", Font.BOLD, 50);
        GameOver.setFont(customFont);
        GameOver.setForeground(Color.WHITE);
        toMenu.setForeground(Color.WHITE);
        score.setForeground(Color.WHITE);
        toMenu.setPreferredSize(new Dimension(200,75));
        
        //Creating borders for the button hover effect
        Border normal = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border hover = BorderFactory.createLineBorder(Color.YELLOW, 5);
        toMenu.setBorder(normal);
        toMenu.setRolloverEnabled(true);
        
        //Logic to change the button border when the mouse hovers over it
        toMenu.getModel().addChangeListener(e -> {
            if (toMenu.getModel().isRollover()) {
                toMenu.setBorder(hover);
            } else {
            toMenu.setBorder(normal);
            }
        });

        //Adding the components to the panel so they appear on screen
        this.add(toMenu);
        this.add(GameOver);
        this.add(score);
    }
        
    //Getter method to access the menu button from other classes
    public JButton getMenuButton(){
        return toMenu;
    }
    
    //Method to update the score text with the final score
    public void scoreText(String s){
        score.setText(s);
    }
}