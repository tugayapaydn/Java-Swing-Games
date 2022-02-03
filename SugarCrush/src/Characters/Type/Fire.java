package Characters.Type;

import javax.swing.*;

public class Fire extends Type
{
    public Fire()
    {
        name = "Fire";
        type = ColorType.RED;
        strength = 100;
        agility = 125;
        health = 75;
        still = new ImageIcon(this.getClass().getResource("Assets/red.png")).getImage();
    }
}