package Characters.Type;

import javax.swing.*;

public class Ice extends Type
{
    public Ice()
    {
        name = "Ice";
        type = ColorType.BLUE;
        strength = 125;
        agility = 75;
        health = 100;
        still = new ImageIcon(this.getClass().getResource("Assets/ice.png")).getImage();
    }
}