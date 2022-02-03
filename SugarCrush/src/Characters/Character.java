package Characters;

import Characters.Type.*;
import GemCreator.Gem;

import java.util.ArrayList;

public abstract class Character
{
    // Type of the character
    Type type = new TypeFactory().getType();

    double health;

    int h = 50;
    int w = 50;

    /**
     * Returns color (or gem) type of the character
     * @return ColorType
     */
    public ColorType getType()
    {
        return type.getType();
    }

    public abstract void setHealth(double health);

    /**
     * Handles the damage done to the character.
     * @param damage Base damage value
     * @param type ColorType of the tiles that hit to the character.
     * @param tile_size Number of tiles that are hit to the character.
     */
    public void hit(double damage, ColorType type, int tile_size)
    {
        double mult = 1.0;

        if (tile_size > 3)
        {
            if (this.getType() == ColorType.RED && type == ColorType.GREEN)
            {
                mult = 0.5;
            }
            else if (this.getType() == ColorType.RED && type == ColorType.BLUE)
            {
                mult = 2.0;
            }
            else if (this.getType() == ColorType.GREEN && type == ColorType.BLUE)
            {
                mult = 0.5;
            }
            else if (this.getType() == ColorType.GREEN && type == ColorType.RED)
            {
                mult = 2.0;
            }
            else if (this.getType() == ColorType.BLUE && type == ColorType.RED)
            {
                mult = 0.5;
            }
            else if (this.getType() == ColorType.BLUE && type == ColorType.GREEN)
            {
                mult = 2.0;
            }
        }

        double total_damage = damage * mult * tile_size;
        this.setHealth(health - total_damage);
    }

    /**
     * Returns true if the character is dead, otherwise, false.
     * @return boolean
     */
    public boolean isDead()
    {
        if (health > 0)
            return false;

        return true;
    }
}
