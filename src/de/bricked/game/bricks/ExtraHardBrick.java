package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;
import de.bricked.game.powerups.PowerUp;

public class ExtraHardBrick extends Brick
{
	public ExtraHardBrick(PowerUp powerUp)
	{		
		super(BrickTypes.EXTRA_HARD.getID(), BrickTypes.EXTRA_HARD.getTextureIDs(), BrickTypes.EXTRA_HARD.getNumberOfHitsRequired(), powerUp);		
	}
	
	@Override
	public boolean hit(boolean instantDestroy)
	{
		if(instantDestroy)
		{
			return true;
		}		
		
		numberOfHitsRequired--;
		
		if(numberOfHitsRequired == 0)
		{
			return true;
		}
		
		if(currentTexturePosition < textureIDs.length)
		{
			currentTexturePosition++;
		}
		
		return false;
	}	

	@Override
	public String getCurrentTextureID()
	{	
		return textureIDs[currentTexturePosition];
	}
}