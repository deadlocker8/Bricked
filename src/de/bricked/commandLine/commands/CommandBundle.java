package de.bricked.commandLine.commands;

import java.util.ResourceBundle;

import de.bricked.commandLine.CommandLineController;
import de.bricked.game.Game;
import de.bricked.game.levels.Level;
import de.bricked.ui.LevelController;

/**
 * holds important objects that are needed by the commands
 * --> fill in your variables (with getters and setters) here
 * @author deadlocker8
 *
 */
public class CommandBundle
{
	private CommandLineController controller;
	private ResourceBundle languageBundle;	
	private Game game;
	private Level currentLevel;	
	private boolean showFPS = false;
	private LevelController levelController;

	public CommandBundle(Game game)
	{
		this.game = game;
	}

	public CommandLineController getController()
	{
		return controller;
	}	
	
	public ResourceBundle getLanguageBundle()
	{
		return languageBundle;
	}	

	public void setController(CommandLineController controller)
	{
		this.controller = controller;
	}

	public void setLanguageBundle(ResourceBundle languageBundle)
	{
		this.languageBundle = languageBundle;
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public Level getCurrentLevel()
	{
		return currentLevel;
	}

	public void setCurrentLevel(Level currentLevel)
	{
		this.currentLevel = currentLevel;
	}

	public boolean isShowFPS()
	{
		return showFPS;
	}

	public void setShowFPS(boolean showFPS)
	{
		this.showFPS = showFPS;
	}

	public LevelController getLevelController()
	{
		return levelController;
	}

	public void setLevelController(LevelController levelController)
	{
		this.levelController = levelController;
	}
}