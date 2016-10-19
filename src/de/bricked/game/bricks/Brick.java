package de.bricked.game.bricks;

import java.util.Arrays;

import de.bricked.game.powerups.PowerUp;

public abstract class Brick
{	
	protected String ID;
	protected String[] textureIDs;
	protected int currentTexturePosition;
	protected int numberOfHitsRequired;
	protected PowerUp powerUp;
	

	public Brick(String ID, String[] textureIDs, int numberOfHitsRequired, PowerUp powerUp)
	{		
		this.ID = ID;
		this.textureIDs =  textureIDs;
		this.numberOfHitsRequired = numberOfHitsRequired;
		this.powerUp = powerUp;
		this.currentTexturePosition = 0;
	}	

	public String getID()
	{
		return ID;
	}
	
	public int getNumberOfHitsRequired()
	{
		return numberOfHitsRequired;
	}

	public void setNumberOfHitsRequired(int numberOfHitsRequired)
	{
		this.numberOfHitsRequired = numberOfHitsRequired;
	}

	public PowerUp getPowerUp()
	{
		return powerUp;
	}
	
	public abstract String getCurrentTextureID();
	
	public abstract boolean hit(boolean instantDestroy);	

	@Override
	public String toString()
	{
		return "Brick [textureIDs=" + Arrays.toString(textureIDs) + ", numberOfHitsRequired=" + numberOfHitsRequired + ", powerUp=" + powerUp + "]";
	}	
}