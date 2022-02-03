package PowerUp;

/**
 * Base decorator class, Multiplier class defines power up multiplications
 */
public class Multiplier implements IPowerUp
{
    private long base_multiplier;

    public Multiplier(long numb)
    {
        this.base_multiplier = numb;
    }

    /**
     * General power up operation to multiply number with multiplier.
     */
    @Override
    public long PowerUpOp() {
        return this.base_multiplier;
    }
}
