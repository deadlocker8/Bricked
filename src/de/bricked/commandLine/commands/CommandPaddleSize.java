package de.bricked.commandLine.commands;

import de.bricked.game.paddle.Paddle;
import de.bricked.game.paddle.PaddleSize;
import de.bricked.ui.LevelController;
import logger.LogLevel;
import logger.Logger;

/**
 * Changes the paddle size
 * @author deadlocker8
 *
 */
public class CommandPaddleSize extends Command
{
	public CommandPaddleSize()
	{		
		super.keyword = "paddleSize";
		super.numberOfParams = 1;
		super.helptText = "help.paddleSize";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(!isValid(command))
		{			
			bundle.getController().print(bundle.getLanguageBundle().getString("error.invalid.arguments"));
			return;
		}

        LevelController levelController = bundle.getLevelController();
        if(levelController != null)
        {
            Paddle paddle = levelController.getPaddle();
            bundle.getLevelController().getPaddle().setPaddleSize(PaddleSize.getPaddleSize(Double.parseDouble(command[1])));
            bundle.getLevelController().getLabelPaddle().setFitWidth(paddle.getWidth());
        }
        else
        {
            bundle.getController().print("Can't change paddleSize without a paddle");
        }
		//implement your code here
	}
}