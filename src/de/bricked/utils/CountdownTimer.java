package de.bricked.utils;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class CountdownTimer
{
	private int count;

	public CountdownTimer(int seconds, Label label)
	{
		this.count = seconds;
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				Platform.runLater(()->{
					try
					{
						label.setText(String.valueOf(count));
					}
					catch(Exception e)
					{
						
					}
				});			
				if(count > 0)
					count--;

				if(count == 0)
					timer.cancel();
			}
		};
		timer.schedule(task, 0, 1000);
	}
}