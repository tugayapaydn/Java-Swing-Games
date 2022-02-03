package Characters.Type;

import java.awt.*;

public abstract class Type
{
    ColorType type;
    String name;

    double strength;
    double agility;
    double health;
    Image still;

    public String getName() {
        return name;
    }
    public double getStrength() {
        return strength;
    }
    public double getHealth() {
        return health;
    }
    public double getAgility() {
        return agility;
    }
    public Image getStill() {
        return still;
    }
    public ColorType getType() {
        return type;
    }
}

