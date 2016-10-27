package de.bricked.utils;


import de.bricked.game.levels.LevelPackHandler;
import logger.LogLevel;
import logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils
{
    public static String getFileContentFromJar(String path)
    {
        try
        {
            InputStream inputStream = FileUtils.class.getResourceAsStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            String line = "";
            StringBuilder text = new StringBuilder();

            while (line != null)
            {
                line = bufferedReader.readLine();
                if (line != null)
                    text.append(line);
            }
            return text.toString();
        }
        catch (Exception e)
        {
            Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
        }
        return null;
    }
}
