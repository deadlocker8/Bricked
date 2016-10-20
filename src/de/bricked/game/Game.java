package de.bricked.game;

import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackReader;
import de.bricked.game.settings.Settings;

public class Game
{
	private Settings settings;
	private LevelPack levelPack;
	private Level level;

	public Game()
	{		
		this.settings = new Settings();		
		// DEBUG
		LevelPackReader reader = new LevelPackReader("default.json");
		this.levelPack = reader.read();
		this.level = null;
	}
	
	public Settings getSettings()
	{
		return settings;
	}

	public void setSettings(Settings settings)
	{
		this.settings = settings;
	}

	public void setLevelPack(LevelPack levelPack)
	{
		this.levelPack = levelPack;
	}

	public LevelPack getLevelPack()
	{
		return levelPack;
	}
	
	public void setLevel(Level level)
	{
		this.level = level;
	}
	
	public Level getLevel()
	{
		return level;
	}
}