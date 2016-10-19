package de.bricked.commandLine.commands;

/**
 * Entry in the history log
 * @author deadlocker8
 *
 */
public class HistoryEntry
{
	private HistoryType type;
	private String text;
	
	public HistoryEntry(HistoryType type, String text)
	{		
		this.type = type;
		this.text = text;
	}

	public HistoryType getType()
	{
		return type;
	}

	public String getText()
	{
		return text;
	}
	
	public String toString()
	{
		return "HistoryEntry [type=" + type + ", text=" + text + "]";
	}	
}