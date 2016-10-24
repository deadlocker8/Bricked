package de.bricked.game.powerups;

public abstract class PowerUp
{
    protected int id;
    protected int duration;

    public PowerUp(int id, int duration)
    {
        this.id = id;
        this.duration = duration;
    }

    public boolean isPermanent()
    {
        return (duration == -1);
    }

    protected abstract void activate();


}