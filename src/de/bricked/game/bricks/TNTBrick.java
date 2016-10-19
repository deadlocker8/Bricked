package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;
import de.bricked.game.powerups.PowerUp;

public class TNTBrick extends Brick
{
	public TNTBrick(PowerUp powerUp)
	{		
		super(BrickTypes.TNT.getID(), BrickTypes.TNT.getTextureIDs(), BrickTypes.TNT.getNumberOfHitsRequired(), powerUp);		
	}
	
	@Override
	public boolean hit(boolean instantDestroy)
	{
		return true;
	}	

	@Override
	public String getCurrentTextureID()
	{
		return textureIDs[0];
	}
}