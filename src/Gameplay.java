import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// everything in this class add to the panel
public class Gameplay extends JPanel implements KeyListener, ActionListener {

// the game will not started outomatically
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    // for the ball how fast should be moves
    private Timer Timer;
    // the speed giving to the timer
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    // how much the ball moving in x direction each time
    private int ballXdir = -1;
    // how much the ball moving in y direction each time
    private int ballYdir = -2;
    // create object map of MapGenerator class
    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //Creates a Timer and initializes both the initial delay and between-event delay to delay milliseconds.
        // If delay is less than or equal to zero, the timer fires as soon as it is started. If listener is not null, it's registered as an action listener on the timer.
        Timer = new Timer(delay, this);
        Timer.start();
    }
// when we create a shape after shape the second shape will appears above
    // g is object from graphics class
    // g.methodsName of graphics class
    public void paint(Graphics g) {
        // background
        g.setColor(Color.gray);
        g.fillRect(0, 0,700, 600);

        map.draw((Graphics2D) g);


// score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        g.setColor(Color.black);
        // just x direction is movable
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.cyan);
        g.fillOval(ballposX, ballposY, 20, 20);
// if you lose and the ball dropdown it will be less than platform in y direction
        if (ballposY > 570) {

            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.black);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        // if the player win
        if(totalbricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.black);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);


        }
//Disposes of this graphics context and releases any system resources that it is using. A Graphics object cannot be used after dispose has been called.
        g.dispose();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
//Starts the Timer, causing it to start sending action events to its listeners.
        Timer.start();
        // make the ball affected with the platform .../"java.awt.Rectangle.intersect/"
        //Determines whether or not this Rectangle and the specified Rectangle intersect. Two rectangles intersect if their intersection is nonempty.
        if (play) {           //the ball                                           // the platform
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
                A:              // object and array length in mapgenerator class
                for (int i = 0; i < map.map.length; i++) {
                    for (int j = 0; j < map.map[0].length; j++) {
                        if (map.map[i][j] > 0) {
                            int brickX = j * map.bricksWidth + 80;
                            int brickY = i * map.bricksHeight + 50;
                            int bricksWidth = map.bricksWidth;
                            int bricksHeight = map.bricksHeight;

                            Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                            Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                            Rectangle brickrect = rect;

                            if (ballrect.intersects(brickrect)) {
                                map.setBricksValue(0, i, j);
                                totalbricks--;
                                score += 5;
                                //if the ball broke the brick it will move inversely
                               if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                    ballXdir = -ballXdir;
                                } else {
                                    ballYdir = -ballYdir;
                                }
                                break A;
                            }
                        }


                    }
                }
            } else{
                A:              // object and array length in mapgenerator class
                for (int i = 0; i < map.map.length; i++) {
                    for (int j = 0; j < map.map[0].length; j++) {
                        if (map.map[i][j] > 0) {
                            int brickX = j * map.bricksWidth + 80;
                            int brickY = i * map.bricksHeight + 50;
                            int bricksWidth = map.bricksWidth;
                            int bricksHeight = map.bricksHeight;

                            Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                            Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                            Rectangle brickrect = rect;

                            if (ballrect.intersects(brickrect)) {
                                map.setBricksValue(0, i, j);
                                totalbricks--;
                                score += 5;
                                //if the ball broke the brick it will move inversely
                                if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                    ballXdir = -ballXdir;
                                } else {
                                    ballYdir = -ballYdir;
                                }
                                break A;
                            }
                        }


                    }
                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
            // if rhe ball reach the borders it will moves the invers of it's direction
            // for the left
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            // for the top
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            // for the right
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        // call pain method and do everything again

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
// the control of platform
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //set the maximum width of right is 600
            // if we allow more than frame width it will go out
            if (playerX >= 600) {
               playerX = 600;
            } else {
                // if it's less than 600 allow to moveing right
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


    }

    public void moveRight ()
    {
        play = true;
        // one press moves 20 px
        playerX += 20;
    }
    public void moveLeft ()
    {
        play = true;
        playerX -= 20;
    }

}