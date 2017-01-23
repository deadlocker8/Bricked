package de.bricked.game.powerups.ball;

import de.bricked.game.Game;
import de.bricked.game.balls.Ball;
import de.bricked.game.balls.BallType;
import de.bricked.game.powerups.PowerUp;
import de.bricked.game.powerups.PowerUpType;
import de.bricked.ui.LevelController;

public class NoCollisionBallPowerUp extends PowerUp
{
    public NoCollisionBallPowerUp()
    {
    	  super(PowerUpType.NO_COLLISION_BALL.getID(), PowerUpType.NO_COLLISION_BALL.getDurationInSeconds());
    }

    @Override
    public void activate(LevelController levelController, Game game)
    {
    	levelController.changeBall(new Ball(BallType.NO_COLLISION, levelController.getGamePaneHeight()));
    }

	@Override
	public void deactivate(LevelController levelController, Game game)
	{	
		levelController.changeBall(new Ball(BallType.NORMAL, levelController.getGamePaneHeight()));
	}
}