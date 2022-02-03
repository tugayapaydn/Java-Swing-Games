package PowerUp;

/**
 * DecoratorA class used to multiply variable by 10
 */
public class DecoratorC implements IPowerUp
{
    private int multiplier = 10;
    private IPowerUp pu;

    public DecoratorC(IPowerUp basePu)
    {
        this.pu = basePu;
    }

    @Override
    public long PowerUpOp()
    {
        return pu.PowerUpOp()*this.multiplier;
    }

}
