package de.bricked.utils;

import java.util.Timer;
import java.util.TimerTask;

import de.bricked.ui.LevelController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CountdownTimer
{
	private int count;	
	private HBox hbox;	
	private LevelController levelController;
	private Timer timer;
	private TimerTask task;
	private CountdownTimer self;

	public CountdownTimer(int seconds, HBox hbox, LevelController levelController)
	{
		this.count = seconds;
		this.hbox = hbox;	
		this.levelController = levelController;
		self = this;
		
		start();
	}
	
	public void addSecondsToTimer(int seconds)
	{
		this.count += seconds;
	}	
	
	public HBox getHBox()
	{
		return hbox;
	}	
	
	public void start()
	{
		task = new TimerTask()
		{
			@Override
			public void run()
			{
				Platform.runLater(()->{
					try
					{
						Label labelSeconds = (Label)hbox.getChildren().get(1);
						labelSeconds.setText(String.valueOf(count));
					}
					catch(Exception e)
					{
						
					}
				});			
				if(count > 0)
				{
					count--;
				}

				if(count == 0)
				{
					Platform.runLater(()->{
						levelController.deactivatePowerUp(self, hbox);
					});
					timer.cancel();
				}				
			}
		};
		timer = new Timer();		
		timer.schedule(task, 0, 1000);
	}
	
	public void stop()
	{
		timer.cancel();		
		timer.purge();
	}
}