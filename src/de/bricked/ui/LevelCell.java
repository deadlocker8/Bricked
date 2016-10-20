package de.bricked.ui;

import de.bricked.game.levels.Level;
import fontAwesome.FontIcon;
import fontAwesome.FontIconType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class LevelCell extends ListCell<Level>
{
	private double maxWidth;
	
	public LevelCell(double maxWidth)
	{
		this.maxWidth = maxWidth;
	}

	@Override
	protected void updateItem(Level item, boolean empty)
	{
		super.updateItem(item, empty);

		if( ! empty)
		{
			HBox hbox = new HBox();

			Label labelPosition = new Label(String.valueOf(item.getPosition()));
			labelPosition.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

			Label labelLevelName = new Label(item.getName());
			labelLevelName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

			Label labelAuthor = new Label("(by " + item.getAuthor() + ")");
			labelAuthor.setStyle("-fx-font-size: 12px;");
			
			hbox.getChildren().add(labelPosition);				
			hbox.getChildren().add(labelLevelName);			
			hbox.getChildren().add(labelAuthor);			
			
			Region r = new Region();			
			hbox.getChildren().add(r);
			HBox.setHgrow(r, Priority.ALWAYS);
			

			int stars = item.getDifficulty();

			FontIcon iconStarOne = new FontIcon(FontIconType.STAR);
			iconStarOne.setSize(16);
			iconStarOne.setTextFill(Color.YELLOW);
			Label labelStarOne = new Label();
			labelStarOne.setGraphic(iconStarOne);
			hbox.getChildren().add(labelStarOne);

			FontIcon iconStarTwo = new FontIcon(FontIconType.STAR);
			iconStarTwo.setSize(16);
			if(stars > 1)
			{
				iconStarTwo.setTextFill(Color.YELLOW);
			}
			else
			{
				iconStarTwo.setTextFill(Color.TRANSPARENT);
			}
			Label labelStarTwo = new Label();
			labelStarTwo.setGraphic(iconStarTwo);
			hbox.getChildren().add(labelStarTwo);

			FontIcon iconStarThree = new FontIcon(FontIconType.STAR);
			iconStarThree.setSize(16);
			if(stars > 2)
			{
				iconStarThree.setTextFill(Color.YELLOW);
			}
			else
			{
				iconStarThree.setTextFill(Color.TRANSPARENT);
			}
			Label labelStarThree = new Label();
			labelStarThree.setGraphic(iconStarThree);
			hbox.getChildren().add(labelStarThree);

			
			HBox.setMargin(labelLevelName, new Insets(0, 0, 0, 25));
			HBox.setMargin(labelAuthor, new Insets(0, 0, 0, 10));
					
			
			hbox.setAlignment(Pos.CENTER);
			hbox.setStyle("-fx-border-color: #212121; -fx-border-width: 2px; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #77C0EA;");
			hbox.setPadding(new Insets(15));

			setGraphic(hbox);
			setPadding(new Insets(10));
		}
		else
		{
			setGraphic(null);
		}

		setStyle("-fx-background-color: transparent");
	}
}
