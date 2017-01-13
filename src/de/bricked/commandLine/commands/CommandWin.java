package de.bricked.commandLine.commands;

/**
 * Finish current level
 * @author deadlocker8
 *
 */
public class CommandWin extends Command
{
	public CommandWin()
	{		
		super.keyword = "win";		
		super.numberOfParams = 0;
		super.helptText = "help.win";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(bundle.getLevelController() != null)
		{			
			bundle.getLevelController().win();
		
			bundle.getController().print(bundle.getLanguageBundle().getString("success.win"));
		}
		else
		{
			bundle.getController().print(bundle.getLanguageBundle().getString("error.no.level"));
		}
	}
}