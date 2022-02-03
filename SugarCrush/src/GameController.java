import GemCreator.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Game Controller is the panel of the gem table.
 */
public class GameController extends JPanel implements Runnable, ActionListener, KeyListener
{
    private final MainMenu menu;
    private Thread worldThread = null;
    private boolean first_start = false;
    private boolean start_game = false;
    private boolean stop = false;

    private boolean paused = false;
    private Object pauseLocker = new Object();
    private CharacterPanel characterPanel;

    private ImageIcon background = new ImageIcon(this.getClass().getResource("Assets/background.jpg"));
    private Image backgroundImg;
    private final myMouseListener ml = new myMouseListener(this);
    private static int rowSugarPanel = 6;
    private static int colSugarPanel = 9;

    private GemCreator gemCreator = new GemCreator(rowSugarPanel, colSugarPanel, this);
    private JTextArea logger;
    private Gem selectedGem;

    public GameController(CharacterPanel cp, JTextArea logger)
    {
        this.logger = logger;
        menu = new MainMenu(this);
        this.characterPanel = cp;
        addMouseListener(ml);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
        backgroundImg = background.getImage().getScaledInstance(450, 300, Image.SCALE_SMOOTH);
    }

    /**
     * Main thread function to update game table at each frame.
     */
    @Override
    public void run() {
        while (!stop)
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gemCreator.updateList();
                    repaint();
                    characterPanel.repaint();
                }
            });

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e) { }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String command = e.getActionCommand();

                if (command.compareTo("Start") == 0)
                {
                    StartGame();
                }
                else if (command.compareTo("Resume") == 0)
                {
                    resume();
                }
                else if (command.compareTo("Exit") == 0)
                {
                    System.exit(-1);
                }
            }
        });
    }

    /**
     * StartGame function shuffles the game table and starts the game.
     */
    public void StartGame()  {
        try {
            if (worldThread != null)
            {
                stop = true;
                worldThread.join();
                worldThread = null;
            }
        }
        catch (InterruptedException e) {
            System.exit(-1);
        }

        stop = false;

        gemCreator.shuffle();
        characterPanel.reset();
        first_start = true;
        start_game = true;
        logger.setText("");

        worldThread = new Thread(this);
        worldThread.start();
        System.out.println("Game Started!");
    }

    /**
     * Pauses the game by stopping the main thread
     */
    public void pause() throws InterruptedException {
        start_game = false;
    }

    /**
     * Resumes the game by stopping the main thread
     */
    public void resume()
    {
        start_game = true;
    }

    /**
     * Paints the components background and gemlist.
     * @param g Graphics g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (start_game)
        {
            removeMenu();
            paintBackground(g);
            paintGemList(g);
        }
        else
        {
            printMenu(g);
        }
        grabFocus();
    }

    /**
     * Paints the gems in the gem table
     * @param g Graphics
     */
    public void paintGemList(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        Gem[][] gemList = gemCreator.getGemList();

        for (int i = 0; i < rowSugarPanel; i++) {
            for (int j = 0; j < colSugarPanel; j++) {
                if (gemList[i][j] != null)
                {
                    g2d.drawImage(gemList[i][j].getStyle(), gemList[i][j].getPoint().x, gemList[i][j].getPoint().y, null);
                }
            }
        }
    }

    /**
     * Paints the background of the gem table
     * @param g Graphics
     */
    public void paintBackground(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(backgroundImg, 0, 0, null);
    }

    /**
     * Selects the gem in the point p
     * @param p Gem coordinates
     * @return
     */
    public Gem SelectGem(Point p)
    {
        if (!gemCreator.isAvailable() || p == null)
            return null;

        int c = p.x / GemCreator.getW();
        int r = p.y / GemCreator.getH();

        Gem g = gemCreator.getGem(r, c);

        if (selectedGem == null)
        {
            g.Select();
            selectedGem = g;
        }
        else
        {
            selectedGem.UnSelect();
            gemCreator.startShifting(g, selectedGem);
            selectedGem = null;
        }

        return g;
    }

    /**
     * Sends the tiles to the character panel to damage characters and enemies.
     * @param arr Tile array
     * @param mod Mod is 1 if it is computer turn, otherwise, 0.
     */
    public void damage(ArrayList<Gem> arr, int mod)
    {
        characterPanel.damage(arr, mod);
    }

    public void mousePressed(MouseEvent e)
    {
        if (!start_game)
            return;

        Point p = getMousePosition();
        SelectGem(p);
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

        if (first_start) {
            super.add(menu.getButtonResume());
        }
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

    @Override
    public void keyPressed(KeyEvent e) {
        if (start_game) {
            try
            {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    pause();
                    repaint();
                }
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
}
