package de.bricked.game.balls;

public enum BallType
{
	NORMAL("#9CD8FF", 0.03, 0.005, 0.015),
	EXPLOSIVE("#CC2E2E", 0.03, 0.01, 0.02),
	NO_COLLISION("#2828CC", 0.03, 0.015, 0.023);	

	private String color;	
	private double sizeFactor;
	private double speedFactor;
	private double maxTotalSpeed;
		
	private BallType(String color, double sizeFactor, double speedFactor, double maxTotalSpeed)
	{	
		this.color = color;
		this.sizeFactor = sizeFactor;
		this.speedFactor = speedFactor;
		this.maxTotalSpeed = maxTotalSpeed;
	}	
	
	public String getColor()
	{
		return color;
	}
	
	public double getSizeFactor()
	{
		return sizeFactor;
	}

	public double getSpeedFactor()
	{
		return speedFactor;
	}

	public double getMaxTotalSpeed()
	{
		return maxTotalSpeed;
	}
}