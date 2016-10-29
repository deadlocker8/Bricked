package de.bricked.game.powerups;

public abstract class PowerUp
{
    protected int id;
    protected int duration;
    protected final double speed = 2.0;  

    public PowerUp(int id, int duration)
    {
        this.id = id;
        this.duration = duration;
    }
    
    public int getID()
    {
    	return id;
    }

    public boolean isPermanent()
    {
        return (duration == -1);
    }    

    public double getSpeed()
	{
		return speed;
	} 

	public abstract void activate();
}