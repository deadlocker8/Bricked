package de.bricked.game.powerups.ball;

import de.bricked.game.Game;
import de.bricked.game.balls.Ball;
import de.bricked.game.balls.BallType;
import de.bricked.game.powerups.PowerUp;
import de.bricked.game.powerups.PowerUpType;
import de.bricked.ui.LevelController;

public class ExplodeBallPowerUp extends PowerUp
{
    public ExplodeBallPowerUp()
    {
    	  super(PowerUpType.EXPLODE_BALL.getId(), PowerUpType.EXPLODE_BALL.getDurationInSeconds());
    }

    @Override
    public void activate(LevelController levelController, Game game)
    {
    	levelController.changeBall(new Ball(BallType.EXPLOSIVE));
    }

	@Override
	public void deactivate(LevelController levelController, Game game)
	{		
		levelController.changeBall(new Ball(BallType.NORMAL));
	}
}