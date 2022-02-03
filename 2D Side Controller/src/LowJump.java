import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Class used to make main hero jump low.
 */
public class LowJump implements IJump
{
    // Object is used to synchronize threads for landed variable.
    private Object landedLock = new Object();

    private static int MAX_JUMP_HEIGHT;
    private Point virtualCoord;
    private Point realCoord;
    private int worldHeight;
    private int heroHeight;

    private boolean jumping = false;
    private boolean landing = false;

    public LowJump(int worldHeight, int heroHeight, Point virtualCoord, Point realCoord)
    {
        this.worldHeight = worldHeight;
        this.heroHeight = heroHeight;
        MAX_JUMP_HEIGHT = worldHeight - (worldHeight*7/10);
        this.virtualCoord = virtualCoord;
        this.realCoord = realCoord;

    }

    @Override
    public boolean isLanded()
    {
        return !(jumping || landing);
    }
    /**
     * Sets character's jumping status.
     */
    @Override
    public void setJumping ()
    {
        if (jumping == false && landing == false)
        {
            jumping = true;
        }
    }

    /**
     * Main jump function.
     * Firstly increases the characters y coordinate. After, decreases the y coordinate.
     */
     @Override
    public void jump()
    {
        if (jumping && (virtualCoord.y > 0))
        {
            virtualCoord.setLocation(virtualCoord.x, virtualCoord.y - JUMP_SPEED);
            realCoord.setLocation(realCoord.x, realCoord.y - JUMP_SPEED);
        }

        if (landing && (virtualCoord.y < worldHeight))
        {
            virtualCoord.setLocation(virtualCoord.x, virtualCoord.y + LAND_SPEED);
            realCoord.setLocation(realCoord.x, realCoord.y + LAND_SPEED);
        }

        if (virtualCoord.y <= MAX_JUMP_HEIGHT)
        {
            landing = true;
            jumping = false;
        }
        if (virtualCoord.y+heroHeight >= worldHeight)
        {
            realCoord.setLocation(realCoord.x, worldHeight-heroHeight);
            virtualCoord.setLocation(virtualCoord.x, worldHeight-heroHeight);
            landing = false;
            jumping = false;
        }
    }

    @Override
    public String toString() {
        return "LowJump";
    }
}
