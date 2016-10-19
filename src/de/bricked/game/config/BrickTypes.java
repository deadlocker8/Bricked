package de.bricked.game.config;

public enum BrickTypes
{
	NORMAL("N", new String[]{"#00FF00"}, 1),
	AIR("A", new String[]{""}, -1),
	SOLID("S", new String[]{"#0000FF"}, -1),
	HARD("H", new String[]{"#212121"}, 2),
	EXTRA_HARD("E", new String[]{""}, 3),
	INVISIBLE("I", new String[]{""}, 2),
	TNT("T", new String[]{"#FF0000"}, 1);	

	private String ID;
	private String[] textureIDs;	
	private int numberOfHitsRequired;	
		
	private BrickTypes(String ID, String[] textureIDs, int numberOfHitsRequired)
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