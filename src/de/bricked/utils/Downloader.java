package de.bricked.utils;


import de.bricked.game.levels.LevelPack;
import de.bricked.game.levels.LevelPackParser;
import tools.PathUtils;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;

public class Downloader
{
    private LevelPack levelPack;
    private LevelPackParser levelPackParser;
    private String downloadedString = "";
    private String outDir;
    private String dirname = "/deadspaghetti/bricked/levelpacks/";

    public Downloader()
    {
        outDir = PathUtils.getOSindependentPath()+dirname;
        PathUtils.checkFolder(new File(outDir));
    }

    public void download(String url)
    {
        downloadedString = FileUtils.getURLContent(url);
        levelPackParser = new LevelPackParser(downloadedString);
        levelPack = levelPackParser.getLevelPack();
        save();
    }

    private void save()
    {
        try
        {
            FileWriter fileWriter = new FileWriter(outDir+getPackName()+".json");
            fileWriter.write(downloadedString);
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String getPackName()
    {
        return levelPack.getPackageName();
    }
}
