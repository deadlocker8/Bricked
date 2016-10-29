package de.bricked.game.powerups;


public enum PowerUpType
{
    EXTRA_LIFE(1, -1),
    FASTER_BALL(2, 10),
    SLOWER_BALL(3, 10),
    EXPLODE_BALL(4, 10),
    NO_COLLISION_BALL(5, 10);   

    private int id;
    private int durationInSeconds;

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
}