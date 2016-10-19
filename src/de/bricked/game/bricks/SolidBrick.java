package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;
import de.bricked.game.powerups.PowerUp;

public class SolidBrick extends Brick
{
	public SolidBrick(PowerUp powerUp)
	{
		super(BrickTypes.SOLID.getID(), BrickTypes.SOLID.getTextureIDs(), BrickTypes.SOLID.getNumberOfHitsRequired(), powerUp);		
	}
	
	@Override
	public boolean hit(boolean instantDestroy)
	{
		if(instantDestroy)
		{
			return true;
		}
		
		return false;
	}	

	@Override
	public String getCurrentTextureID()
	{
		return textureIDs[0];
	}
}