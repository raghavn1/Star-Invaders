import java.awt.CardLayout;
import javax.swing.*;

public class MainPanel extends JPanel{
    private  CardLayout card = new CardLayout();
    private MenuScreen menu = new MenuScreen();
    private SkinPanel skins = new SkinPanel();
    private TopDownShooter gamescreen = new TopDownShooter();
    private GameOverScreen gameOver = gamescreen.getGameOverScreen();
    private Controller controller = new Controller(this, menu, gameOver, gamescreen,skins);


    public MainPanel(){
        this.setLayout(card);



        this.add(menu, "Menu");
        this.add(gamescreen, "GAME");
        this.add(skins, "SKINS");
        //this.add( gameOver, "GameOver");
        this.showMenu();
      


    }
     
   public void showMenu(){
     card.show(this, "Menu");
        
   }

   public void showskins(){
    card.show(this, "SKINS");
   }
   public void showGame(){
     card.show(this, "GAME");
     gamescreen.getGameOverScreen().setVisible(false);
     gamescreen.timer.start();
     gamescreen.initGame();

   }

  
}