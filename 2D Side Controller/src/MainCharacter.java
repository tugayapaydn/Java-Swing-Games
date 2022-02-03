import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class MainCharacter implements ICharacter
{
    private static final int WALK_SPEED = 7;
    private static final int height = 120;
    private static final int width = 80;
    private int heart_left = 3;

    private int startPointX;
    private int startPointY;

    // Distance between real coord x and virtual coord y
    public int range = 0;

    // Hero Status Images
    private Image heroStandImg = new ImageIcon(this.getClass().getResource("assets/heroWait.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    private Image heroJumpImg = new ImageIcon(this.getClass().getResource("assets/heroJump.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    private Image heroRunImg = new ImageIcon(this.getClass().getResource("assets/heroRun.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    private Image heroFailImg = new ImageIcon(this.getClass().getResource("assets/heroFail.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

    // Coordinate points
    private Point virtualCoord = new Point();
    private Point realCoord = new Point();

    private IJump jumpFunc;
    private IJump jumpChanger = null;
    private boolean moveRight = false;
    private boolean failed = false;

    private int lastPressed = -1;

    /**
     * Constructor of MainCharacter
     * @param startX The horizontal start point of the character
     * @param mapBottom Map bottom coordinate also will be hero bottom point.
     */
    public MainCharacter(int startX, int mapBottom)
    {
        startPointX = startX;
        startPointY = mapBottom;

        this.virtualCoord.setLocation(new Point(startX, mapBottom-height));
        this.realCoord.setLocation(new Point(startX, mapBottom-height));
        this.jumpFunc = new LowJump(mapBottom, height, virtualCoord, realCoord);
    }

    /* Setter Functions */
    public void setJump(IJump jumpFunc)
    {
        this.jumpChanger = jumpFunc;
    }

    /**
     * Returns an Image instance according to character status.
     * Character statuses can be moving, jumping, standing or failed.
     * @return Image instance.
     */
    public Image getCharImg()
    {
        if (virtualCoord.y + height < startPointY)
        {
            return heroJumpImg;
        }
        else if (moveRight)
        {
            return heroRunImg;
        }
        else if (failed)
        {
            return heroFailImg;
        }
        else
        {
            return heroStandImg;
        }
    }

    /* Getter functions */
    public Point getVirtualCoord() { return virtualCoord; }
    public Point getRealCoord() { return realCoord; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }
    public boolean isFailed() { return failed; }

    /**
     * Resets character settings to default to start from the starting point.Also, decreases one hearth.
     */
    public void reset()
    {
        this.virtualCoord.setLocation(new Point(startPointX, startPointY-height));
        this.realCoord.setLocation(new Point(startPointX, startPointY-height));
        this.range = 0;
        --heart_left;
        failed = false;
        moveRight = false;
        this.jumpFunc = new LowJump(startPointY, height, virtualCoord, realCoord);
        jumpChanger = null;
    }

    /**
     * Checks if character run out of total life chances.
     * @return true if character has any hearth left, otherwise, false.
     */
    public boolean isAlive()
    {
        if (heart_left == 0){
            failed = true;
            return false;
        }

        return true;
    }

    /**
     * Increases character x coordinate wrt WALK_SPEED.
     */
    public void walk()
    {
        if (moveRight) {
            virtualCoord.setLocation(virtualCoord.x + WALK_SPEED, virtualCoord.y);
            range -= WALK_SPEED;
        }
    }

    /**
     * Makes character jump wrt jumpFunc.
     */
    public void jump()
    {
        this.jumpFunc.jump();
    }

    /**
     * Main character updater function. Updates both walk and jump results.
     */
    public void update()
    {
        walk();
        jump();
    }

    /**
     * Checks for key pressed events to start an action.
     * Currently start moving and jumping actions.
     * @param e KeyEvent
     * @throws InterruptedException
     */
    public void keyPressed(KeyEvent e) throws InterruptedException
    {
        if (!failed)
        {
            int code = e.getKeyCode();

            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
            {
                if (e.getKeyChar() != lastPressed)
                {
                    lastPressed = e.getKeyChar();
                    MainWindow.logger.append("Move Right" + System.lineSeparator());
                }

                moveRight = true;
            }
            else if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W ||code == KeyEvent.VK_SPACE)
            {
                if (e.getKeyChar() != lastPressed)
                {
                    lastPressed = e.getKeyChar();
                    MainWindow.logger.append(jumpFunc.toString() + System.lineSeparator());
                }

                if (jumpFunc.isLanded() && jumpChanger != null)
                {
                    jumpFunc = jumpChanger;
                }
                jumpFunc.setJumping();
            }
            else if (code == KeyEvent.VK_ESCAPE)
            {
                moveRight = false;
            }
        }
    }

    /**
     * Checks for key release events to stop an action.
     * Currently stops character moving.
     * @param e KeyEvent
     */
    public void keyReleased(KeyEvent e)
    {
        if (!failed)
        {
            lastPressed = -1;
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
            {
                moveRight = false;
            }
        }
    }
}
