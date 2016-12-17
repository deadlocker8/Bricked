package de.bricked.game.paddle;

public class Paddle
{
	private double height;
    private PaddleSize paddleSize;
	private double SPEED_FACTOR = 0.03;
	private double speed;
    private double gameWidth;
	
	public Paddle(PaddleSize size, double initialHeight, double gameWidth)
	{
        this.gameWidth = gameWidth;
        this.paddleSize = size;
		this.height = initialHeight;		
		this.speed = SPEED_FACTOR * gameWidth;
	}

	public double getWidth()
	{
		return gameWidth * paddleSize.getSizeFactor() + paddleSize.getSizeFactor();
	}
	
	public double getHeight()
	{
		return height;
	}

    public PaddleSize getPaddleSize()
    {
        return paddleSize;
    }

    public void setPaddleSize(PaddleSize paddleSize)
    {
        this.paddleSize = paddleSize;
    }

    public double getSpeed()
	{
		return speed; 
	}
}