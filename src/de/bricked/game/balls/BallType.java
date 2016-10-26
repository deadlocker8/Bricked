package de.bricked.game.balls;

public enum BallType
{
	NORMAL("N", "#9CD8FF", 10, 4),
	EXPLOSIVE("A","#CC2E2E", 10, 6),
	NO_COLLISION("S","#2828CC", 10, 8);	

	private String ID;
	private String color;	
	private double radius;
	private double speedFactor;
		
	private BallType(String ID, String color, double radius, double speedFactor)
	{		
		this.ID = ID;
		this.color = color;
		this.radius = radius;
		this.speedFactor = speedFactor;
	}		
	
	public String getID()
	{
		return ID;
	}

	public String getColor()
	{
		return color;
	}
	
	public double getRadius()
	{
		return radius;
	}

	public double getSpeedFactor()
	{
		return speedFactor;
	}

}