package de.brickedleveleditor.ui.controller;


import de.bricked.game.paddle.PaddleSize;
import de.brickedleveleditor.utils.AlertGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class LevelPackDialogController extends AbstractController
{
    @FXML private Spinner difficultySpinner;
    @FXML private ComboBox paddleSizeComboBox;
    @FXML private Spinner startLivesSpinner;
    @FXML private TextField authorTextField;
    @FXML private TextField levelNameTextField;

    @Override
    protected void initController()
    {
        initPaddleSizeComboBox();
    }

    private void initPaddleSizeComboBox()
    {
        paddleSizeComboBox.getItems().addAll(PaddleSize.values());
        paddleSizeComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSaveButtonClicked()
    {
        String levelName = levelNameTextField.getText();
        String author = authorTextField.getText();
        if(levelName.equals("") || author.equals(""))
        {
            AlertGenerator.showAlert(Alert.AlertType.WARNING, "Levelname or Author missing!", null);
        }
        else
        {
            int position = ((MainController) parentController).getLevelPackWriter().getLevels().size();
            int difficulty = (int) difficultySpinner.getValue();
            int startLives = (int) startLivesSpinner.getValue();
            PaddleSize initPadSize = (PaddleSize) paddleSizeComboBox.getSelectionModel().getSelectedItem();
            ((MainController) parentController).getLevelPackWriter().addLevel(levelName, author, position, difficulty, startLives, initPadSize, ((MainController) parentController).getBrickArrayList());
            stage.close();
        }
    }

    @FXML
    private void onBackButtonClicked()
    {
        stage.close();
    }

}
