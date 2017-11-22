package de.bricked.game.settings;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import com.google.gson.Gson;

import de.bricked.game.Config;
import de.bricked.ui.LanguageType;
import logger.Logger;
import tools.PathUtils;

public class Settings
{
	private GameSize gameSize;
	private LanguageType language;
	private double volume;
	private boolean muted;
	private transient final String filename = "settings.json";
	private transient Gson gson;
	private transient File file;

	public Settings(boolean init)
	{
		gson = new Gson();
		PathUtils.checkFolder(new File(Config.FILESYSTEM_ROOT_DIR));
		file = new File(Config.FILESYSTEM_ROOT_DIR + filename);
		if(file.exists())
		{
			load();
		}
		else
		{
			initDefaultSettings();
			save();
		}
	}
	
	//needed for GSON de-serialization
	public Settings()
	{
		
	}

	private void initDefaultSettings()
	{
		gameSize = GameSize.NORMAL;
		language = LanguageType.ENGLISH;
		muted = false;
		volume = 0.5;
	}

	public void save()
	{
		try
		{
			FileWriter fileWriter = new FileWriter(file);
			String json = gson.toJson(this);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
		}
		catch(Exception e)
		{
			Logger.error(e);
		}
	}

	public void load()
	{
		try
		{
			String jsonContent = new String(Files.readAllBytes(FileSystems.getDefault().getPath(Config.FILESYSTEM_ROOT_DIR + filename)));			
			Settings loadedSettings = gson.fromJson(jsonContent, Settings.class);
			this.gameSize = loadedSettings.gameSize;
			this.language = loadedSettings.language;
			this.muted = loadedSettings.muted;
			this.volume = loadedSettings.volume;
			// MORE settings go here
		}
		catch(Exception e)
		{
			Logger.error(e);
		}
	}

	public GameSize getGameSize()
	{
		return gameSize;
	}

	public void setGameSize(GameSize gameSize)
	{
		this.gameSize = gameSize;
	}

	public LanguageType getLanguage()
	{
		return language;
	}

	public void setLanguage(LanguageType language)
	{
		this.language = language;
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
	}

	public boolean isMuted()
	{
		return muted;
	}

	public void setMuted(boolean muted)
	{
		this.muted = muted;
	}

	@Override
	public String toString()
	{
		return "Settings [gameSize=" + gameSize + ", language=" + language + ", volume=" + volume + ", muted=" + muted + "]";
	}
}