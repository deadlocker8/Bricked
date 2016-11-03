package de.bricked.game.bricks;

public enum BrickType
{
	NORMAL("N", new String[]{"normal-yellow", "normal-green", "normal-red", "normal-brown", "normal-pink"}, 1, 10),
	AIR("A", new String[]{"empty"}, -1, 0),
	SOLID("S", new String[]{"solid"}, -1, 0),
	HARD("H", new String[]{"hard", "hard-hitted"}, 2, 20),
	EXTRA_HARD("E", new String[]{"extra-hard", "extra-hard-hitted", "extra-hard-hitted-twice"}, 3, 30),
	INVISIBLE("I", new String[]{"empty", "normal-yellow"}, 2, 20),
	TNT("T", new String[]{"tnt"}, 1, 10);	

	private String ID;
	private String[] textureIDs;	
	private int numberOfHitsRequired;
	private int points;
		
	private BrickType(String ID, String[] textureIDs, int numberOfHitsRequired, int points)
	{		
		this.ID = ID;
		this.textureIDs = textureIDs;
		this.numberOfHitsRequired = numberOfHitsRequired;
		this.points = points;
	}		
	
	public String getID()
	{
		return ID;
	}

	public String[] getTextureIDs()
	{
		return textureIDs;
	}

	public int getNumberOfHitsRequired()
	{
		return numberOfHitsRequired;
	}
	
	public int getPoints()
	{
		return points;
	}
}