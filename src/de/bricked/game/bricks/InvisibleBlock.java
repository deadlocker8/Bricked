package de.bricked.game.bricks;

import de.bricked.game.config.BrickTypes;
import de.bricked.game.powerups.PowerUp;

public class InvisibleBlock extends Brick
{
	public InvisibleBlock(PowerUp powerUp)
	{		
		super(BrickTypes.INVISIBLE.getID(), BrickTypes.INVISIBLE.getTextureIDs(), BrickTypes.INVISIBLE.getNumberOfHitsRequired(), powerUp);		
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