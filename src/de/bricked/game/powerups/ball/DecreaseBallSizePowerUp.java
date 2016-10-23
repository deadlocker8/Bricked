package de.bricked.game.powerups.ball;


import de.bricked.game.balls.Ball;
import de.bricked.game.powerups.PowerUp;

public class DecreaseBallSizePowerUp extends PowerUp
{
    private Ball ball;

    public DecreaseBallSizePowerUp(int id, int duration, Ball ball)
    {
        super(id, duration);
        this.ball = ball;
    }

    @Override
    protected void activate()
    {

    }
}
