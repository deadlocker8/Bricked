package de.bricked.game.paddle;

public class Paddle
{
	private double width;
	private double height;
    private PaddleSize paddleSize;
	private final double MIN_WIDTH = 50.0;
	private final double MAX_WIDTH_PERCENTAGE = 0.4;
	private double MAX_WIDTH;		
	private double SPEED_FACTOR = 0.03;
	private double speed;
	
	public Paddle(PaddleSize size, double initialHeight, double gameWidth)
	{		
		this.width = gameWidth * size.getSizeFactor();
        this.paddleSize = size;
		if(this.width < MIN_WIDTH)
		{
			this.width = MIN_WIDTH;
		}
		
		this.height = initialHeight;
		this.MAX_WIDTH = MAX_WIDTH_PERCENTAGE * gameWidth;
		this.speed = SPEED_FACTOR * gameWidth;
	}

	public double getWidth()
	{
		return width * paddleSize.getSizeFactor();
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
        width = paddleSize.getSizeFactor() * width;
    }

    public double getSpeed()
	{
		return speed; 
	}

	@Deprecated
	public void changeWidth(double factor)
	{
		if(width * factor <= MAX_WIDTH && width * factor >= MIN_WIDTH)
		{
			width = width * factor;
		}
	}
}