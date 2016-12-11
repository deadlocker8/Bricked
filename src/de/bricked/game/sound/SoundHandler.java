package de.bricked.game.sound;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;

import de.bricked.game.Config;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import logger.LogLevel;
import logger.Logger;

public class SoundHandler
{
	private double volume;
	private boolean muted;
	private HashMap<SoundType, MediaPlayer> mediaPlayers;

	public SoundHandler(double volume, boolean muted)
	{		
		this.volume = volume;
		this.muted = muted;
		
		mediaPlayers = new HashMap<>();
		
		for(SoundType currentType : SoundType.values())
		{
			try
			{
				String path = SoundHandler.class.getResource(Config.JAR_SOUND_SAVEDIR + currentType.getFileName() + ".mp3").toURI().toURL().toString();
				Media sound = new Media(path);
				MediaPlayer mediaPlayer = new MediaPlayer(sound);
				mediaPlayer.setVolume(volume);
				mediaPlayer.setAutoPlay(false);	
				mediaPlayer.setOnEndOfMedia(()->{
					mediaPlayer.stop();
				});

				mediaPlayers.put(currentType, mediaPlayer);
			}
			catch(MalformedURLException | URISyntaxException e)
			{
				Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
			}
		}
	}

	public void play(SoundType soundType)
	{	
		if(volume > 0 && !muted)
		{		
			MediaPlayer player = mediaPlayers.get(soundType);
			
			if(player != null)
			{						
				if(!player.getStatus().equals(Status.PLAYING))
				{				
					player.play();
				}
			}
		}
	}

	public double getVolume()
	{
		return volume;
	}

	public boolean isMuted()
	{
		return muted;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
	}

	public void setMuted(boolean muted)
	{
		this.muted = muted;
	}
}