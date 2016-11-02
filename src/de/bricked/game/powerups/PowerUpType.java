package de.bricked.game.powerups;


import de.bricked.game.powerups.ball.ExplodeBallPowerUp;
import de.bricked.game.powerups.ball.NoCollisionBallPowerUp;

public enum PowerUpType
{
    NONE(0, -1),
    EXTRA_LIFE(1, -1),
    FASTER_BALL(2, 10),
    SLOWER_BALL(3, 10),
    EXPLODE_BALL(4, 10),
    NO_COLLISION_BALL(5, 10);

    private int id;
    private int durationInSeconds;
    public static PowerUpType[] types = PowerUpType.values();

    PowerUpType(int id, int durationInSeconds)
    {
        this.id = id;
        this.durationInSeconds = durationInSeconds;
    }

    public int getId()
    {
        return id;
    }

    public int getDurationInSeconds()
    {
        return durationInSeconds;
    }

    public static PowerUp getInstance(PowerUpType powerUpType)
    {
        switch (powerUpType)
        {
            case EXTRA_LIFE: return new ExtraLifePowerUp();
            case FASTER_BALL: return null;
            case SLOWER_BALL: return null;
            case EXPLODE_BALL: return new ExplodeBallPowerUp();
            case NO_COLLISION_BALL: return new NoCollisionBallPowerUp();
            default: return null;
        }
    }
}