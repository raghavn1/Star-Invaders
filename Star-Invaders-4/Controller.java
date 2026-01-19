//Class to handle button clicks and switch between different screens
import java.awt.event.*;

public class Controller {

    //References to all the different panels and screens in the game
    private MainPanel mainPanel; 
    private MenuScreen menu;
    private GameOverScreen gameover;
    private TopDownShooter gamescreen;
    private SkinPanel skinPanel;
    

    //Constructor to link all the screens together and setup what the buttons do
    public Controller(MainPanel mainPanel, MenuScreen menu, GameOverScreen gameover, TopDownShooter gamescreen, SkinPanel skinPanel){
        this.mainPanel = mainPanel;
        this.menu = menu;
        this.gameover = gameover;
        this.gamescreen = gamescreen;
        this.skinPanel = skinPanel;

        //When the play button is clicked, it tells the main panel to show the game
        menu.getPlayButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showGame();
            }
        });

        //When the exit button in the skin panel is clicked, it goes back to the main menu
        skinPanel.getexitButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showMenu();
            }
        });

        //When skin 1 is selected, it sets that skin
        skinPanel.getskin1().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               gamescreen.setSkin("/player1Sprite.png");
            }
        });

        //When skin 2 is selected, it sets that skin
        skinPanel.getskin2().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               gamescreen.setSkin("/player2Sprite.png");
            }
        });
        

        //When the skin button is clicked on the menu, it opens the skin selection screen
        menu.getSkinButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showskins();
            }
        });

        //When the menu button is clicked after losing, it goes back to menu and updates the high score
         gameover.getMenuButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showMenu();
                menu.updateHighScoreDisplay();
            }
        });
    }
}