package Characters;

import java.awt.*;

/**
 * Char class includes type and other Character attributes.
 */
public class Char extends Character
{
    public Char()
    {
        this.health = type.getHealth();
    }

    /**
     * Returns name
     * @return String
     */
    public String getName()
    {
        return type.getName();
    }

    /**
     * Returns still
     * @return Image
     */
    public Image getStill()
    {
        return type.getStill();
    }

    /**
     * Returns Agility
     * @return double
     */
    public double getAgility()
    {
        return type.getAgility();
    }

    /**
     * Returns health
     * @return double
     */
    public double getHealth()
    {
        return this.health;
    }

    /**
     * Returns strength
     * @return double
     */
    public double getStrength()
    {
        return type.getStrength();
    }

    /**
     * Sets a new health value.
     * @param health new health of the enemy
     */
    @Override
    public void setHealth(double health) {
        this.health = health;
    }

}
