package PowerUp;

import java.awt.*;

/**
 * DecoratorA class used to multiply variable by 2
 */
public class DecoratorA implements IPowerUp
{
    private int multiplier = 2;
    private IPowerUp pu;

    public DecoratorA(IPowerUp basePu)
    {
        this.pu = basePu;
    }

    @Override
    public long PowerUpOp()
    {
        return pu.PowerUpOp()*this.multiplier;
    }

}
