package Characters.Style;

import javax.swing.*;

public class Underwild extends Style
{
    public Underwild()
    {
        name = "Underwild";
        still = new ImageIcon(this.getClass().getResource("Assets/underwild.png")).getImage();

        strength_mult = 0.8;
        agility_mult = 1.6;
        health_mult = 0.8;
    }
}
