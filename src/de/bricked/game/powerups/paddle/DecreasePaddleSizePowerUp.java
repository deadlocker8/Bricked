package de.bricked.game.powerups.paddle;

import de.bricked.game.paddle.Paddle;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.game.powerups.PowerUp;


public class DecreasePaddleSizePowerUp extends PowerUp
{
    private Paddle paddle;

    public DecreasePaddleSizePowerUp(int id, int duration, Paddle paddle)
    {
        super(id, duration);
        this.paddle = paddle;
    }

    @Override
    protected void activate()
    {
        paddle.setPaddleSize(PaddleSize.getNextSmaller(paddle.getPaddleSize()));
    }
}