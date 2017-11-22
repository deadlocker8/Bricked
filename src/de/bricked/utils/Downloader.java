package de.bricked.utils;

import java.io.FileWriter;

import de.bricked.game.Config;
import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackParser;
import logger.Logger;

public class Downloader
{
    private LevelPack levelPack;
    private LevelPackParser levelPackParser;
    private String downloadedString = "";

    public void download(String url)
    {
        downloadedString = FileUtils.getURLContent(url);
        if(downloadedString != null)
        {
            try
            {
                levelPackParser = new LevelPackParser(downloadedString);
                levelPack = levelPackParser.getLevelPack();
                save();
            }
            catch (Exception e)
            {
            	Logger.error(e);
            }
        }
    }

    private void save()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(Config.FILESYSTEM_LEVELPACK_SAVEDIR +getPackName()+".json");
            fileWriter.write(downloadedString);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e)
        {
        	Logger.error(e);
        }
    }

    private String getPackName()
    {
        return levelPack.getPackageName();
    }
}