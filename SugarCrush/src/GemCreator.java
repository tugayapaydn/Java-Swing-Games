import Characters.Type.ColorType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import GemCreator.*;

/**
 * GemCreator class handles all functionalities of the game window.
 */
public class GemCreator
{
    private GameController gc;

    // Gem images
    private Image redGemStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/RedGem.png")).getImage();
    private Image blueGemStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/GreenGem.png")).getImage();
    private Image greenGemStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/BlueGem.png")).getImage();

    private Image redGemSelectedStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/RedGemSelected.png")).getImage();
    private Image blueGemSelectedStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/GreenGemSelected.png")).getImage();
    private Image greenGemSelectedStyle = new ImageIcon(this.getClass().getResource("GemCreator/Assets/BlueGemSelected.png")).getImage();

    private static int w = 50;
    private static int h = 50;

    int r, c;

    private boolean available = true;

    private Gem gemList[][];

    private boolean startedWork = false;
    private boolean computerTurn = false;
    private boolean userPlayed = false;

    // Shifting fields
    private int SHIFT_SPEED = 5;
    private boolean shifting = false;
    private boolean shiftBack = false;
    private Gem[] shiftingGems = new Gem[2];
    private Point[] shiftingPoints = new Point[2];

    private boolean removing = false;
    private ArrayList<Gem> removingGems = new ArrayList<Gem>();

    private boolean shiftUp = false;
    private ArrayList<Gem> upShiftingGems = new ArrayList<Gem>();
    private ArrayList<Point> upShiftingPoints = new ArrayList<Point>();

    public boolean isAvailable() {
        return !startedWork;
    }

    /**
     * Constructor creates all images for gems
     * @param r Number of rows in the table
     * @param c Number of columns in the table
     * @param gc GameController class instance
     */
    public GemCreator(int r, int c, GameController gc)
    {
        if (redGemStyle == null || greenGemStyle == null || blueGemStyle == null)
        {
            System.out.println("Failure: GemCreator");
            System.exit(-1);
        }
        this.gc = gc;
        redGemStyle = redGemStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        greenGemStyle = greenGemStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        blueGemStyle = blueGemStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        redGemSelectedStyle = redGemSelectedStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        greenGemSelectedStyle = greenGemSelectedStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        blueGemSelectedStyle = blueGemSelectedStyle.getScaledInstance(w, h, Image.SCALE_SMOOTH);

        this.r = r;
        this.c = c;
        gemList = new Gem[r][c];
    }

    /**
     * Returns a gem from the list
     * @param r Row
     * @param c Column
     * @return Gem
     */
    public Gem getGem(int r, int c)
    {
        return gemList[r][c];
    }

    /**
     * Returns gem list
     * @return Gem[][]
     */
    public Gem[][] getGemList() {
        return gemList;
    }

    /**
     * Returns height of the game panel
     * @return int
     */
    public static int getH() {
        return h;
    }

    /**
     * Returns width of the game panel
     * @return int
     */
    public static int getW() {
        return w;
    }

    /**
     * Returns a randomly generated gem.
     * @param r Row of the gem
     * @param c Column of the gem
     * @return Gem
     */
    public Gem createGem(int r, int c)
    {
        Image style = null;
        Image selectedStyle = null;
        ColorType type = null;

        Random random = new Random();
        int next = random.nextInt(3);

        switch (next)
        {
            case 0:
                style = this.redGemStyle;
                selectedStyle = this.redGemSelectedStyle;
                type = ColorType.RED;
                break;
            case 1:
                style = this.greenGemStyle;
                selectedStyle = this.greenGemSelectedStyle;
                type = ColorType.GREEN;
                break;
            case 2:
                style = this.blueGemStyle;
                selectedStyle = this.blueGemSelectedStyle;
                type = ColorType.BLUE;
                break;
            default:
                System.out.println("CREATE GEM DE BI PROBLEM VAR USTAM BI BAK.");
                System.exit(-1);
                break;
        }

        int x = r*w;
        int y = c*h;

        return new Gem(style, selectedStyle, new Point(y, x), w, type);
    }

    /**
     * This method is called for each frame to update the game panel.
     * It includes shifting, removing, upshifting functionalities.
     */
    public void updateList()
    {
        if (shifting /*&& !removing && !shiftUp*/)
        {
            Gem g1 = shiftingGems[0];
            Gem g2 = shiftingGems[1];
            Point p1 = shiftingPoints[0];
            Point p2 = shiftingPoints[1];

            shiftGem(g1, p1);
            shiftGem(g2, p2);

            if (g1.getPoint().x == p1.getX() && g1.getPoint().y == p1.getY())
            {
                shifting = false;
                boolean b1 = checkMatchingGems(g1);
                boolean b2 = checkMatchingGems(g2);

                if (shiftBack)
                {
                    shiftBack = false;
                    //Removal will start automatically
                }
                else
                {
                    if (!b1 && !b2)
                    {
                        startShifting(g1, g2);
                        shiftBack = true;
                    }
                    else
                    {
                        //Removal will start automatically
                    }
                }
            }
        }
        else if (removing /*&& !shifting && !shiftUp && !shiftBack*/)
        {
            updateRemoving();
        }
        else if (shiftUp /*&& !shifting && !removing && !shiftBack*/)
        {
            updateUpShifting();
        }
        else
        {
            if (isWorkDone())
            {
                if (computerTurn)
                {
                    checkTable();
                    startedWork = false;
                    computerTurn = false;
                    available = true;
                }
                else
                {
                    computerTurn = true;
                    computerTurn();
                }
            }
        }
    }

    /**
     * Defines whether the next user's move is done
     * @return
     */
    private boolean isWorkDone()
    {
        if (startedWork && !shifting && !shiftBack && !removing && !shiftUp)
        {
            return true;
        }
        return false;
    }

    /**
     * Shuffles the game table
     */
    public void shuffle()
    {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                do {
                    gemList[i][j] = createGem(i, j);
                } while ((getHorizontalMatchingGems(gemList[i][j]) != null || getVerticalMatchingGems(gemList[i][j]) != null));
            }
        }
    }

    /**
     * Shifts 2 gems in the table.
     * If there is no match, shifts back the gems. If there is a match,
     * it calls removing function.
     * @param g1 Gem 1
     * @param g2 Gem 2
     */
    public void startShifting(Gem g1, Gem g2)
    {
        if (!checkAdjacent(g1, g2))
            return;

        startedWork = true;
        shifting = true;

        int r1 = g1.getPoint().y / GemCreator.getH();
        int c1 = g1.getPoint().x / GemCreator.getW();
        int r2 = g2.getPoint().y / GemCreator.getH();
        int c2 = g2.getPoint().x / GemCreator.getW();

        gemList[r1][c1] = g2;
        gemList[r2][c2] = g1;

        shiftingGems[0] = g1;
        shiftingGems[1] = g2;
        shiftingPoints[0] = new Point(g2.getPoint());
        shiftingPoints[1] = new Point(g1.getPoint());
    }

    /**
     * Shifts 2 Gem in the table
     * @param g
     * @param p
     */
    public void shiftGem(Gem g, Point p)
    {
        Point gemP = g.getPoint();
        int sx = 1;
        int sy = 1;

        if (p.x < gemP.x)
            sx = -1;
        else if (p.x == gemP.x)
            sx = 0;

        if (p.y < gemP.y)
            sy = -1;
        else if (p.y == gemP.y)
            sy = 0;

        g.setPoint(new Point(gemP.x + (sx * SHIFT_SPEED), gemP.y + (sy * SHIFT_SPEED)));
    }

    /**
     * Checks if there are any matching gems for a given gem
     * @param g Gem
     * @return True if there are matching gems, otherwise, false.
     */
    public boolean checkMatchingGems(Gem g)
    {
        ArrayList<Gem> arrh = getHorizontalMatchingGems(g);
        ArrayList<Gem> arrv = getVerticalMatchingGems(g);

        if (arrh == null && arrv == null)
        {
            return false;
        }
        else
        {
            setRemoving(arrh);
            setRemoving(arrv);
            return true;
        }
    }

    /**
     * Checks if there are any matching gems in the horizontal row for a given gem
     * @param g Gem
     * @return True if there are matching gems, otherwise, false.
     */
    private ArrayList<Gem> getHorizontalMatchingGems(Gem g)
    {
        ArrayList<Gem> matchingGems = new ArrayList<Gem>();

        int r1 = g.getPoint().y / GemCreator.getH();
        int c1 = g.getPoint().x / GemCreator.getW();

        int total = 1;
        matchingGems.add(g);
        for (int i = c1-1; i >= 0 && gemList[r1][i] != null && gemList[r1][i].equals(g); i--)
        {
            total++;
            matchingGems.add(gemList[r1][i]);
        }
        for (int i = c1+1; i < this.c && gemList[r1][i] != null && gemList[r1][i].equals(g); i++)
        {
            total++;
            matchingGems.add(gemList[r1][i]);
        }

        if (total >= 3)
            return matchingGems;
        else
            return null;
    }

    /**
     * Checks if there are any matching gems in the vertical column for a given gem.
     * @param g Gem
     * @return True if there are matching gems, otherwise, false.
     */
    private ArrayList<Gem> getVerticalMatchingGems(Gem g)
    {
        ArrayList<Gem> matchingGems = new ArrayList<Gem>();

        int r1 = g.getPoint().y / GemCreator.getH();
        int c1 = g.getPoint().x / GemCreator.getW();

        int total = 1;
        matchingGems.add(g);

        for (int i = r1-1; i >= 0 && gemList[i][c1] != null && gemList[i][c1].equals(g); i--)
        {
            total++;
            matchingGems.add(gemList[i][c1]);
        }
        for (int i = r1+1; i < this.r && gemList[i][c1] != null && gemList[i][c1].equals(g); i++)
        {
            total++;
            matchingGems.add(gemList[i][c1]);
        }

        if (total >= 3)
            return matchingGems;
        else
            return null;
    }

    /**
     * Starts removing animation for the given list of gems.
     * @param gList Gem List
     */
    private void setRemoving(ArrayList<Gem> gList)
    {
        if (gList == null)
            return;

        hit(gList);
        this.removingGems.addAll(gList);
        this.removing = true;
    }

    /**
     * Handles the hit (damage) to any char.
     * @param gList The gem list that will damage to the character.
     */
    private void hit (ArrayList<Gem> gList)
    {
        ArrayList<Gem> arr = new ArrayList<Gem>();
        arr.addAll(gList);

        if (computerTurn)
            gc.damage(arr, 1);
        else
            gc.damage(arr, 0);
    }

    /**
     * Handles removing animations for the removing gems.
     */
    private void updateRemoving()
    {
        if (!removing)
            return;

        for (int i = 0; i < removingGems.size(); i++)
        {
            Gem g = removingGems.get(i);
            int r = g.getPoint().y / GemCreator.getH();
            int c = g.getPoint().x / GemCreator.getW();

            if (g.getDim() > 0)
            {
                g.scaleDown();
                gemList[r][c] = g;
            }
            else
            {
                gemList[r][c] = null;
                removingGems.remove(i);
                i--;
            }
        }

        if (removingGems.size() == 0)
        {
            removing = false;
            preShiftList();
            shiftUp = true;
        }
    }

    /**
     * Determines the gems in the table that will be shifted upwards to fill empty spots after removal of the matched gems.
     */
    private void preShiftList()
    {
        int total_move;

        for (int i = 1; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (gemList[i][j] != null && gemList[i-1][j] == null)
                {
                    Gem g = gemList[i][j];

                    total_move = 0;
                    for (int k = i-1; k >= 0 && gemList[k][j] == null; k--,total_move++)
                    {
                            gemList[k][j] = gemList[k+1][j];
                            gemList[k+1][j] = null;
                    }

                    upShiftingGems.add(g);
                    upShiftingPoints.add(new Point(g.getPoint().x, g.getPoint().y-(h*total_move)));
                }
            }
        }
    }

    /**
     * Shifts 1 block all the gems below the given column and row upwards.
     */
    private void updateUpShifting()
    {
        if (!shiftUp)
            return;

        for (int i = 0; i < upShiftingGems.size(); i++)
        {
            Gem g = upShiftingGems.get(i);
            Point targetP = upShiftingPoints.get(i);
            Point p = g.getPoint();

            if (p.y > targetP.y) {
                g.getPoint().setLocation(p.x, p.y - SHIFT_SPEED);
            }
            else
            {
                upShiftingGems.remove(i);
                upShiftingPoints.remove(i);

                int r = g.getPoint().y / GemCreator.getH();
                int c = g.getPoint().x / GemCreator.getW();
            }
        }

        if (upShiftingGems.size() == 0)
        {
            shiftUp = false;
            fillEmptySpots();
        }
    }

    /**
     * Creates random gems for empty spots.
     */
    private void fillEmptySpots()
    {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (gemList[i][j] == null)
                    gemList[i][j] = createGem(i, j);
            }
        }
    }


    /**
     * Checks if the given 2 gems are adjacent.
     * @param g1 Gem 1
     * @param g2 Gem 2
     * @return True if the gems are adjacent, otherwise, false.
     */
    public boolean checkAdjacent(Gem g1, Gem g2)
    {
        Point p1 = g1.getPoint();
        Point p2 = g2.getPoint();

        int xd = Math.abs((p2.x - p1.x));
        int yd = Math.abs((p2.y - p1.y));

        if ((xd == 0 && yd == h) || (yd == 0 && xd == w))
            return true;

        return false;
    }

    /**
     * Checks if there are any matching patterns in the game table. If not, shuffles the game table.
     */
    public void checkTable()
    {
        boolean shuffle = false;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (getVerticalMatchingGems(gemList[i][j]) != null || getHorizontalMatchingGems(gemList[i][j]) != null)
                {
                    return;
                }
            }
        }

        System.out.println("No matching tiles. Shuffling...");
        this.shuffle();
    }

    /**
     * Handles computer turn to make the next move.
     */
    public void computerTurn()
    {
        if (!computerTurn)
        {
            return;
        }

        Random random = new Random();

        int r = random.nextInt(this.r-1);
        int c = random.nextInt(this.c-1);
        Gem g1 = gemList[r][c];
        Gem g2 = null;

        int r2, c2;

        if (r > 0 && c > 0)
        {
            int side = random.nextInt(4);
            switch (side)
            {
                case 0:
                    g2 = getGem(r+1, c);
                    break;
                case 1:
                    g2 = getGem(r-1, c);
                    break;
                case 2:
                    g2 = getGem(r, c-1);
                    break;
                case 3:
                    g2 = getGem(r, c+1);
                    break;
            }
        }
        else
        {
            int side = random.nextInt(2);
            switch (side)
            {
                case 0:
                    g2 = getGem(r, c+1);
                    break;
                case 1:
                    g2 = getGem(r+1, c);
                    break;
            }
        }
        startShifting(g1, g2);
    }
}
