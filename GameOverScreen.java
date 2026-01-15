
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

public class GameOverScreen extends JPanel{

    private SpringLayout layout = new SpringLayout();// springlayout as the game over pag elayout
    private JButton toMenu = new JButton("Back to Menu");// button that leads back to menu
    private JLabel GameOver = new JLabel("Game Over");// label to show game over to player
    private JLabel score = new JLabel("Score: ");// displays the players score after playing


    public GameOverScreen(){
        this.setLayout(layout);// setting this panels layout as spring layout
        this.setBackground(Color.BLACK);// setting the background as black
        this.setPreferredSize(new Dimension(800,700));


        // displaying th ebuttons and lables with spring layout constraints
        layout.putConstraint(SpringLayout.WEST, toMenu, 300, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, toMenu, 350, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, GameOver,300, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, GameOver, 200, SpringLayout.NORTH, this);
        
        layout.putConstraint(SpringLayout.WEST, score,300, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, score, 300, SpringLayout.NORTH, this);

        
        // customizing texts and buttons
        Font customFont = new Font("Comic Sans", Font.BOLD, 50);
        GameOver.setFont(customFont);
        GameOver.setForeground(Color.WHITE);
        score.setForeground(Color.WHITE);
        toMenu.setPreferredSize(new Dimension(200,75));

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
