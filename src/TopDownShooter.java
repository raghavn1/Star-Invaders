import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class TopDownShooter extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    public Timer timer;
    private boolean isGameOver = false;
    private int score = 0;
    private boolean wPressed, aPressed, sPressed, dPressed;
    private int mouseX, mouseY;

    private Player player;
    private ArrayList<Zombie> zombies;
    private ArrayList<Bullet> bullets;
    private Random random;
    private GameOverScreen gameOverScreen;

    public static void main(String[] args) {
        
    }

    public TopDownShooter() {
        setPreferredSize(new Dimension(GameConstants.WIDTH, GameConstants.HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.gameOverScreen = new GameOverScreen();
        gameOverScreen.setVisible(false);
        this.add(gameOverScreen, BorderLayout.CENTER);

        initGame();
        timer = new Timer(16, this);
        //timer.start();
    }

    public GameOverScreen getGameOverScreen(){
        return gameOverScreen;
    }

    public void initGame() {
        player = new Player(GameConstants.WIDTH / 2, GameConstants.HEIGHT / 2);
        zombies = new ArrayList<>();
        bullets = new ArrayList<>();
        random = new Random();
        score = 0;
        isGameOver = false;
        gameOverScreen.setVisible(false);
        wPressed = aPressed = sPressed = dPressed = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) return;

        updatePlayer();
        updateBullets();
        updateZombies();

        if (random.nextInt(100) < 2 + (score / 100)) spawnZombie();

        checkCollisions();
        repaint();
    }

    private void updatePlayer() {
        if (wPressed && player.y > 0) player.y -= GameConstants.PLAYER_SPEED;
        if (sPressed && player.y < GameConstants.HEIGHT - GameConstants.PLAYER_SIZE) player.y += GameConstants.PLAYER_SPEED;
        if (aPressed && player.x > 0) player.x -= GameConstants.PLAYER_SPEED;
        if (dPressed && player.x < GameConstants.WIDTH - GameConstants.PLAYER_SIZE) player.x += GameConstants.PLAYER_SPEED;

        double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
        double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
        player.angle = Math.atan2(mouseY - centerY, mouseX - centerX);
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

    private void checkCollisions() {
        Rectangle playerRect = player.getBounds();
        Iterator<Zombie> zIt = zombies.iterator();
        while (zIt.hasNext()) {
            Zombie z = zIt.next();
            Rectangle zombieRect = z.getBounds();

            if (playerRect.intersects(zombieRect)) isGameOver = true;

            Iterator<Bullet> bIt = bullets.iterator();
            while (bIt.hasNext()) {
                Bullet b = bIt.next();
                if (b.getBounds().intersects(zombieRect)) {
                    bIt.remove();
                    zIt.remove();
                    score += 10;
                    break;
                }
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
            timer.stop();
            return;
        }

        // Draw Player
        AffineTransform old = g2d.getTransform();
        g2d.translate(player.x + GameConstants.PLAYER_SIZE/2, player.y + GameConstants.PLAYER_SIZE/2);
        g2d.rotate(player.angle);
        g2d.translate(-GameConstants.PLAYER_SIZE/2, -GameConstants.PLAYER_SIZE/2);
        g2d.setColor(Color.BLUE);
        g2d.fillOval(0, 0, GameConstants.PLAYER_SIZE, GameConstants.PLAYER_SIZE);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(GameConstants.PLAYER_SIZE/2, GameConstants.PLAYER_SIZE/2 - 5, GameConstants.PLAYER_SIZE, 10);
        g2d.setTransform(old);

        // Draw Zombies
        g2d.setColor(Color.GREEN);
        for (Zombie z : zombies) g2d.fillRect((int)z.x, (int)z.y, GameConstants.ZOMBIE_SIZE, GameConstants.ZOMBIE_SIZE);

        // Draw Bullets
        g2d.setColor(Color.YELLOW);
        for (Bullet b : bullets) g2d.fillOval((int)b.x, (int)b.y, GameConstants.BULLET_SIZE, GameConstants.BULLET_SIZE);

        // UI
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Score: " + score, 10, 25);
    }

    private void drawGameOver(Graphics2D g) {
       gameOverScreen.setOpaque(true);
        gameOverScreen.setVisible(true);
    }

    @Override public void mousePressed(MouseEvent e) {
        double centerX = player.x + GameConstants.PLAYER_SIZE / 2.0;
        double centerY = player.y + GameConstants.PLAYER_SIZE / 2.0;
        bullets.add(new Bullet(centerX, centerY, player.angle));
    }

    @Override public void mouseMoved(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = true; if (k == KeyEvent.VK_A) aPressed = true;
        if (k == KeyEvent.VK_S) sPressed = true; if (k == KeyEvent.VK_D) dPressed = true;
    }
    @Override public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) wPressed = false; if (k == KeyEvent.VK_A) aPressed = false;
        if (k == KeyEvent.VK_S) sPressed = false; if (k == KeyEvent.VK_D) dPressed = false;
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}