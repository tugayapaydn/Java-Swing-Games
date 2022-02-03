import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MouseListener class sends signals of mouse to the game controller
 */
public class myMouseListener extends MouseAdapter
{
    private GameController gc;

    public myMouseListener (GameController gc)
    {
        this.gc = gc;

    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        gc.mousePressed(e);
    }
}
