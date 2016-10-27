package de.bricked.game.sound;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import de.bricked.game.Config;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logger.LogLevel;
import logger.Logger;

public class SoundHandler
{
    private double volume;
    private boolean muted;

    public SoundHandler()
    {
        volume = 0.0;
        muted = false;
    }

    public void play(String soundID)
	{
        if(volume > 0 && !muted)
        {
            try
            {
                String path = SoundHandler.class.getResource(Config.JAR_SOUND_SAVEDIR + soundID + ".mp3").toURI().toURL().toString();
                Media sound = new Media(path);
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setVolume(volume);
                mediaPlayer.setAutoPlay(true);
            }
            catch (MalformedURLException | URISyntaxException e)
            {
                Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
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