package de.bricked.commandLine.commands;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Lists all available commands
 * @author deadlocker8
 *
 */
public class CommandList extends Command
{
	public CommandList()
	{
		super();
		super.keyword = "list";
		super.numberOfParams = 0;
		super.helptText = "help.list";
	}

	@Override
	public void execute(String[] command, CommandBundle bundle)
	{		
		if(!isValid(command))
		{			
			bundle.getController().print(bundle.getLanguageBundle().getString("error.invalid.arguments"));
			return;
		}
		
		//sort possible commands alphabetically
		ArrayList<Command> commands = PossibleCommands.possibleCommands;
		commands.sort(new Comparator<Command>()
		{
			@Override
			public int compare(Command o1, Command o2)
			{
				return o1.keyword.compareTo(o2.keyword);				
			}
		});
		
		//loop through all possible commands and print keyword
		StringBuilder sb = new StringBuilder();	
		sb.append("All possible commands:\n");
		for(int i = 0; i < commands.size(); i++)
		{
			sb.append(commands.get(i).keyword);
			if(i != (commands.size()-1))
			{
				sb.append("\n");
			}
		}
		
		bundle.getController().print(sb.toString());			
	}
}