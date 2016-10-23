package de.bricked.commandLine.commands;

import de.bricked.game.paddle.Paddle;
import de.bricked.game.paddle.PaddleSize;

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

        Paddle paddle = bundle.getLevelController().getPaddle();
		bundle.getLevelController().getPaddle().setPaddleSize(PaddleSize.getPaddleSize(Double.parseDouble(command[1])));
        bundle.getLevelController().getLabelPaddle().setFitWidth(paddle.getWidth());
		//implement your code here
	}
}