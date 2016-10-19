package de.bricked.commandLine.commands;

/**
 * Finish current level with 3 stars
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
		//TODO
//		if(bundle.getCurrentLevel() != null)
//		{			
//			Level currentLevel = bundle.getCurrentLevel();
//			LevelHandler levelHandler = bundle.getGame().getLevelHandler();
//			int moves = levelHandler.get(currentLevel.getId() - 1).getRating().getPerfectMoveCount();
//			levelHandler.finish(currentLevel.getId() - 1, moves);
//		
//			bundle.getController().print(bundle.getLanguageBundle().getString("success.win"));
//		}
//		else
//		{
//			bundle.getController().print(bundle.getLanguageBundle().getString("error.win"));
//		}
	}
}