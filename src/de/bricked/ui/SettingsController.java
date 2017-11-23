package de.bricked.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import de.bricked.game.Game;
import de.bricked.game.settings.GameSize;
import de.bricked.ui.cells.ComboBoxLanguageCell;
import de.bricked.ui.cells.ComboBoxResolutionCell;
import de.bricked.utils.Colors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import logger.Logger;
import tools.AlertGenerator;

public class SettingsController implements CommandLineAvailabale
{
	@FXML private AnchorPane mainPane;
	@FXML private Label labelLevelPack;
	@FXML private Button buttonBack;
	@FXML private Button buttonSave;
	@FXML private ComboBox<GameSize> comboBoxResolution;
	@FXML private ComboBox<LanguageType> comboBoxLanguage;
	@FXML private CheckBox checkBoxSound;
	@FXML private Slider sliderVolume;
	@FXML private TableView<Control> tableViewControls;

	public Stage stage;
	public Image icon = new Image("de/bricked/resources/icon.png");
	public final ResourceBundle bundle = ResourceBundle.getBundle("de/bricked/main/", Locale.GERMANY);
	public Controller controller;
	public Game game;

	public void init(Stage stage, Controller controller, Game game)
	{
		this.stage = stage;
		this.controller = controller;
		this.game = game;

		comboBoxResolution.setCellFactory(new Callback<ListView<GameSize>, ListCell<GameSize>>()
		{
			@Override
			public ListCell<GameSize> call(ListView<GameSize> param)
			{
				return new ComboBoxResolutionCell();
			}
		});
		comboBoxResolution.setButtonCell(new ComboBoxResolutionCell());
		comboBoxResolution.getItems().addAll(GameSize.values());

		comboBoxLanguage.setCellFactory(new Callback<ListView<LanguageType>, ListCell<LanguageType>>()
		{
			@Override
			public ListCell<LanguageType> call(ListView<LanguageType> param)
			{
				return new ComboBoxLanguageCell();
			}
		});
		comboBoxLanguage.setButtonCell(new ComboBoxLanguageCell());
		comboBoxLanguage.getItems().addAll(LanguageType.values());

		checkBoxSound.selectedProperty().addListener(new ChangeListener<Boolean>()
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if(newValue == true)
				{
					sliderVolume.setDisable(false);
				}
				else
				{
					sliderVolume.setDisable(true);
				}
			}
		});

		// preselect
		comboBoxResolution.setValue(game.getSettings().getGameSize());
		comboBoxLanguage.setValue(game.getSettings().getLanguage());
		if(game.getSettings().isMuted())
		{
			checkBoxSound.setSelected(false);
			sliderVolume.setDisable(true);
		}
		else
		{
			checkBoxSound.setSelected(true);
			sliderVolume.setDisable(false);
		}
		sliderVolume.setValue(game.getSettings().getVolume() * 100);

		initTableViewControls();

		mainPane.setStyle("-fx-base: " + Colors.BACKGROUND);
	}

	public void save()
	{
		game.getSettings().setGameSize(comboBoxResolution.getValue());
		game.getSettings().setLanguage(comboBoxLanguage.getValue());
		game.getSettings().setMuted(!checkBoxSound.isSelected());
		game.getSettings().setVolume(sliderVolume.getValue() / 100.0);
		game.getSettings().save();

		game.refreshSoundHandler();
		stage.close();
		controller.stage.show();

		// TODO load correct language file
	}

	public void back()
	{
		stage.close();
		controller.stage.show();
	}

	private void initTableViewControls()
	{
		ArrayList<Control> controls = new ArrayList<>();
		controls.add(new Control("Start Ball", "Space / Left Mouse Button"));
		controls.add(new Control("Move Paddle", "Arrow Keys / Mouse"));
		controls.add(new Control("Pause", "P"));
		controls.add(new Control("Exit Level / Back", "Esc"));

		TableColumn<Control, String> columnAction = new TableColumn<>("Action");
		columnAction.setCellValueFactory(new Callback<CellDataFeatures<Control, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call(CellDataFeatures<Control, String> param)
			{
				Control control = (Control)param.getValue();
				return new SimpleStringProperty(control.getAction());
			}
		});
		tableViewControls.getColumns().add(columnAction);
		columnAction.setStyle( "-fx-alignment: CENTER; -fx-font-weight: bold;");
		columnAction.prefWidthProperty().bind(tableViewControls.widthProperty().divide(2).subtract(2));
		columnAction.setResizable(false);		
		columnAction.setSortable(false);
		
		TableColumn<Control, String> columnKey = new TableColumn<>("Key");
		columnKey.setCellValueFactory(new Callback<CellDataFeatures<Control, String>, ObservableValue<String>>()
		{
			public ObservableValue<String> call(CellDataFeatures<Control, String> param)
			{
				Control control = (Control)param.getValue();
				return new SimpleStringProperty(control.getKey());
			}
		});
		tableViewControls.getColumns().add(columnKey);
		columnKey.setStyle( "-fx-alignment: CENTER; -fx-font-weight: bold;");
		columnKey.prefWidthProperty().bind(tableViewControls.widthProperty().divide(2).subtract(2));
		columnKey.setResizable(false);	
		columnKey.setSortable(false);
		
		tableViewControls.getItems().addAll(controls);
	}

	@Override
	public void showCommandLine()
	{
		try
		{
			controller.getCommandLine().showCommandLine("Debug Console", 400, 250, 400, 200, -1, -1, true);
		}
		catch(IOException e)
		{
			AlertGenerator.showAlert(AlertType.ERROR, "Error", "", "An error occurred while opening the debug console.\n\nDetails:\n" + e.getMessage(), icon, stage, null, false);
			Logger.error(e);
		}
	}
}