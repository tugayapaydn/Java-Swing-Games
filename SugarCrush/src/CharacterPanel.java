import Characters.Char;
import Characters.Enemy;
import GemCreator.Gem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CharacterPanel extends JPanel
{
    private Char c1 = new Char();
    private Char c2 = new Char();
    private Char c3 = new Char();

    private Enemy e1 = new Enemy();
    private Enemy e2 = new Enemy();
    private Enemy e3 = new Enemy();

    private Image cim1, cim2, cim3;
    private Image eim1, eim2, eim3;

    int dim = 80;

    public CharacterPanel()
    {
        setFocusable(true);
        setVisible(true);
        setBackground(Color.black);
        cim1 = c1.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        cim2 = c2.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        cim3 = c3.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        eim1 = e1.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        eim2 = e2.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
        eim3 = e3.getStill().getScaledInstance(dim, dim, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        paintHero(g);
    }

    /**
     * Paints characters
     * @param g
     */
    public void paintHero(Graphics g)
    {
        g.drawImage(cim1, 30, 30, null);
        g.drawImage(cim2, 180, 30, null);
        g.drawImage(cim3, 320, 30, null);
        g.drawImage(eim1, 30, 150, null);
        g.drawImage(eim2, 180, 150, null);
        g.drawImage(eim3, 320, 150, null);

        g.setColor(Color.WHITE);
        g.drawString(c1.getName(), 30, 25);
        g.drawString(c2.getName(), 180, 25);
        g.drawString(c3.getName(), 320, 25);
        g.drawString(e1.getName(), 30, 140);
        g.drawString(e2.getName(), 180, 140);
        g.drawString(e3.getName(), 320, 140);

    }

    /**
     * Damages enemies or characters according to matching tiles.
     * @param arr Array of tiles.
     * @param mod Mod is 1 if it is computer turn, otherwise, 0.
     */
    // mod 1 == computer turn
    public void damage(ArrayList<Gem> arr, int mod)
    {
        ArrayList<Gem> arr1 = new ArrayList<Gem>();
        ArrayList<Gem> arr2 = new ArrayList<Gem>();
        ArrayList<Gem> arr3 = new ArrayList<Gem>();

        for (int i = 0; i < arr.size(); i++)
        {
            Gem g = arr.get(i);

            int c1 = g.getPoint().x / GemCreator.getW();

            if (c1 >= 0 && c1 < 3)
            {
                arr1.add(g);
            }
            else if (c1 > 2 && c1 < 6)
            {
                arr2.add(g);
            }
            else
            {
                arr3.add(g);
            }
        }

        if (mod == 1)
        {
            if (arr1.size() > 0 && c1.getHealth() > 0)
            {
                c1.hit(e1.getStrength(), arr1.get(0).getType(), arr1.size());
                System.out.println(e1.getName()+" hit to "+c1.getName()+". Hearth left: "+c1.getHealth());
                if (c1.isDead())
                    System.out.println(c1.getName()+" has died.");
            }
            if (arr2.size() > 0 && c2.getHealth() > 0)
            {
                c2.hit(e2.getStrength(), arr2.get(0).getType(), arr2.size());
                System.out.println(e2.getName()+" hit to "+c2.getName()+". Hearth left: "+c2.getHealth());
                if (c2.isDead())
                    System.out.println(c2.getName()+" has died.");
            }
            if (arr3.size() > 0 && c3.getHealth() > 0)
            {
                c3.hit(e3.getStrength(), arr3.get(0).getType(), arr3.size());
                System.out.println(e3.getName()+" hit to "+c3.getName()+". Hearth left: "+c3.getHealth());
                if (c3.isDead())
                    System.out.println(c3.getName()+" has died.");
            }
        }
        else
        {
            if (arr1.size() > 0 && e1.getHealth() > 0)
            {
                e1.hit(c1.getStrength(), arr1.get(0).getType(), arr1.size());
                System.out.println(c1.getName()+" hit to "+e1.getName()+". Hearth left: "+e1.getHealth());
                if (e1.isDead())
                    System.out.println(c1.getName()+" has died.");
            }
            if (arr2.size() > 0 && e2.getHealth() > 0){
                e2.hit(c2.getStrength(), arr2.get(0).getType(), arr2.size());
                System.out.println(c1.getName()+" hit to "+e2.getName()+". Hearth left: "+e2.getHealth());
                if (e2.isDead())
                    System.out.println(c2.getName()+" has died.");
            }
            if (arr3.size() > 0 && e3.getHealth() > 0)
            {
                e3.hit(c3.getStrength(), arr3.get(0).getType(), arr3.size());
                System.out.println(c1.getName()+" hit to "+e3.getName()+". Hearth left: "+e3.getHealth());
                if (e3.isDead())
                    System.out.println(e3.getName()+" has died.");
            }
        }
        checkWin();
    }

    /**
     * Checks if all the enemies or characters are dead.
     */
    public void checkWin()
    {
        if (e1.isDead() && e2.isDead() && e3.isDead())
        {
            System.out.println("Win!");
        }
        else if (c1.isDead() && c2.isDead() && c3.isDead())
        {
            System.out.println("Game Over!");
        }
    }

    public void reset()
    {
        c1 = new Char();
        c2 = new Char();
        c3 = new Char();

        e1 = new Enemy();
        e2 = new Enemy();
        e3 = new Enemy();
    }
}
