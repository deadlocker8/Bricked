package de.bricked.commandLine.commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * contains all possible allowed commands
 * @author deadlocker8
 *
 */
public class PossibleCommands
{			
	public static final ArrayList<Command> possibleCommands = new ArrayList<>(Arrays.asList(
				new CommandList(),
				new CommandHelp(),
				new CommandClear(),
				new CommandShortcuts(),			
				new CommandWin(),
				new CommandRestart(),
				new CommandShowFPS(),
                new CommandPaddleSize()
			));	
}