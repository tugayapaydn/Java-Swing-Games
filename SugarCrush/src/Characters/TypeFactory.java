package Characters;

import Characters.Style.Style;
import Characters.Type.Type;
import java.util.Random;

/**
 * TypeFactory is used to create random types (Fire, Ice, Nature) of Type class.
 */
public class TypeFactory implements AbstractFactory
{

    /**
     * Returns a new randomly created type
     * @return Type
     */
    @Override
    public Type getType()
    {
        int rand = new Random().nextInt(3);

        switch (rand)
        {
            case 0:
                return new Characters.Type.Fire();
            case 1:
                return new Characters.Type.Ice();
            case 2:
                return new Characters.Type.Nature();
            default:
                return null;
        }
    }

    @Override
    public Style getStyle() {
        return null;
    }
}
