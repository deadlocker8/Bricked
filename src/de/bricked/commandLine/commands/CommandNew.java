package de.bricked.commandLine.commands;

/**
 * Clears the history log and console
 * @author deadlocker8
 *
 */
public class CommandNew extends Command
{
	public CommandNew()
	{		
		super.keyword = "your-keyword";		
		super.numberOfParams = 0;
		super.helptText = "help.your-keyword";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(!isValid(command))
		{			
			bundle.getController().print(bundle.getLanguageBundle().getString("error.invalid.arguments"));
			return;
		}	
		
		//implement your code here
	}
}