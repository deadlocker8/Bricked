package de.bricked.ui.cells;

import de.bricked.ui.LanguageType;
import javafx.scene.control.cell.ComboBoxListCell;

public class ComboBoxLanguageCell extends ComboBoxListCell<LanguageType>
{	
	@Override
	public void updateItem(LanguageType item, boolean empty)
	{
		super.updateItem(item, empty);

		if( ! empty)
		{	
			setText(item.getName());					
		}
		else
		{
			setText(null);
		}
	}
}