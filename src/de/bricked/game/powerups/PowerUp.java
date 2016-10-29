package de.bricked.game.powerups;

import de.bricked.game.Game;
import de.bricked.ui.LevelController;

public abstract class PowerUp
{
    protected int id;
    protected int durationInSeconds;
    protected final double speed = 2.0;  

    public PowerUp(int id, int durationInSeconds)
    {
        this.id = id;
        this.durationInSeconds = durationInSeconds;
    }
    
    public int getID()
    {
    	return id;
    }

    public boolean isPermanent()
    {
        return (durationInSeconds == -1);
    }
    
    public int getDurationInSeconds()
    {
    	return durationInSeconds;
    }

    public double getSpeed()
	{
		return speed;
	} 

	public abstract void activate(LevelController levelController, Game game);
	
	public abstract void deactivate(LevelController levelController, Game game);
}