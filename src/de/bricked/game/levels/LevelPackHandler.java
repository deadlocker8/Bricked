package de.bricked.game.levels;

import java.io.File;
import java.util.ArrayList;

public class LevelPackHandler
{
    private static String directory = "src/de/bricked/resources/levelpacks/";
	public static ArrayList<LevelPack> getAllLevelPacks()
    {
        ArrayList<LevelPack> levelPacks = new ArrayList<>();
        for(File currentFile : getListOfFiles())
        {
            LevelPackReader levelPackReader = new LevelPackReader(currentFile.getName());
            levelPacks.add(levelPackReader.read());
        }
        return levelPacks;
    }

    private static File[] getListOfFiles()
    {
        File folder = new File(directory);
        return folder.listFiles();
    }

    public static void main(String[] args)
    {
        LevelPackHandler.getAllLevelPacks();
    }
}
