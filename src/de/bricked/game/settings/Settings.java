package de.bricked.game.settings;
import com.google.gson.Gson;
import de.bricked.game.Config;
import logger.LogLevel;
import logger.Logger;
import tools.PathUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class Settings
{
    private GameSize gameSize;
    private String language; //TODO change this to lanugage class maybe?
    private double volume;
    private boolean muted;
    private transient final String filename = "settings.json";
    private transient Gson gson;
    private transient File file;

    public Settings()
    {
        gson = new Gson();
        PathUtils.checkFolder(new File(Config.FILESYSTEM_ROOT_DIR));
        file = new File(Config.FILESYSTEM_ROOT_DIR + filename);
        initDefaultSettings();
    }

    private void initDefaultSettings()
    {
        gameSize = GameSize.NORMAL;
        language = "eng";
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
        catch (Exception e)
        {
            Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
        }
    }

    public void load()
    {
        try
        {
            String jsonContent = new String(Files.readAllBytes(FileSystems.getDefault().getPath(Config.FILESYSTEM_ROOT_DIR + filename)));
            System.out.println(jsonContent);
            Settings loadedSettings = gson.fromJson(jsonContent, Settings.class);
            this.gameSize = loadedSettings.gameSize;
            this.language = loadedSettings.language;
            this.muted = loadedSettings.muted;
            this.volume = loadedSettings.volume;
            // MORE settings go here

        }
        catch (Exception e)
        {
            Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
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

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}
}