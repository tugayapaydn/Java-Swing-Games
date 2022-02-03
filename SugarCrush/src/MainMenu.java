import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainMenu extends JPanel
{
    private JButton ButtonStart = new JButton("Start");
    private JButton ButtonResume = new JButton("Resume");
    private JButton ButtonExit = new JButton("Exit");

    public MainMenu(ActionListener listener)
    {
        ButtonStart.setBounds(50, 50,100,50);
        ButtonStart.addActionListener(listener);

        ButtonResume.setBounds(170, 50,100,50);
        ButtonResume.addActionListener(listener);

        ButtonExit.setBounds(300, 50, 100,50);
        ButtonExit.addActionListener(listener);
    }

    public JButton getButtonStart() { return ButtonStart; }
    public JButton getButtonResume() { return ButtonResume; }
    public JButton getButtonExit() { return ButtonExit; }
}
