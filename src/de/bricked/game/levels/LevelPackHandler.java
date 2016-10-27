package de.bricked.game.levels;

import de.bricked.utils.FileUtils;
import tools.PathUtils;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LevelPackHandler
{
    private static final String directory = "/de/bricked/resources/levelpacks/";
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
        for(String currentFileContent : getFileContent())
        {
            LevelPackParser levelPackParser = new LevelPackParser(currentFileContent);
            levelPacks.add(levelPackParser.getLevelPack());
        }

        return sort(levelPacks);
    }

    private static ArrayList<String> getFilesFromJar()
    {
        ArrayList<String> stringArrayList = new ArrayList<>();
        try
        {
            String text = FileUtils.getFileContentFromJar(directory+"files.txt");
            String[] filenamesArray = text.split(" ");
            for(String filename : filenamesArray)
            {
                stringArrayList.add(FileUtils.getFileContentFromJar(directory + filename));
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return stringArrayList;
    }

    private static ArrayList<String> getFileContentFromUserDir()
    {
        PathUtils.checkFolder(PathUtils.getOSindependentPath().toFile());
        ArrayList<String> stringContent = new ArrayList<>();
        File folder = new File(PathUtils.getOSindependentPath().toFile(), "deadspaghetti/bricked/levelpacks/");
        File[] files = folder.listFiles();
        if(files == null)
        {
            return null;
        }
        ArrayList<File> fileArrayList = new ArrayList<>(Arrays.asList(files));

        for(File file : fileArrayList)
        {
            try
            {
                stringContent.add(new String(Files.readAllBytes(FileSystems.getDefault().getPath(file.getPath()))));

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return stringContent;
    }

    private static ArrayList<String> getFileContent()
    {
        ArrayList<String> fileArrayList = new ArrayList<>();
        ArrayList<String> userDir = getFileContentFromUserDir();
        ArrayList<String> jarDir = getFilesFromJar();
        if(userDir != null)
        {
            fileArrayList.addAll(userDir);
        }
        if(jarDir != null)
        {
            fileArrayList.addAll(getFilesFromJar());
        }
        return fileArrayList;
    }
}
