import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel
{
    private JButton ButtonStart = new JButton("Start");
    private JButton ButtonResume = new JButton("Resume");
    private JButton ButtonExit = new JButton("Exit");

    public MainMenu(ActionListener listener)
    {
        ButtonStart.setBounds(500, 200,150,50);
        ButtonStart.addActionListener(listener);

        ButtonResume.setBounds(500, 300,150,50);
        ButtonResume.addActionListener(listener);

        ButtonExit.setBounds(500, 400,150,50);
        ButtonExit.addActionListener(listener);
    }

    public JButton getButtonStart() { return ButtonStart; }
    public JButton getButtonResume() { return ButtonResume; }
    public JButton getButtonExit() { return ButtonExit; }
}