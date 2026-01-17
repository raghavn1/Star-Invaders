
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private MainPanel mainPanel; 
    private  MenuScreen menu;
    private GameOverScreen gameover;
    private TopDownShooter gamescreen;
    private SkinPanel skinPanel;
    

    public Controller(MainPanel mainPanel, MenuScreen menu, GameOverScreen gameover, TopDownShooter gamescreen, SkinPanel skinPanel){
        this.mainPanel = mainPanel;
        this.menu = menu;
        this.gameover = gameover;
        this.gamescreen = gamescreen;
        this.skinPanel = skinPanel;

        menu.getPlayButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showGame();
               
                
            }
        });

        skinPanel.getexitButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showMenu();
               
                
            }
        });

        skinPanel.getskin1().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               gamescreen.setSkin("/playerSprite.png");
               
                
            }
        });

        skinPanel.getskin2().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               gamescreen.setSkin("/zombieSprite.png");
               
                
            }
        });
        

        menu.getSkinButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showskins();
               
                
            }
        });

         gameover.getMenuButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainPanel.showMenu();
                menu.updateHighScoreDisplay();

            }
        });
        

    }

}