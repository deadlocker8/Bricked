package de.bricked.commandLine.commands;

import de.bricked.ui.LevelSelectController;

/**
 * Restarts current level
 * @author deadlocker8
 *
 */
public class CommandRestart extends Command
{
	public CommandRestart()
	{		
		super.keyword = "restart";		
		super.numberOfParams = 0;
		super.helptText = "help.restart";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(bundle.getLevelController() != null)
		{			
			LevelSelectController levelSelectController = bundle.getLevelController().restartLevel();
			levelSelectController.startLevel();
		
			bundle.getController().print(bundle.getLanguageBundle().getString("success.restart"));
		}
		else
		{
			bundle.getController().print(bundle.getLanguageBundle().getString("error.no.level"));
		}
	}
}