package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;
import de.bricked.game.powerups.PowerUp;

public class NormalBrick extends Brick
{
	public NormalBrick(PowerUp powerUp)
	{		
		super(BrickTypes.NORMAL.getID(), BrickTypes.NORMAL.getTextureIDs(), BrickTypes.NORMAL.getNumberOfHitsRequired(), powerUp);		
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