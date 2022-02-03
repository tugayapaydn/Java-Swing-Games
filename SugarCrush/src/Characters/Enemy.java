package Characters;

import java.awt.*;

/**
 * Enemy class includes type and style and other Character attributes.
 */
public class Enemy extends Character
{
    //Style of the enemy
    Characters.Style.Style style = new StyleFactory().getStyle();

    public Enemy()
    {
        this.health = type.getHealth()*style.getHealth_mult();
    }

    /**
     * Returns name
     * @return String
     */
    public String getName()
    {
        return type.getName() + " - " + style.getName();
    }

    /**
     * Returns still
     * @return Image
     */
    public Image getStill()
    {
        return style.getStill();
    }

    /**
     * Returns Agility
     * @return double
     */
    public double getAgility()
    {
        return type.getAgility() * style.getAgility_mult();
    }

    /**
     * Returns strength
     * @return double
     */
    public double getStrength()
    {
        return  type.getStrength() * style.getStrength_mult();
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
     * Sets a new health value.
     * @param health new health of the enemy
     */
    @Override
    public void setHealth(double health) {
        this.health = health;
    }
}
