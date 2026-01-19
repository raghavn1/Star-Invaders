//Class for the main menu screen where players can start the game or change skins
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

public class MenuScreen extends JPanel{
    //Layout and all the buttons and text labels on the menu
    private SpringLayout layout = new SpringLayout();
    private JButton playButton = new JButton("PLAY");
    private ImageIcon titleImg = new ImageIcon(getClass().getResource("gameTitle.png"));
    private JLabel title = new JLabel(titleImg);
    private JButton SkinButton = new JButton("SKINS");
    private JLabel playerscore = new JLabel("Score: ");


    public MenuScreen(){
      
        //Update the score label from the text file
        updateHighScoreDisplay();
     
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(800,600));

        //Setting where the logo, score, and buttons sit on the screen
        layout.putConstraint(SpringLayout.WEST, title,220 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, title, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, playerscore,260 , SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, playerscore, 200, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, playButton,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, playButton, 300, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, SkinButton,300, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.NORTH, SkinButton, 450, SpringLayout.NORTH, this);

       
        //Formatting the play button and adding a yellow border when hovering
        playButton.setPreferredSize(new Dimension(200,75));
        Border normal = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border hover = BorderFactory.createLineBorder(Color.YELLOW, 5);
        playButton.setBorder(normal);
        playButton.setRolloverEnabled(true);
        playButton.getModel().addChangeListener(e -> {
            if (playButton.getModel().isRollover()) {
                playButton.setBorder(hover);
            } else {
            playButton.setBorder(normal);
            }
        }); 

        //Formatting the skin button and adding a yellow border when hovering
        SkinButton.setPreferredSize(new Dimension(200,75));
        SkinButton.setBorder(normal);
        SkinButton.setRolloverEnabled(true); //Allow it to check if the mouse is over it
        SkinButton.getModel().addChangeListener(e -> {
            if (SkinButton.getModel().isRollover()) { //If it is over, add our hover border, if its not keep it normal
                SkinButton.setBorder(hover);
            } else {
            SkinButton.setBorder(normal);
            }
        }); 

        //Setting the font style and colors for the text and background
        playerscore.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        playerscore.setForeground(Color.WHITE);
        this.setBackground(new Color(44, 95, 52));

        //Adding everything to the panel so it shows up on screen
        this.add(title);
        this.add(playButton);
        this.add(playerscore);
        this.add(SkinButton);
    }

    //Method to read the high score from the Score.txt file
    public void updateHighScoreDisplay() {
        //Look for the text file where the score is saved
        File scoreFile = new File("Score.txt");
        int highScore = 0;

        try {
            //Only try to read if the file exists
            if (scoreFile.exists()) {
                //Use a scanner to look inside the file
                Scanner scanner = new Scanner(scoreFile);
                
                //Check if there is an int inside, and save it as the high score
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                //Close 
                scanner.close();
            }
        } catch (Exception e) {
            //Error catch in case something goes wrong reading the file
            System.out.println("Could not read High Score: " + e.getMessage());
        }
        
        //Update the label on the screen to show the number we found
        playerscore.setText("High Score: " + highScore);
    }

    //Getter method to give the Controller access to the play button
    public JButton getPlayButton(){
        return  playButton;
    }

    //Getter method to give the Controller access to the skin button
     public JButton getSkinButton(){
        return  SkinButton;
    }
}