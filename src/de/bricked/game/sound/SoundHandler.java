package de.bricked.game.sound;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import de.bricked.game.Config;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import logger.Logger;

public class SoundHandler
{
	private double volume;
	private boolean muted;
	private HashMap<SoundType, Sound> mediaPlayers;	
	private int tntCounter;
	
	static
	{
		TinySound.init();
	}

	public SoundHandler(double volume, boolean muted)
	{		
		this.volume = volume;
		this.muted = muted;
		this.tntCounter = 0;
		
		mediaPlayers = new HashMap<>();			
		
		for(SoundType currentType : SoundType.values())
		{
			try
			{
				URL path = SoundHandler.class.getResource(Config.JAR_SOUND_SAVEDIR + currentType.getFileName() + ".wav").toURI().toURL();
			
				Sound sound = TinySound.loadSound(path);
				mediaPlayers.put(currentType, sound);
			}
			catch(MalformedURLException | URISyntaxException e)
			{
				Logger.error(e);
			}
		}
	}

	public void play(SoundType soundType)
	{			
		if(volume > 0 && !muted)
		{	
			Sound player = mediaPlayers.get(soundType);		
			if(soundType.equals(SoundType.TNT))
			{
				tntCounter++;			
			}
			else
			{
				tntCounter = 0;
			}
			
			if(player != null && tntCounter < 2)
			{		
				player.play(volume);
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
	
	public void stopAll()
	{
		for(Sound currentSound : mediaPlayers.values())
		{
			currentSound.stop();
		}
	}
}