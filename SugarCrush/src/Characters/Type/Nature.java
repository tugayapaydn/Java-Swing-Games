package Characters.Type;

import javax.swing.*;

public class Nature extends Type
{
    public Nature()
    {
        name = "Nature";
        type = ColorType.GREEN;
        strength = 75;
        agility = 100;
        health = 125;
        still = new ImageIcon(this.getClass().getResource("Assets/green.png")).getImage();
    }
}
