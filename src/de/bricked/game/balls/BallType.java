package de.bricked.game.balls;

public enum BallType
{
	NORMAL("N", "normal", 10, 2),
	EXPLOSIVE("A","explosive", 10, 4),
	NO_COLLISION("S","no_collision", 10, 6);	

	private String ID;
	private String textureID;	
	private double radius;
	private double speedFactor;
		
	private BallType(String ID, String textureID, double radius, double speedFactor)
	{		
		this.ID = ID;
		this.textureID = textureID;
		this.radius = radius;
		this.speedFactor = speedFactor;
	}		
	
	public String getID()
	{
		return ID;
	}

	public String getTextureID()
	{
		return textureID;
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