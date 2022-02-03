import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is used to write output stream to a JTextArea(logger)
 */
public class JTextAreaOutputStream extends OutputStream
{
    private final JTextArea logger;

    public JTextAreaOutputStream (JTextArea logger)
    {
        this.logger = logger;
        ((DefaultCaret)logger.getCaret()).setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
    }

    public JTextArea getLogger() {
        return logger;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable ()
        {
            @Override
            public void run()
            {
                logger.append (text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }
}
