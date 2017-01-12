package de.bricked.ui;

public enum LanguageType
{
	ENGLISH("English");
	
	private String name;
	
	private LanguageType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}