package de.bricked.ui;

public class Control
{
	private String action;
	private String key;
	
	public Control(String action, String key)
	{		
		this.action = action;
		this.key = key;
	}

	public String getAction()
	{
		return action;
	}

	public String getKey()
	{
		return key;
	}
}