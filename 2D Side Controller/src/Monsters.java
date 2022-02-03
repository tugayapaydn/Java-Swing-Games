import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Monsters
{
    private final int MONSTER_CAP;
    private final int MAXIMUM_DISTANCE;
    private final int MINIMUM_DISTANCE;

    protected final int height = 90;
    protected final int width = 60;

    // Monster custom image
    private final Image monsterStandImg = new ImageIcon(this.getClass().getResource("assets/monster.png")).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

    // Start point of monster
    private int startPointX;

    // Coordinates of created monsters
    private List<Integer> monsterCoords = null;

    private int farPoint = 0;

    /* Getter functions */
    public Image getImage()
    {
        return monsterStandImg;
    }
    public List<Integer> getMonsterCoords()
    {
        return monsterCoords;
    }

    public Monsters(int startX, int monster_cap, int maximum_distance, int minimum_distance)
    {
        this.startPointX = startX;
        this.MONSTER_CAP = monster_cap;
        this.MAXIMUM_DISTANCE = maximum_distance;
        this.MINIMUM_DISTANCE = minimum_distance;
    }

    /**
     * Randomly creates number of MONSTER_CAP monster coordinates.
     */
    public void createMonsterCoords()
    {
        this.monsterCoords = new LinkedList<Integer>();

        Random rand = new Random();

        farPoint = 0;
        int random;

        for (int i = 0; i < MONSTER_CAP; i++)
        {
            int randomInt = rand.nextInt();
            if (randomInt < 0) { randomInt *= -1; }

            if (i == 0) { // For the first monster
                random = startPointX + (randomInt % MAXIMUM_DISTANCE) + MINIMUM_DISTANCE;
            }
            else {
                random = farPoint + (randomInt % MAXIMUM_DISTANCE) + MINIMUM_DISTANCE;
            }

            monsterCoords.add(random);
            farPoint = random;
        }
    }

    /**
     * Adds a new randomly located monster to the monster set.
     */
    public void addMonster()
    {
        Random rand = new Random();
        int randomInt = rand.nextInt();
        if (randomInt < 0)
        {
            randomInt *= -1;
        }

        randomInt = farPoint + (randomInt % MAXIMUM_DISTANCE) + MINIMUM_DISTANCE;
        monsterCoords.add(monsterCoords.size()-1, randomInt);
        farPoint = randomInt;
    }

    /**
     * Removes the first monster from the set.
     */
    public void removeFirstMonster()
    {
        monsterCoords.remove(0);
    }
}
