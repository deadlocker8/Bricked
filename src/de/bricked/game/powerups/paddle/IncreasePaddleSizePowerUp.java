package de.bricked.game.powerups.paddle;

import de.bricked.game.Game;
import de.bricked.game.paddle.Paddle;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.game.powerups.PowerUp;
import de.bricked.ui.LevelController;


public class IncreasePaddleSizePowerUp extends PowerUp
{
    private Paddle paddle;

    public IncreasePaddleSizePowerUp(int id, int duration, Paddle paddle)
    {
        super(id, duration);
        this.paddle = paddle;
    }

    @Override
    public void activate(LevelController levelController, Game game)
    {
        paddle.setPaddleSize(PaddleSize.getNextBigger(paddle.getPaddleSize()));
    }

	@Override
	public void deactivate(LevelController levelController, Game game)
	{				
	}
}