package de.bricked.game.bricks;

public enum BrickType
{
	NORMAL("N", new String[]{"green"}, 1, 100),
	AIR("A", new String[]{"empty"}, -1, 0),
	SOLID("S", new String[]{"blue"}, -1, 0),
	HARD("H", new String[]{"yellow", "dark_yellow"}, 2, 200),
	EXTRA_HARD("E", new String[]{"grey", "medium_grey", "dark_grey"}, 3, 300),
	INVISIBLE("I", new String[]{"empty", "green"}, 2, 200),
	TNT("T", new String[]{"red"}, 1, 100);	

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