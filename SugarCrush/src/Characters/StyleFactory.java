package Characters;

import Characters.Style.Style;
import Characters.Type.Type;
import java.util.Random;

/**
 * StyleFactory is used to create random styles (Atlantis, Valhalla, Underwild) of Type class.
 */
public class StyleFactory implements AbstractFactory
{
    @Override
    public Type getType() {
        return null;
    }

    /**
     * Returns a new randomly created style.
     * @return Stype
     */
    @Override
    public Style getStyle() {

        int rand = new Random().nextInt(3);

        switch (rand) {
            case 0:
                return new Characters.Style.Atlantis();
            case 1:
                return new Characters.Style.Valhalla();
            case 2:
                return new Characters.Style.Underwild();
            default:
                return null;
        }
    }
}
