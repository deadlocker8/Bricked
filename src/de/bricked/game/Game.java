package de.bricked.game;

import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.settings.Settings;

public class Game
{
	private Settings settings;
	private LevelPack levelPack;
	private Level level;
	private int livesRemaining;

	public Game()
	{		
		this.settings = new Settings();				
		this.levelPack = null;
		this.level = null;	
		this.livesRemaining = 0;
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

	public int getLivesRemaining()
	{
		return livesRemaining;
	}

	public void setLivesRemaining(int livesRemaining)
	{
		this.livesRemaining = livesRemaining;
	}
}