package de.bricked.game.bricks;

public enum BrickType
{
	NORMAL("N", new String[]{"green"}, 1),
	AIR("A", new String[]{"empty"}, -1),
	SOLID("S", new String[]{"blue"}, -1),
	HARD("H", new String[]{"yellow", "dark_yellow"}, 2),
	EXTRA_HARD("E", new String[]{"grey", "medium_grey", "dark_grey"}, 3),
	INVISIBLE("I", new String[]{"empty", "green"}, 2),
	TNT("T", new String[]{"red"}, 1);	

	private String ID;
	private String[] textureIDs;	
	private int numberOfHitsRequired;
		
	private BrickType(String ID, String[] textureIDs, int numberOfHitsRequired)
	{		
		this.ID = ID;
		this.textureIDs = textureIDs;
		this.numberOfHitsRequired = numberOfHitsRequired;
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
}