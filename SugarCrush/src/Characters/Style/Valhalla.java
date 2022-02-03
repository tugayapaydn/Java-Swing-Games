package Characters.Style;

import javax.swing.*;

public class Valhalla extends Style
{
    public Valhalla()
    {
        name = "Valhalla";
        still = new ImageIcon(this.getClass().getResource("Assets/valhalla.png")).getImage();
        strength_mult = 1.3;
        agility_mult = 0.4;
        health_mult = 1.3;
    }
}
