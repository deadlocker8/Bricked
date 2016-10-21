package de.bricked.game.bricks;

import de.bricked.game.powerups.PowerUp;

public class Brick
{	
	private final BrickType type;
	private int currentTexturePosition;	
	private int numberOfHitsRemaining;
	protected PowerUp powerUp;	

	public Brick(BrickType type, PowerUp powerUp)
	{		
		this.type = type;
		this.powerUp = powerUp;
		this.currentTexturePosition = 0;
		numberOfHitsRemaining = type.getNumberOfHitsRequired();
	}	
	
	public Brick(BrickType type)
	{		
		this.type = type;
		this.powerUp = null;
		this.currentTexturePosition = 0;
		numberOfHitsRemaining = type.getNumberOfHitsRequired();
	}	

	public BrickType getType()
	{
		return type;
	}
	
	public PowerUp getPowerUp()
	{
		return powerUp;
	}
	
	public String getCurrentTextureID()
	{
		return type.getTextureIDs()[currentTexturePosition];
	}

	public boolean hit(boolean instantDestroy)
	{
		if(instantDestroy)
		{
			return true;
		}	
		
		if(type.getNumberOfHitsRequired() == -1)
		{
			return false;
		}
		
		numberOfHitsRemaining--;
		
		if(numberOfHitsRemaining == 0)
		{
			return true;
		}
		
		if(currentTexturePosition < type.getTextureIDs().length)
		{
			currentTexturePosition++;
		}
		
		return false;
	}

	@Override
	public String toString()
	{
		return "Brick [type=" + type + ", currentTexturePosition=" + currentTexturePosition + ", numberOfHitsRemaining=" + numberOfHitsRemaining + ", powerUp=" + powerUp + "]";
	}
}