//Class has all the other panels on this in a card layout, and switches between them
import java.awt.CardLayout;
import javax.swing.*;

public class MainPanel extends JPanel{

    private CardLayout card = new CardLayout();
    private MenuScreen menu = new MenuScreen();
    private SkinPanel skins = new SkinPanel();
    private TopDownShooter gamescreen = new TopDownShooter();
    private GameOverScreen gameOver = gamescreen.getGameOverScreen();
    
  
    private Controller controller = new Controller(this, menu, gameOver, gamescreen,skins);


    //Constructor to set up the card layout and add each screen to the panel
    public MainPanel(){
        this.setLayout(card);

        //Adding each screen to the "deck" with a unique name
        this.add(menu, "Menu");
        this.add(gamescreen, "GAME");
        this.add(skins, "SKINS");
        
        //Start the program by showing the main menu
        this.showMenu();
    }
     
   //Show the menu screen
   public void showMenu(){
     card.show(this, "Menu");
   }

   //Show the skin selection screen
   public void showskins(){
    card.show(this, "SKINS");
   }
   
   //Show the game screen and start the game timer
   public void showGame(){
     card.show(this, "GAME");
     
     //Hide any old game over messages and reset the game logic
     gamescreen.getGameOverScreen().setVisible(false);
     gamescreen.timer.start();
     gamescreen.initGame();
   }
}