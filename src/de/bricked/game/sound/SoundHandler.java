package de.bricked.game.sound;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logger.LogLevel;
import logger.Logger;

public class SoundHandler
{
	public static void play(String soundID)
	{		
		try
		{
			String path = SoundHandler.class.getResource("/de/bricked/resources/sounds/" + soundID + ".mp3").toURI().toURL().toString();
			Media sound = new Media(path);
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setAutoPlay(true);				
		}
		catch(MalformedURLException | URISyntaxException e)
		{
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		} 
	}
}