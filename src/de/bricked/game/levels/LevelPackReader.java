package de.bricked.game.levels;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.bricked.game.paddle.PaddleSize;
import logger.LogLevel;
import logger.Logger;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;

public class LevelPackReader
{

	private String filename;
	private String directory;

	public LevelPackReader(String filename)
	{
		this.directory = "src/de/bricked/resources/levelpacks/";
		this.filename = filename;
	}

	private ArrayList<Level> parseLevels(JsonArray levelPackArray)
	{
		ArrayList<Level> levels = new ArrayList<>();
		for(JsonElement levelJson : levelPackArray)
		{
			JsonObject levelObject = levelJson.getAsJsonObject();
			String levelName = levelObject.get("name").getAsString();
			String levelAuthor = levelObject.get("author").getAsString();
			int levelPosition = levelObject.get("position").getAsInt();
			int difficulty = levelObject.get("difficulty").getAsInt();
			int startLives = levelObject.get("startLives").getAsInt();
			String initPadSize = levelObject.get("initPadSize").getAsString();
			PaddleSize padSize; 
			try
			{
				padSize = PaddleSize.valueOf(initPadSize);
			}
			catch(Exception e)
			{
				padSize = PaddleSize.MEDIUM;
			}	
			
			String boardString = levelObject.get("board").getAsString();
			Level level = new Level(levelName, levelAuthor, levelPosition, difficulty, startLives, padSize, boardString);
			levels.add(level);
		}

		levels.sort(new Comparator<Level>()
		{
			@Override
			public int compare(Level o1, Level o2)
			{
				if(o1.getPosition() == o2.getPosition())
				{
					return 0;
				}				
				
				if(o1.getPosition() > o2.getPosition())
				{
					return 1;
				}
				else
				{
					return -1;
				}				
			}
		});

		return levels;
	}

	public LevelPack read()
	{
		try
		{
			String jsonContent = new String(Files.readAllBytes(FileSystems.getDefault().getPath(directory + filename)));
			JsonObject root = new JsonParser().parse(jsonContent).getAsJsonObject();
			String packageName = root.get("name").getAsString();
			String packageAuthor = root.get("author").getAsString();
			String packageVersion = root.get("version").getAsString();
			ArrayList<Level> levels = parseLevels(root.get("levelPack").getAsJsonArray());
			return new LevelPack(packageName, packageAuthor, packageVersion, levels);
		}
		catch(Exception e)
		{
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
		return null;
	}
}
