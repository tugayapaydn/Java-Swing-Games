package PowerUp;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Creates random power up instances
 */
public class PowerUpCreator
{
    public enum PowerUp {A, B, C, D}

    private final int noPowerUp = 4;
    private final int powerUpDim = 50;
    private final int powerUpConstantY = 200;

    // Power up images
    private Image powerUpA = new ImageIcon(this.getClass().getResource("assets/letterA.png")).getImage();
    private Image powerUpB = new ImageIcon(this.getClass().getResource("assets/letterB.png")).getImage();
    private Image powerUpC = new ImageIcon(this.getClass().getResource("assets/letterC.png")).getImage();
    private Image powerUpD = new ImageIcon(this.getClass().getResource("assets/letterD.png")).getImage();

    // Defines if any power ups created
    private boolean powerUpCreated = false;

    // Next power up x coordinate
    private int powerUpCoordX;

    // Next power up type
    private PowerUp powerUpType;

    public PowerUpCreator()
    {
        powerUpA = powerUpA.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        powerUpB = powerUpB.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        powerUpC = powerUpC.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        powerUpD = powerUpD.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    }

    // Getter functions
    public PowerUp getPowerUpType() {
        return powerUpType;
    }
    public int getPowerUpCoordX()
    {
        return powerUpCoordX;
    }
    public int getPowerUpDim()
    {
        return powerUpDim;
    }
    public int getPowerUpConstantY() {
        return powerUpConstantY;
    }

    /**
     * Returns next power up image.
     * @return Image
     */
    public Image getNextPowerUpImage()
    {
        if (!powerUpCreated)
            return null;

        switch (powerUpType)
        {
            case A: return powerUpA;
            case B: return powerUpB;
            case C: return powerUpC;
            case D: return powerUpD;

            default:
                System.out.println("Failure for getting next image for powerup");
                return null;
        }
    }

    /**
     * Creates a random power up wrt start point.
     * @param start_point   Starting coordinate of the power up
     * @param minimum_distance  Minimum distance between start point and power up coordinate
     * @param randomizeLimit    Random number limit to add to distance between start point and power up coordinate
     */
    public void createRandomPowerUp(int start_point, int minimum_distance, int randomizeLimit)
    {
        //Choose random powerup
        Random rand = new Random();
        int nextPowerUp = rand.nextInt(noPowerUp);

        switch (nextPowerUp)
        {
            case 0:
                powerUpType = PowerUp.A;
                break;
            case 1:
                powerUpType = PowerUp.B;
                break;
            case 2:
                powerUpType = PowerUp.C;
                break;
            case 3:
                powerUpType = PowerUp.D;
                break;
            default:
                System.out.println("Failure for powerups");
        }

        // Get random coordinate for the next powerup.
        powerUpCoordX = start_point + minimum_distance + rand.nextInt(randomizeLimit);
        powerUpCreated = true;
    }

    /**
     * Checks if the next power up collapses with the hero
     * @param heroCoord Virtual coordinate of the hero
     * @param heroHeight Hero height
     * @param heroWidth Hero width
     * @return True if the next power up collapses with the hero, otherwise, false.
     */
    public boolean checkCollapse(Point heroCoord, int heroHeight, int heroWidth)
    {
        if (!powerUpCreated)
            return false;

        int powerUpTopCoord = powerUpConstantY;
        int powerUpBottomCoord = powerUpConstantY+powerUpDim;
        int powerUpLeftCoord = powerUpCoordX;
        int powerUpRigthCoord = powerUpCoordX + powerUpDim;

        int heroTopCoord = heroCoord.y;
        int heroBottomCoord = heroCoord.y+heroHeight;
        int heroLeftCoord = heroCoord.x;
        int heroRightCoord = heroCoord.x+heroWidth;

        if (heroRightCoord >= powerUpLeftCoord && heroLeftCoord <= powerUpRigthCoord)
        {
            if (heroBottomCoord >= powerUpTopCoord && heroTopCoord <= powerUpBottomCoord)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the hero passed next power up
     * @param heroCoord Virtual coordinates of the hero
     * @param heroWidth Hero width.
     * @return true if the next power up is passed, otherwise, false.
     */
    public boolean checkPassed(Point heroCoord, int heroWidth)
    {
        if (!powerUpCreated)
            return false;

        int powerUpRigthCoord = powerUpCoordX + powerUpDim;
        int heroLeftCoord = heroCoord.x;

        if (heroLeftCoord >= powerUpRigthCoord)
            return true;

        return false;
    }
}
