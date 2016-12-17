package de.bricked.game.powerups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.bricked.game.powerups.ball.ExplodeBallPowerUp;
import de.bricked.game.powerups.ball.NoCollisionBallPowerUp;


public enum PowerUpType
{
    NONE(0, -1, null),
    EXTRA_LIFE(1, -1, null),
    FASTER_BALL(2, 10, Arrays.asList(3)),
    SLOWER_BALL(3, 10, Arrays.asList(2, 4, 5)),
    EXPLODE_BALL(4, 10, Arrays.asList(2, 3, 5)),
    NO_COLLISION_BALL(5, 10, Arrays.asList(2, 3, 4));

    private int ID;
    private int durationInSeconds;
    private List<Integer> deactivatesPowerUpIDs;
    public static PowerUpType[] types = PowerUpType.values();

    PowerUpType(int ID, int durationInSeconds, List<Integer> deactivatesPowerUpdIDs)
    {
        this.ID = ID;
        this.durationInSeconds = durationInSeconds;
        this.deactivatesPowerUpIDs = deactivatesPowerUpdIDs;
    }

    public int getID()
    {
        return ID;
    }

    public int getDurationInSeconds()
    {
        return durationInSeconds;
    }    

    public ArrayList<Integer> getDeactivatesPowerUpIDs()
	{
		return new ArrayList<>(deactivatesPowerUpIDs);
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
	
	public static PowerUpType valueOf(int ID)
	{
		for(PowerUpType currentType : PowerUpType.values())
		{
			if(ID == currentType.getID())
			{
				return currentType;
			}
		}
		return PowerUpType.NONE;
	}
}