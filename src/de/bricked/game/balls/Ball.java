package de.bricked.game.balls;

public class Ball
{	
	protected String textureID;
	protected float speedFactor;
	
	public Ball(String textureID, float speedFactor)
	{
		this.textureID = textureID;
		this.speedFactor = speedFactor;
	}

	public String getTextureID()
	{
		return textureID;
	}

	public float getSpeedFactor()
	{
		return speedFactor;
	}
	
	
	
}