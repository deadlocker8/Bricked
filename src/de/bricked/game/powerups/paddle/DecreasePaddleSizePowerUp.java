package de.bricked.game.powerups.paddle;

import de.bricked.game.Game;
import de.bricked.game.paddle.Paddle;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.game.powerups.PowerUp;
import de.bricked.ui.LevelController;


public class DecreasePaddleSizePowerUp extends PowerUp
{
    private Paddle paddle;

    public DecreasePaddleSizePowerUp(int id, int duration, Paddle paddle)
    {
        super(id, duration);
        this.paddle = paddle;
    }

    @Override
    public void activate(LevelController levelController, Game game)
    {
        paddle.setPaddleSize(PaddleSize.getNextSmaller(paddle.getPaddleSize()));
    }

	@Override
	public void deactivate(LevelController levelController, Game game)
	{				
	}
}
