package de.bricked.commandLine.commands;

/**
 * enable or disable FPS counter
 * @author deadlocker8
 *
 */
public class CommandShowFPS extends Command
{
	public CommandShowFPS()
	{		
		super.keyword = "showFPS";		
		super.numberOfParams = 1;
		super.helptText = "help.showFPS";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(!isValid(command))
		{			
			bundle.getController().print(bundle.getLanguageBundle().getString("error.invalid.arguments"));
			return;
		}	
		
		switch(command[1])
		{
			case "0":	bundle.setShowFPS(false);						
						if(bundle.getLevelController() != null)
						{
							bundle.getLevelController().showLabelFPS(false);
						}
						bundle.getController().print(bundle.getLanguageBundle().getString("success.showFPS.disable"));
						break;
			case "1":	bundle.setShowFPS(true);						
						if(bundle.getLevelController() != null)
						{
							bundle.getLevelController().showLabelFPS(true);
						}
						bundle.getController().print(bundle.getLanguageBundle().getString("success.showFPS.enable"));
						break;
			default: 	bundle.getController().print(bundle.getLanguageBundle().getString("error.invalid.arguments"));
						break;			
		}		
	}
}