
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private MainPanel mainPanel; 
    private  MenuScreen menu;
    private GameOverScreen gameover;
    private TopDownShooter gamescreen;
    

    public Controller(MainPanel mainPanel, MenuScreen menu, GameOverScreen gameover, TopDownShooter gamescreen){
        this.mainPanel = mainPanel;
        this.menu = menu;
        this.gameover = gameover;
        this.gamescreen = gamescreen;

        menu.getPlayButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showGame();
               
                
            }
        });

         gameover.getMenuButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showMenu();

            }
        });
        

    }

}
