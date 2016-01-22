/**
 * Created by Pontus on 2015-12-21.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener {

    public static final int WIDTH = 600;  // For game area
    public static final int HEIGHT = 600;

    private Snake snake = new Snake(WIDTH, HEIGHT, 1);
    private int delay = snake.getDelay();
    private int direction = snake.getDirection();

    public static void main(String[] args) {
        Game game = new Game();

        game.initGraphics();
        game.initEvents();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(178, 178, 177));
        g2.fillRect(0,0,WIDTH, HEIGHT);

        g2.setColor(new Color(10,10,10));
        g2.drawString("Current score: " + snake.getScore(), WIDTH/2 - 10, 50);

        g2.setColor(new Color(193, 57, 60));

        for(int i = 0; i < snake.nBodies(); i++){
            if(i > 0){
                if(i == snake.nBodies() - 1){
                    g2.setColor(new Color(87, 65, 56));
                }else{
                    g2.setColor(new Color(48, 73, 88));
                }
            }

            if(i == snake.nBodies() - 1){
                g2.fillRect(snake.getX(i), snake.getY(i), snake.getWidth(), snake.getHeight());
            }else {
                g2.fillRect(snake.getX(i), snake.getY(i), snake.getWidth(), snake.getHeight());
            }

        }

        if(snake.checkGameOver()){
            g2.drawString("Game over! You got " + snake.getScore() + " points!", WIDTH/2 - 10, HEIGHT/2);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!snake.checkGameOver()){
            snake.setDirection(direction);
            snake.update();
        }
        repaint();
    }

    KeyListener kl = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    direction = 1;
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 2;
                    break;
                case KeyEvent.VK_UP:
                    direction = 3;
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 4;
                    break;
            }
        }
    };

    public void initGraphics() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame window = new JFrame();

        window.getRootPane().setDoubleBuffered(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Snake Wear");

        window.setResizable(false);
        window.validate();
        window.add(this);

        // --- End GUI

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.addKeyListener(kl);

    }

    public void initEvents() {
        Timer timer = new Timer(delay, this);
        timer.start();
    }
}
