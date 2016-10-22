package de.bricked.game.paddle;

public class Paddle
{
	private double width;
	private double height;	
	private final double MIN_WIDTH = 50.0;
	private final double MAX_WIDTH_PERCENTAGE = 0.4;
	private double MAX_WIDTH;		
	private double SPEED_FACTOR = 0.05;
	private double speed;
	
	public Paddle(double initialWidth, double initialHeight, double gameWidth)
	{		
		this.width = initialWidth;
		this.height = initialHeight;
		this.MAX_WIDTH = MAX_WIDTH_PERCENTAGE * gameWidth;
		this.speed = SPEED_FACTOR * gameWidth;
	}

	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public double getSpeed()
	{
		return speed; 
	}
	
	public void changeWidth(double factor)
	{
		if(width * factor <= MAX_WIDTH && width * factor >= MIN_WIDTH)
		{
			width = width * factor;
		}
	}
}