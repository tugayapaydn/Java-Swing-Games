import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.PrintStream;

/**
 * Main Frame of the game window
 */
public class MainWindow {
    private JPanel MainPanel;
    private JPanel panel3;
    private JPanel SugarPanel;
    private JTextArea textArea1;
    private JScrollPane scrollPane1;

    public JPanel getSugarPanel() {
        return SugarPanel;
    }
    public JPanel getMainPanel() {
        return MainPanel;
    }

    public void createUIComponents()
     {
         panel3 = new CharacterPanel();
         textArea1 = new JTextArea();
         MainPanel = new JPanel();
         SugarPanel = new GameController((CharacterPanel)panel3, textArea1);
         SugarPanel.setFocusable(true);

         JTextAreaOutputStream out = new JTextAreaOutputStream (textArea1);
         System.setOut (new PrintStream(out));
         scrollPane1 = new JScrollPane(out.getLogger());
     }

    /**
     * Main method sets a frame and starts the game
     * @param args
     */
    public static void main (String[] args)
    {
        MainWindow w = new MainWindow();

        JFrame frame = new JFrame("Sugar Game");
        frame.setContentPane(w.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        //frame.setFocusable(true);
        frame.pack();

        GameController sp = (GameController)w.getSugarPanel();
    }
}
