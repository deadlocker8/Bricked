package de.bricked.ui.cells;

import de.bricked.game.settings.GameSize;
import javafx.scene.control.cell.ComboBoxListCell;

public class ComboBoxResolutionCell extends ComboBoxListCell<GameSize>
{	
	@Override
	public void updateItem(GameSize item, boolean empty)
	{
		super.updateItem(item, empty);

		if( ! empty)
		{	
			if(item == GameSize.FULL_SCREEN)
			{
				setText("Fullscreen");
			}
			else
			{
				setText(item.getWidth() + " x " + item.getHeight());
			}			
		}
		else
		{
			setText(null);
		}
	}
}