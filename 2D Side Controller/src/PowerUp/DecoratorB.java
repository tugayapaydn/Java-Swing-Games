package PowerUp;

/**
 * DecoratorA class used to multiply variable by 5
 */
public class DecoratorB implements IPowerUp
{
    private int multiplier = 5;
    private IPowerUp pu;

    public DecoratorB(IPowerUp basePu)
    {
        this.pu = basePu;
    }

    @Override
    public long PowerUpOp()
    {
        return pu.PowerUpOp()*this.multiplier;
    }


}
