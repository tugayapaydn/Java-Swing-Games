
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * MainWindow includes the game window and a logger panel.
 */
public class MainWindow extends JFrame /*implements ActionListener*/
{
    //Game panel dimensions
    final private int worldWidth = 1200;
    final private int worldHeight = 500;

    //Logger panel dimensions
    final private int loggerHeight = 200;

    private JPanel mainMenu = new JPanel();
    private World world = new World(worldWidth, worldHeight);

    final protected static JTextArea logger = new JTextArea();

    public MainWindow()
    {
        // Settings of main window
        super.setTitle("Uçan Tekme A.Ş Game");
        super.setSize(worldWidth, worldHeight+loggerHeight);
        super.setPreferredSize(new Dimension(worldWidth, worldHeight+loggerHeight));
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setLayout(new GridBagLayout());

        // Settings of world and logger panels
        logger.setSize(worldWidth, loggerHeight);
        logger.setEditable(false);
        logger.setFocusable(false);
        logger.setMaximumSize(new Dimension(worldWidth, loggerHeight));
        logger.setLineWrap(true);
        ((DefaultCaret)logger.getCaret()).setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        // Main window design settings
        JScrollPane sp = new JScrollPane(logger);
        sp.setSize(new Dimension(worldWidth, loggerHeight));
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 3.0;
        c.weighty = 3.0;
        c.gridx = 0;
        c.gridwidth = worldWidth;
        c.fill = GridBagConstraints.BOTH;
        super.add(world, c);

        c.weightx = 0.5;
        c.weighty = 0.7;
        c.gridx = 1;
        super.add(sp, c);
        super.pack();
    }

    public static void main (String[] args)
    {
        new MainWindow();
    }
}
