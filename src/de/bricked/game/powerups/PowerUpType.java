package de.bricked.game.powerups;


public enum PowerUpType
{
    EXTRA_LIFE(1),
    FASTER_BALL(2),
    SLOWER_BALL(3),
    EXPLODE_BALL(4),
    NO_COLLISION_BALL(5);   

    private int id;

    PowerUpType(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
