package Characters.Style;

import Characters.Type.ColorType;

import java.awt.*;

public abstract class Style
{
    String name;

    double strength_mult;
    double agility_mult;
    double health_mult;
    Image still;

    public Image getStill() {
        return still;
    }

    public String getName() {
        return name;
    }

    public double getAgility_mult() {
        return agility_mult;
    }

    public double getHealth_mult() {
        return health_mult;
    }

    public double getStrength_mult() {
        return strength_mult;
    }
}
