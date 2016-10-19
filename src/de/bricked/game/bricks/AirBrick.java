package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;

public class AirBrick extends Brick
{
	public AirBrick()
	{		
		super(BrickTypes.AIR.getID(), BrickTypes.AIR.getTextureIDs(), BrickTypes.AIR.getNumberOfHitsRequired(), null);		
	}
	
	@Override
	public boolean hit(boolean instantDestroy)
	{
		//air brick is collision free
		
		return false;
	}	

	@Override
	public String getCurrentTextureID()
	{	
		return textureIDs[0];
	}
}