package de.bricked.game;

import de.bricked.game.levels.Level;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackReader;

public class Game
{
	private LevelPack levelPack;
	private Level level;

	public Game()
	{
		// DEBUG
		LevelPackReader reader = new LevelPackReader("default.json");
		this.levelPack = reader.read();
		this.level = null;
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