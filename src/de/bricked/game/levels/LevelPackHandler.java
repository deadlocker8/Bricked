package de.bricked.game.levels;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class LevelPackHandler
{
    private static String directory = "src/de/bricked/resources/levelpacks/";
    private static final String DEFAULT_PACKAGE_NAME = "default";

    private static ArrayList<LevelPack> sort(ArrayList<LevelPack> levelPackArrayList)
    {
        //sort level packs based on their name
        levelPackArrayList.sort(new Comparator<LevelPack>()
        {
            @Override
            public int compare(LevelPack o1, LevelPack o2)
            {
                return o1.getPackageName().toUpperCase().compareTo(o2.getPackageName().toUpperCase());
            }
        });

        //bring default to front
        LevelPack temp = levelPackArrayList.get(0);
        for(int i=0; i < levelPackArrayList.size(); i++)
        {
            LevelPack currentPack = levelPackArrayList.get(i);
            if(currentPack.getPackageName().equalsIgnoreCase(DEFAULT_PACKAGE_NAME))
            {
                //swap level packs
                levelPackArrayList.set(0, currentPack);
                levelPackArrayList.set(i, temp);
                break;
            }
        }
        return levelPackArrayList;
    }

	public static ArrayList<LevelPack> getAllLevelPacks()
    {
        ArrayList<LevelPack> levelPacks = new ArrayList<>();
        for(File currentFile : getListOfFiles())
        {
            LevelPackReader levelPackReader = new LevelPackReader(currentFile.getName());
            levelPacks.add(levelPackReader.read());
        }

        return sort(levelPacks);
    }

    private static File[] getListOfFiles()
    {
        File folder = new File(directory);
        return folder.listFiles();
    }
}
