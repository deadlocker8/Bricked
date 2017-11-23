package de.bricked.ui.cells;

import de.bricked.game.levels.Level;
import de.bricked.utils.Colors;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

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

		if(!empty)
		{
			HBox hbox = new HBox();

			Label labelPosition = new Label(String.valueOf(item.getPosition()));
			labelPosition.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
			labelPosition.getStyleClass().add("greylabel");

			Label labelLevelName = new Label(item.getName());
			labelLevelName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
			labelLevelName.setMaxWidth(maxWidth * 0.4);
			labelLevelName.getStyleClass().add("greylabel");

			Label labelAuthor = new Label("(by " + item.getAuthor() + ")");
			labelAuthor.setStyle("-fx-font-size: 12px;");
			labelAuthor.setMaxWidth(maxWidth * 0.3);
			labelAuthor.getStyleClass().add("greylabel");

			hbox.getChildren().add(labelPosition);
			hbox.getChildren().add(labelLevelName);
			hbox.getChildren().add(labelAuthor);

			Region r = new Region();
			hbox.getChildren().add(r);
			HBox.setHgrow(r, Priority.ALWAYS);

			int difficulty = item.getDifficulty();

			Image shieldIcon;
			switch(difficulty)
			{
				case 1:
					shieldIcon = new Image("/de/bricked/resources/textures/misc/shield-easy.png");
					break;
				case 2:
					shieldIcon = new Image("/de/bricked/resources/textures/misc/shield-medium.png");
					break;
				case 3:
					shieldIcon = new Image("/de/bricked/resources/textures/misc/shield-hard.png");
					break;
				default:
					shieldIcon = new Image("/de/bricked/resources/textures/misc/shield-hard.png");
					break;
			}

			ImageView imageViewShieldOne = new ImageView(shieldIcon);
			imageViewShieldOne.setFitWidth(12);
			imageViewShieldOne.setFitHeight(12);
			hbox.getChildren().add(imageViewShieldOne);

			ImageView imageViewShieldTwo;
			if(difficulty >= 2)
			{
				imageViewShieldTwo = new ImageView(shieldIcon);
			}
			else
			{
				imageViewShieldTwo = new ImageView();
			}

			imageViewShieldTwo.setFitWidth(12);
			imageViewShieldTwo.setFitHeight(12);
			hbox.getChildren().add(imageViewShieldTwo);
			HBox.setMargin(imageViewShieldTwo, new Insets(0, 0, 0, 3));

			ImageView imageViewShieldThree;
			if(difficulty >= 3)
			{
				imageViewShieldThree = new ImageView(shieldIcon);
			}
			else
			{
				imageViewShieldThree = new ImageView();
			}

			imageViewShieldThree.setFitWidth(12);
			imageViewShieldThree.setFitHeight(12);
			hbox.getChildren().add(imageViewShieldThree);
			HBox.setMargin(imageViewShieldThree, new Insets(0, 0, 0, 3));

			HBox.setMargin(labelLevelName, new Insets(0, 0, 0, 25));
			HBox.setMargin(labelAuthor, new Insets(0, 0, 0, 10));

			hbox.setAlignment(Pos.CENTER);
			hbox.setStyle("-fx-background-color: " + Colors.SELECT_LEVEL);
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