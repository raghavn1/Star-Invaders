import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class TopDownShooter extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    public Timer timer;
    private boolean isGameOver = false;
    private int score = 0;
    private boolean wPressed, aPressed, sPressed, dPressed, fpressed;
    private int mouseX, mouseY;
    private int ammo = 30;
    private boolean shootable = true;

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

    public Sound backgroundSong;
    private Sound shootSound;
    private Sound zombieDeathSound;
    private Sound ammoPickupSound;

    private Image backgroundImg;


    public TopDownShooter() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gameOverScreen = new GameOverScreen();
        gameOverScreen.setVisible(false);
        this.add(gameOverScreen, BorderLayout.CENTER);


        // Replace your old icon lines with this:

    




        backgroundSong = new Sound("/backgroundSong.wav");
        shootSound = new Sound("/shootSound.wav");
        zombieDeathSound = new Sound ("/zombieDeathSound.wav");
        ammoPickupSound = new Sound ("/ammoPickupSound.wav");
        
        
        timer = new Timer(16, this);
        //timer.start();
        initGame();
        

    }

    public void setSkin(String s){
        SkinId = s;
    }

    public GameOverScreen getGameOverScreen(){
        return gameOverScreen;
    }

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
        backgroundSong.loop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver){
            backgroundSong.stop();
            return;
        }

        updatePlayer();
        updateBullets();
        updateZombies();

        if(ammo <=0){
            shootable = false;
        }

        if (random.nextInt(100) < 2 + (score / 100)) spawnZombie();

        checkCollisions();
        collectAmmunition();
        repaint();
    }

    private void updatePlayer() {
        if (wPressed && player.y > 0) player.y -= GameConstants.PLAYER_SPEED;
        if (sPressed && player.y < GameConstants.HEIGHT - GameConstants.PLAYER_SIZE) player.y += GameConstants.PLAYER_SPEED;
        if (aPressed && player.x > 0) player.x -= GameConstants.PLAYER_SPEED;
        if (dPressed && player.x < GameConstants.WIDTH - GameConstants.PLAYER_SIZE) player.x += GameConstants.PLAYER_SPEED;

        double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
        double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
        //player.angle = Math.atan2(mouseY - centerY, mouseX - centerX); //ORIGINAL
        player.angle = Math.atan2(-mouseY + centerY, -mouseX + centerX);
    }

    private void updateBullets() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.move();
            if (b.x < 0 || b.x > GameConstants.WIDTH || b.y < 0 || b.y > GameConstants.HEIGHT) {
                it.remove();
            }
        }
    }


    private void updateZombies() {
        for (Zombie z : zombies) z.chase(player);
    }

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

    private void spawnAmmunition(double  x, double  y){
        ammunitions.add(new Ammunition(x, y));
    }
        



    private void checkCollisions() {
        Rectangle playerRect = player.getBounds();
        Iterator<Zombie> zIt = zombies.iterator();
        while (zIt.hasNext()) {
            Zombie z = zIt.next();
            Rectangle zombieRect = z.getBounds();

            if (playerRect.intersects(zombieRect)){ 
                backgroundSong.stop();
                isGameOver = true;
            }

            Iterator<Bullet> bIt = bullets.iterator();
            while (bIt.hasNext()) {
                Bullet b = bIt.next();
                if (b.getBounds().intersects(zombieRect)) {
                    spawnAmmunition(z.x, z.y);
                    zombieDeathSound.play();
                    bIt.remove();
                    zIt.remove();
                   
                    score += 10;
                    break;
                }
            }
        }
    }

    private void collectAmmunition(){
        Rectangle playerRect = player.getBounds();
        Iterator<Ammunition> aIt = ammunitions.iterator();
        while(aIt.hasNext()){
            Ammunition a = aIt.next();
            Rectangle ammunitionRect = a.getBounds();

            if (playerRect.intersects(ammunitionRect)) {
                ammoPickupSound.play();
                aIt.remove();
                ammo+=5;
                shootable = true;
                break;
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isGameOver) {
            drawGameOver(g2d);
            setHighScore();
            backgroundSong.stop();
            timer.stop();
            return;
        }
        // Draw Background
        g2d.drawImage(backgroundImg, 0, 0, 800, 900, null);

        // Draw Player
        AffineTransform old = g2d.getTransform();

        g2d.translate(player.x + GameConstants.PLAYER_SIZE / 2.0, player.y + GameConstants.PLAYER_SIZE / 2.0);

        g2d.rotate(player.angle - Math.PI / 2);

        g2d.translate(-GameConstants.PLAYER_SIZE / 2.0, -GameConstants.PLAYER_SIZE / 2.0);

        g2d.drawImage(playerImg, 0, 0, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE, null);

        g2d.setTransform(old);

        

        // Draw Zombies
        for (Zombie z : zombies) {
            g2d.drawImage(zombieImg, (int)z.x, (int)z.y, GameConstants.ZOMBIE_SIZE, GameConstants.ZOMBIE_SIZE, null);
        }

        // Draw Bullets
        g2d.setColor(Color.YELLOW);
        for (Bullet b : bullets) {
            g2d.fillOval((int)b.x, (int)b.y, GameConstants.BULLET_SIZE, GameConstants.BULLET_SIZE);
        }

        // drawing ammunitions
        g2d.setColor(Color.BLUE);
        for(Ammunition a: ammunitions){ 
            g2d.drawImage(AmmoImg,(int)a.x, (int) a.y, 30, 30, null);
        }

        // UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Score: " + score, 10, 25);
        g2d.drawString("Ammo:" + ammo, 10, 45 );
    }

    private void drawGameOver(Graphics2D g) {
        gameOverScreen.setOpaque(true);
        gameOverScreen.setVisible(true);
        gameOverScreen.scoreText("Score: " + score);
    }

    private void setHighScore(){
        String fileName = "Score.txt";
    int currentHighScore = 0;
    File scoreFile = new File(fileName);

    try {
        // 1. Read the existing high score
        if (scoreFile.exists()) {
            Scanner scanner = new Scanner(scoreFile);
            if (scanner.hasNextInt()) {
                currentHighScore = scanner.nextInt();
            }
            scanner.close();
        } else {
            // Create the file if it doesn't exist yet
            scoreFile.createNewFile();
        }

        // 2. Compare and Write if the new score is higher
        if (score > currentHighScore) {
            PrintWriter writer = new PrintWriter(scoreFile);
            writer.println(score);
            writer.close(); // Important: This flushes the data to the file
            System.out.println("New High Score: " + score + " saved to " + fileName);
        }

    } catch (IOException e) {
        System.out.println("Error accessing high score file: " + e.getMessage());
    }
    }

    @Override public void mousePressed(MouseEvent e) {
        if(shootable == true){
        double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
        double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
        bullets.add(new Bullet(centerX, centerY, player.angle - Math.PI));
        ammo-=1;
        shootSound.play();
        }else {
            return;
        }
        
    }

    @Override public void mouseMoved(MouseEvent e) {
        mouseX = e.getX(); mouseY = e.getY(); 
    }

    @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = true; if (k == KeyEvent.VK_A) aPressed = true;
        if (k == KeyEvent.VK_S) sPressed = true; if (k == KeyEvent.VK_D) dPressed = true;
        if(k== KeyEvent.VK_F) fpressed = true;
    }
    @Override public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = false; if (k == KeyEvent.VK_A) aPressed = false;
        if (k == KeyEvent.VK_S) sPressed = false; if (k == KeyEvent.VK_D) dPressed = false;
        if(k== KeyEvent.VK_F) fpressed = false;
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}