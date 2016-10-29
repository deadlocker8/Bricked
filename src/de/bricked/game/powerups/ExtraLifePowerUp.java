package de.bricked.game.powerups;


public class ExtraLifePowerUp extends PowerUp
{

    public ExtraLifePowerUp()
    {
        super(PowerUpType.valueOf("EXTRA_LIFE").getId(), -1);
    }

    @Override
    public void activate()
    {

    }
}
