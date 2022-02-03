package Characters;

/**
 * AbstractFactory interface includes Type and Style class functions.
 */
public interface AbstractFactory
{
    public Characters.Type.Type getType();
    public Characters.Style.Style getStyle();
}
