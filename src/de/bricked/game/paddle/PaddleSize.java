package de.bricked.game.paddle;

public enum PaddleSize
{
	SMALL(0.0),
	MEDIUM(0.1),
	LARGE(0.2),
	EXTRA_LARGE(0.3);
	
	
	private double sizeFactor;
		
	private PaddleSize( double sizeFactor)
	{				
		this.sizeFactor = sizeFactor;
	}
	
	public double getSizeFactor()
	{
		return sizeFactor;
	}
}