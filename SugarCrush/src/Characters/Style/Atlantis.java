package Characters.Style;

import javax.swing.*;

public class Atlantis extends Style
{
    public Atlantis()
    {
        name = "Atlantis";
        still = new ImageIcon(this.getClass().getResource("Assets/atlantis.png")).getImage();

        strength_mult = 1.3;
        agility_mult = 1.2;
        health_mult = 1.2;
    }
}
