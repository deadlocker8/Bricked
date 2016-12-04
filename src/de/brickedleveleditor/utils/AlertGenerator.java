package de.brickedleveleditor.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertGenerator
{
    public static void showAlert(AlertType type, String contentText, Image icon)
    {
        show(type, type.name(), "", contentText, icon, true);
    }

    public static void showAlert(AlertType type, String title, String headerText, String contentText, Image icon, boolean centerOnScreen)
    {
        show(type, title, headerText, contentText, icon, centerOnScreen);
    }

    private static void show(AlertType type, String title, String headerText, String contentText, Image icon, boolean centerOnScreen)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        if(icon != null)
        {
            dialogStage.getIcons().add(icon);
        }
        if (centerOnScreen)
        {
            dialogStage.centerOnScreen();
        }
        alert.showAndWait();
    }
}