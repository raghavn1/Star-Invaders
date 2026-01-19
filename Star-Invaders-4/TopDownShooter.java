//Main game class that handles all the movement, and logic
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class TopDownShooter extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    //Game settings like timer, score, ammo, and movement keys
    public Timer timer;
    private boolean isGameOver = false;
    private int score = 0;
    private boolean wPressed, aPressed, sPressed, dPressed, fpressed;
    private int mouseX, mouseY;
    private int ammo = 30;
    private boolean shootable = true;

    //Objects for the player, enemies, bullets, and items
    private Player player;
    private Image playerImg;
    private ArrayList<Zombie> zombies;
    private Image zombieImg;
    private Image AmmoImg;
    private ArrayList<Bullet> bullets;
    private ArrayList<Ammunition> ammunitions;
    private Random random;
    private GameOverScreen gameOverScreen;
    private String SkinId = "/player1Sprite.png";

    //Sound effects for the game
    public Sound backgroundSong;
    private Sound shootSound;
    private Sound zombieDeathSound;
    private Sound ammoPickupSound;
    private Image backgroundImg;

    //Constructor to set up the game window and load sounds
    public TopDownShooter() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        
        //Adding listeners for keyboard and mouse input
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        //Set up the game over screen (hidden at start)
        this.gameOverScreen = new GameOverScreen();
        gameOverScreen.setVisible(false);
        this.add(gameOverScreen, BorderLayout.CENTER);

        //Loading all the sound files
        backgroundSong = new Sound("/backgroundSong.wav");
        shootSound = new Sound("/shootSound.wav");
        zombieDeathSound = new Sound ("/zombieDeathSound.wav");
        ammoPickupSound = new Sound ("/ammoPickupSound.wav");
        
        //The calling actionPerformed every 16ms
        timer = new Timer(16, this);
        initGame();//Initialize game variables
    }

    //Method to change the player's look based on the skin chosen
    public void setSkin(String s){
        SkinId = s;
    }

    //Getter method to give Controller access to the game over screen
    public GameOverScreen getGameOverScreen(){
        return gameOverScreen;
    }

    //Resets all variables to start a fresh game (Initialize game)
    public void initGame() {
        backgroundImg = new ImageIcon(getClass().getResource("/backgroundImg.jpg")).getImage();
        playerImg = new ImageIcon(getClass().getResource(SkinId)).getImage();
        zombieImg = new ImageIcon(getClass().getResource("/zombieSprite.png")).getImage();
        AmmoImg = new ImageIcon(getClass().getResource("/ammoSprite.png")).getImage();
        
        player = new Player(GameConstants.WIDTH / 2, GameConstants.HEIGHT / 2);
        zombies = new ArrayList<>();
        bullets = new ArrayList<>();
        ammunitions = new ArrayList<>();
        random = new Random();
        
        shootable = true;
        score = 0;
        ammo = 30;
        isGameOver = false;
        gameOverScreen.setVisible(false);
        wPressed = aPressed = sPressed = dPressed = false;
        
        //Start playing the music on a loop
        backgroundSong.loop();
        requestFocusInWindow();
    }

    //This method runs every 16 milliseconds (the game loop)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver){
            backgroundSong.stop();
            return;
        }

        //Update the positions of everything
        updatePlayer();
        updateBullets();
        updateZombies();

        //Check if player ran out of ammo
        if(ammo <=0){
            shootable = false;
        }

        //Randomly spawn new zombies over time (getting harder as score goes up)
        if (random.nextInt(100) < 2 + (score / 100)) spawnZombie();

        //Check for collisions between objects
        checkCollisions();
        collectAmmunition();
        
        //Redraw the screen with the new positions
        repaint();
    }

    //Moves the player based on WASD keys and calculates the look-at-mouse angle
    private void updatePlayer() {
        if (wPressed && player.y > 0) player.y -= GameConstants.PLAYER_SPEED;
        if (sPressed && player.y < GameConstants.HEIGHT - GameConstants.PLAYER_SIZE) player.y += GameConstants.PLAYER_SPEED;
        if (aPressed && player.x > 0) player.x -= GameConstants.PLAYER_SPEED;
        if (dPressed && player.x < GameConstants.WIDTH - GameConstants.PLAYER_SIZE) player.x += GameConstants.PLAYER_SPEED;

        double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
        double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
        
        //Point the player towards the mouse cursor using the atan2 function that gives angle in radians
        player.angle = Math.atan2(-mouseY + centerY, -mouseX + centerX);
    }

    //Moves all bullets and deletes them if they go off screen
    private void updateBullets() {
            //Create an iterator to go through all active bullets safely
            Iterator<Bullet> it = bullets.iterator();
            
            //Keep going as long as there is another bullet to check
            while (it.hasNext()) {
                Bullet b = it.next(); //Grab the next bullet
                
                b.move(); //Make the bullet fly forward
                
                //Check if the bullet has flown off the screen edges
                if (b.x < 0 || b.x > GameConstants.WIDTH || b.y < 0 || b.y > GameConstants.HEIGHT) {
                    //Delete the bullet so it doesn't waste computer memory
                    it.remove();
                }
            }
        }

    //Update the zombies to chase the player
    private void updateZombies() {
        for (Zombie z : zombies) z.chase(player);
    }

    //Creates a new zombie at a random edge of the screen
    private void spawnZombie() {
        int x, y;
        if (random.nextBoolean()) {
            x = random.nextBoolean() ? -GameConstants.ZOMBIE_SIZE : GameConstants.WIDTH;
            y = random.nextInt(GameConstants.HEIGHT);
        } else {
            x = random.nextInt(GameConstants.WIDTH);
            y = random.nextBoolean() ? -GameConstants.ZOMBIE_SIZE : GameConstants.HEIGHT;
        }
        zombies.add(new Zombie(x, y));
    }

    //Drops an ammo pack when a zombie is killed
    private void spawnAmmunition(double  x, double  y){
        ammunitions.add(new Ammunition(x, y));
    }

    //Checks for collisions between bullets, zombies, and the player
    private void checkCollisions() {
        Rectangle playerRect = player.getBounds();
        Iterator<Zombie> zIt = zombies.iterator();
        
        while (zIt.hasNext()) {
            Zombie z = zIt.next();
            Rectangle zombieRect = z.getBounds();

            //If a zombie touches the player, it's game over
            if (playerRect.intersects(zombieRect)){ 
                backgroundSong.stop();
                isGameOver = true;
            }

            //Check if any bullets hit this zombie
            Iterator<Bullet> bIt = bullets.iterator();
            while (bIt.hasNext()) {
                Bullet b = bIt.next();
                if (b.getBounds().intersects(zombieRect)) {
                    spawnAmmunition(z.x, z.y); //Drop ammo
                    zombieDeathSound.play();
                    bIt.remove(); //Remove bullet
                    zIt.remove(); //Remove zombie
                    score += 10;
                    break;
                }
            }
        }
    }

    //Checks if the player walks over an ammo pack
    private void collectAmmunition(){
        Rectangle playerRect = player.getBounds();
        Iterator<Ammunition> aIt = ammunitions.iterator();
        while(aIt.hasNext()){
            Ammunition a = aIt.next();
            Rectangle ammunitionRect = a.getBounds();

            if (playerRect.intersects(ammunitionRect)) { //Check for collision between player and ammo
                ammoPickupSound.play();
                aIt.remove();
                ammo+=5;
                shootable = true;
                break;
            }
        }
    }

    //The drawing method that puts images and text on the screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Stop everything and show game over screen if player died
        if (isGameOver) {
            drawGameOver(g2d);
            setHighScore();
            backgroundSong.stop();
            timer.stop();
            return;
        }
        
        //Draw the background image
        g2d.drawImage(backgroundImg, 0, 0, 800, 900, null);

        //Special code to rotate the player image to face the mouse
        AffineTransform old = g2d.getTransform();
        g2d.translate(player.x + GameConstants.PLAYER_SIZE / 2.0, player.y + GameConstants.PLAYER_SIZE / 2.0);
        g2d.rotate(player.angle - Math.PI / 2);
        g2d.translate(-GameConstants.PLAYER_SIZE / 2.0, -GameConstants.PLAYER_SIZE / 2.0);
        g2d.drawImage(playerImg, 0, 0, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE, null);
        g2d.setTransform(old);

        //Draw all the zombies on screen
        for (Zombie z : zombies) {
            g2d.drawImage(zombieImg, (int)z.x, (int)z.y, GameConstants.ZOMBIE_SIZE, GameConstants.ZOMBIE_SIZE, null);
        }

        //Draw all active bullets
        g2d.setColor(Color.YELLOW);
        for (Bullet b : bullets) {
            g2d.fillOval((int)b.x, (int)b.y, GameConstants.BULLET_SIZE, GameConstants.BULLET_SIZE);
        }

        //Draw all dropped ammo packs
        for(Ammunition a: ammunitions){ 
            g2d.drawImage(AmmoImg,(int)a.x, (int) a.y, 30, 30, null);
        }

        //Draw the Score and Ammo text on the top left
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Score: " + score, 10, 25);
        g2d.drawString("Ammo:" + ammo, 10, 45 );
    }

    //Shows the game over panel and displays the final score
    private void drawGameOver(Graphics2D g) {
        gameOverScreen.setOpaque(true);
        gameOverScreen.setVisible(true);
        gameOverScreen.scoreText("Score: " + score);
    }

    //Method to compare current score to high score and save it to a file
    private void setHighScore(){
        String fileName = "Score.txt";
        int currentHighScore = 0;
        File scoreFile = new File(fileName);

        try {
            //Read the file to see the old high score
            if (scoreFile.exists()) {
                Scanner scanner = new Scanner(scoreFile);
                if (scanner.hasNextInt()) {
                    currentHighScore = scanner.nextInt();
                }
                scanner.close();
            } else {
                scoreFile.createNewFile();
            }

            //If the player just beat the high score, write the new score to the file
            if (score > currentHighScore) {
                PrintWriter writer = new PrintWriter(scoreFile);
                writer.println(score);
                writer.close();
            }

        } catch (IOException e) {
            System.out.println("Error accessing high score file: " + e.getMessage());
        }
    }

    //Detects when the mouse is clicked to fire a bullet
    @Override public void mousePressed(MouseEvent e) {
        if(shootable == true){
            double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
            double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
            
            //Add a new bullet flying in the direction the player is facing
            bullets.add(new Bullet(centerX, centerY, player.angle - Math.PI));
            ammo -= 1;
            shootSound.play();
        }
    }

    //Tracks the mouse movement to update the player's aim
    @Override public void mouseMoved(MouseEvent e) {
        mouseX = e.getX(); mouseY = e.getY(); 
    }

    @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    
    //Tracks when movement keys (WASD) are held down
    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = true; if (k == KeyEvent.VK_A) aPressed = true;
        if (k == KeyEvent.VK_S) sPressed = true; if (k == KeyEvent.VK_D) dPressed = true;
        if (k == KeyEvent.VK_F) fpressed = true;
    }
    
    //Tracks when movement keys are released to stop movement
    @Override public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = false; if (k == KeyEvent.VK_A) aPressed = false;
        if (k == KeyEvent.VK_S) sPressed = false; if (k == KeyEvent.VK_D) dPressed = false;
        if (k == KeyEvent.VK_F) fpressed = false;
    }
    
    //Empty methods required to satisfy the Listeners
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}