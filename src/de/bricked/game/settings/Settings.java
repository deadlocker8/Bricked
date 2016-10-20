package de.bricked.game.settings;
import com.google.gson.Gson;
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
    private transient final String directory = "/deadspaghetti/bricked/";
    private transient final String filename = "settings.json";
    private transient Gson gson;
    private transient File file;

    public Settings()
    {
        gson = new Gson();
        PathUtils.checkFolder(new File(PathUtils.getOSindependentPath() + directory));
        file = new File(PathUtils.getOSindependentPath() + directory + filename);

        if(!file.exists())
        {
            try
            {
                initDefaultSettings();
                save();
            }
            catch(Exception e)
            {
                Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
            }
        }
        else
        {
            try
            {
                load();
            }
            catch(Exception e)
            {
            	 Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
            }
        }
    }

    private void initDefaultSettings()
    {
        gameSize = GameSize.NORMAL;
        language = "eng";
    }

    public void save()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(file);
            System.out.println(gson.toJson(this));
            fileWriter.write(gson.toJson(this));
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e)
        {
            Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
        }
    }

    public void load() throws Exception
    {
        String jsonContent = new String(Files.readAllBytes(FileSystems.getDefault().getPath(PathUtils.getOSindependentPath().toAbsolutePath() + directory + filename)));       
        Settings loadedSettings = gson.fromJson(jsonContent, Settings.class);
        this.gameSize = loadedSettings.gameSize;
        this.language = loadedSettings.language;
       // MORE settings go here
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