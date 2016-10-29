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
        super(PowerUpType.valueOf("NO_COLLISION_BALL").getId(), -1);
    }

    @Override
    public void activate(LevelController levelController, Game game)
    {
    	levelController.changeBall(new Ball(BallType.NO_COLLISION));
    }

	@Override
	public void deactivate(LevelController levelController, Game game)
	{		
	}
}