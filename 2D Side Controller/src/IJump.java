public interface IJump
{
    int JUMP_SPEED = 5;
    int LAND_SPEED = 5;
    void setJumping();
    void jump();
    boolean isLanded();
}
