
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class MenuScreen extends JPanel{
    private SpringLayout layout = new SpringLayout();// menu panel has a spring layout
    private JButton playButton = new JButton("PLAY");// creates a play button 
    private ImageIcon titleImg = new ImageIcon(getClass().getResource("gameTitle.png"));//Importing the title image
    private JLabel title = new JLabel(titleImg);// title of the game 
    private JButton SkinButton = new JButton("SKINS");
    private JLabel playerscore = new JLabel("Score: ");// stores the players highscore

    

   
    
    

    public MenuScreen(){
      

        updateHighScoreDisplay();
     
        this.setLayout(layout);// setting the layout
        this.setPreferredSize(new Dimension(800,700));
        //formatting everry element using the spring layout contraints

        layout.putConstraint(SpringLayout.WEST, title,220 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, title, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, playerscore,250 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, playerscore, 200, SpringLayout.NORTH, this);


        layout.putConstraint(SpringLayout.WEST, playButton,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, playButton, 300, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, SkinButton,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, SkinButton, 450, SpringLayout.NORTH, this);

       
        //changing size of the play button
        playButton.setPreferredSize(new Dimension(200,75));
        playButton.setBackground(Color.pink.darker());
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        playButton.setText("PLAY");

        SkinButton.setPreferredSize(new Dimension(200,75));
        SkinButton.setBackground(Color.pink.darker());
        SkinButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        SkinButton.setText("SKINS");

        
        playerscore.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));

        playerscore.setForeground(Color.BLACK);

        this.setBackground(Color.RED);

        //adding every element to the panel
        this.add(title);
        this.add(playButton);
        this.add(playerscore);
        this.add(SkinButton);

    }

    public void updateHighScoreDisplay() {
        File scoreFile = new File("Score.txt");
        int highScore = 0;

        try {
            if (scoreFile.exists()) {
                Scanner scanner = new Scanner(scoreFile);
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Could not read High Score: " + e.getMessage());
        }

        playerscore.setText("High Score: " + highScore);
    }

    public JButton getPlayButton(){
        return  playButton;
    }

     public JButton getSkinButton(){
        return  SkinButton;
    }
    


}