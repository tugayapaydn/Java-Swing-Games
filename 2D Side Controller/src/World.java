import PowerUp.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * World class presents the game enviroment.
 */
public class World extends JPanel implements ActionListener, KeyListener, Runnable
{
    private Thread worldThread = null;
    private boolean first_start = false;
    private boolean start_game = false;
    private long total_points = 0;
    private final int MONSTER_CAP = 10;
    private boolean paused = false;
    private Object pauseObject = new Object();

    //Power up multiplier
    private IPowerUp pointMultiplier;

    private PowerUpCreator puc;
    private MainCharacter mainChar;
    private Monsters monsters;
    private final MainMenu menu;

    // World dimensions
    private final int height;
    private final int width;

    private final Image background = new ImageIcon(this.getClass().getResource("assets/background.png")).getImage();

    private long FPS = 0;
    private long lastFPSTime = System.nanoTime();

    public World(int width, int height)
    {
        setVisible(true);
        setFocusable(true);

        menu = new MainMenu(this);

        addKeyListener(this);

        setSize(width, height);

        this.width = width;
        this.height = height;
    }

    /**
     * Runs the world on a separated thread. Updates the UI and characters.
     */
    @Override
    public void run() {
        while (true)
        {
            if (paused) {
                try {
                    synchronized (pauseObject)
                    {
                        pauseObject.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            FPSCounter();
            mainChar.update();
            checkCollapse();
            checkPassedMonster();
            checkPowerUpPass();
            checkPowerUpCollapse();
            repaint();

            try {
                Thread.sleep(15);   // Print UI at every 15 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actions performed at every timer interrupt or button action.
     * @param e Timer action or button action
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        FPSCounter();
        String command = e.getActionCommand();

        if (command.compareTo("Start") == 0)
        {
            startGame();
        }
        else if (command.compareTo("Resume") == 0)
        {
            resume();
        }
        else if (command.compareTo("Exit") == 0)
        {
            this.exit();
        }
    }

    /**
     * Initializes main character, monsters and powerups. Then starts the game.
     */
    public void startGame()
    {
        MainWindow.logger.setText("");
        mainChar = new MainCharacter(100, height);
        monsters = new Monsters(100, MONSTER_CAP, this.width, (this.width/2));
        monsters.createMonsterCoords();

        puc = new PowerUpCreator();
        puc.createRandomPowerUp(mainChar.getVirtualCoord().x, width*2, width);

        total_points = 0;
        pointMultiplier = new Multiplier(1);

        first_start = true;
        start_game = true;

        if (worldThread != null)
        {
            this.resume();
        }
        else{
            worldThread = new Thread(this);
            worldThread.start();

        }
    }

    /**
     * Pauses the game by stopping the timer
     */
    public void pause() throws InterruptedException {
        start_game = false;
        paused = true;
    }

    /**
     * Resumes the game by starting the timer
     */
    public void resume()
    {
        start_game = true;
        paused = false;
        synchronized (pauseObject)
        {
            pauseObject.notifyAll();
        }
    }

    public void exit()
    {
        System.exit(0);
    }

    /**
     * Resets the round by resetting enviroment and characters.
     * If the character runs out of life, the game will be over.
     */
    private void resetRound()
    {
        mainChar.reset();
        if (!mainChar.isAlive())
        {
            repaint();
            return;
        }

        total_points = 0;
        pointMultiplier = new Multiplier(1);
        puc.createRandomPowerUp(mainChar.getVirtualCoord().x, width*2, width);
        monsters.createMonsterCoords();
    }

    /**
     * Checks if the hero collapses to the next power up.
     */
    private void checkPowerUpCollapse()
    {
        if (puc.checkCollapse(mainChar.getVirtualCoord(), mainChar.getHeight(), mainChar.getWidth()))
        {
            MainWindow.logger.append("Got POWERUP: " + puc.getPowerUpType() + System.lineSeparator());

            switch (puc.getPowerUpType()) {
                case A:
                    pointMultiplier = new DecoratorA(pointMultiplier);
                    break;
                case B:
                    pointMultiplier = new DecoratorB(pointMultiplier);
                    break;
                case C:
                    pointMultiplier = new DecoratorC(pointMultiplier);
                    break;
                case D:
                    mainChar.setJump(new HighJump(this.height, mainChar.getHeight(), mainChar.getVirtualCoord(), mainChar.getRealCoord()));
                    break;
                default:
                    MainWindow.logger.append("Failed to obtain powerup!" + System.lineSeparator());
                    break;
            }
            puc.createRandomPowerUp(mainChar.getVirtualCoord().x, width*2, width);
        }
    }

    /**
     * Checks if the hero passed the next power up.
     */
    private void checkPowerUpPass()
    {
        if (puc.checkPassed(mainChar.getVirtualCoord(), mainChar.getWidth()))
        {
            puc.createRandomPowerUp(mainChar.getVirtualCoord().x, width*2, width);
        }
    }

    /**
     * Paints randomly created monsters in the world.
     * @param g Graphics
     */
    private void createRandomCreatures(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        for (Integer integer : monsters.getMonsterCoords()) {
            int next = integer;
            g2d.drawImage(monsters.getImage(), next + mainChar.range, this.height - monsters.height, this);
        }
    }

    /**
     * Checks if the hero collapsed to a monster.
     */
    private void checkCollapse()
    {
        int bottom_threshold = this.height - monsters.height;
        int mainHeroBottom = mainChar.getRealCoord().y+ mainChar.getHeight();

        int m = monsters.getMonsterCoords().get(0);

        if (mainChar.getVirtualCoord().x+mainChar.getWidth() >= m && mainChar.getVirtualCoord().x <= m+ monsters.width)
        {
            if (mainHeroBottom >= bottom_threshold)
            {
                MainWindow.logger.append("FAILED!" + System.lineSeparator());
                resetRound();
            }
        }
    }

    /**
     * Checks if the hero passed a monster
     */
    private void checkPassedMonster()
    {
        int first = monsters.getMonsterCoords().get(0);

        if ((first+monsters.width) < (mainChar.getVirtualCoord().x))
        {
            total_points += pointMultiplier.PowerUpOp();

            MainWindow.logger.append("Earned Points = " + total_points + System.lineSeparator());

            monsters.removeFirstMonster();
            monsters.addMonster();
        }
    }

    /**
     * Counts frame per second.
     */
    private void FPSCounter()
    {
        this.FPS = 1000000000 / (System.nanoTime() - lastFPSTime);
        lastFPSTime = System.nanoTime();
    }

    /**
     * Paints repeated background.
     * @param g Graphics
     */
    private void paintBackground(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        int rangeTemp = mainChar.range;

        if (rangeTemp < (-1 * width))
        {
            rangeTemp %= width;
        }

        g2d.drawImage(background, rangeTemp, 0, width, height, null);
        g2d.drawImage(background, width+rangeTemp, 0, width, height, null);
    }

    /**
     * Paints the next power up's image
     * @param g Graphics
     */
    private void paintPowerUp(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        int h = puc.getPowerUpDim();
        int w = puc.getPowerUpDim();

        g2d.drawImage(puc.getNextPowerUpImage(), puc.getPowerUpCoordX()+mainChar.range, puc.getPowerUpConstantY(), w, h, null);
    }

    /**
     * Prints FPS value to top-left corner.
     * @param g Graphics
     */
    private void paintFPS(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf((int)this.FPS), 10, 10);
    }

    /**
     * Paints the main hero.
     * @param g Graphics
     */
    private void paintMainHero(Graphics g)
    {
        Point charPoint = mainChar.getRealCoord();
        g.drawImage(mainChar.getCharImg(), charPoint.x, charPoint.y, this);
    }

    /**
     * Prints the menu screen.
     * Resume button will only be printed if the game is paused.
     * @param g Graphics
     */
    private void printMenu(Graphics g)
    {
        super.add(menu.getButtonStart());
        super.add(menu.getButtonExit());

        if (first_start && !mainChar.isFailed()) {
            super.add(menu.getButtonResume());
        }

        g.drawImage(background, 0, 0, width, height, null);
    }

    /**
     * Removes the menu from the screen.
     */
    private void removeMenu()
    {
        super.remove(menu.getButtonStart());
        super.remove(menu.getButtonResume());
        super.remove(menu.getButtonExit());
    }

    /**
     * Overrided paintComponent class paints all components in the world.
     * Background, hero, monsters, powerups, menu, fps counter and game over screen.
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (start_game)
        {
            removeMenu();
            paintBackground(g);
            paintMainHero(g);
            createRandomCreatures(g);
            paintPowerUp(g);
            paintFPS(g);

            if (mainChar.isFailed())
                g.drawImage(new ImageIcon(this.getClass().getResource("assets/gameOver.png")).getImage(), 300, 10, 600, 500, this);
        }
        else
        {
            printMenu(g);
        }
        grabFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (start_game) {
            try
            {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    pause();
                    repaint();
                    mainChar.keyPressed(e);
                }
                else
                {
                    mainChar.keyPressed(e);
                }
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (start_game)
        {
            mainChar.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
