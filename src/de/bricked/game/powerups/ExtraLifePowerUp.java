package de.bricked.game.powerups;

import de.bricked.game.Game;
import de.bricked.ui.LevelController;

public class ExtraLifePowerUp extends PowerUp
{
    public ExtraLifePowerUp()
    {
        super(PowerUpType.valueOf("EXTRA_LIFE").getId(), 10);
    }

	@Override
	public void activate(LevelController levelController, Game game)
	{
		//TODO
	}

	@Override
	public void deactivate(LevelController levelController, Game game)
	{		
	}   
}