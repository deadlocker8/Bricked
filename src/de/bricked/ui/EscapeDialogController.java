package de.bricked.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class EscapeDialogController
{
	@FXML private ImageView imageView; 
	
	private LevelController levelController;
	private Stage stage;

	public void init(LevelController levelController, Stage stage)
	{
		this.levelController = levelController;
		this.stage = stage;
		stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent event)
			{
				if(event.getCode() == KeyCode.ESCAPE)
				{	
					event.consume();	
				}				
			}
		});
		imageView.setImage(new Image("com/sun/javafx/scene/control/skin/modena/dialog-confirm@2x.png"));		
	}
	
	public void buttonOK()
	{
		levelController.back();
		stage.close();
	}
	
	public void buttonCancel()
	{
		levelController.restart();
		stage.close();		
	}
}