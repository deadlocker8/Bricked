package de.bricked.game.powerups.ball;


import de.bricked.game.balls.Ball;
import de.bricked.game.powerups.PowerUp;

public class IncreaseBallSizePowerUp extends PowerUp
{
    private Ball ball;

    public IncreaseBallSizePowerUp(int id, int duration, Ball ball)
    {
        super(id, duration);
        this.ball = ball;
    }

    @Override
    public void activate()
    {

    }
}
