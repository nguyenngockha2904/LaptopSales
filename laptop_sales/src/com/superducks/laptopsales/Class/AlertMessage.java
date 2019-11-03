package com.superducks.laptopsales.Class;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertMessage {

    public static void showAlert(String alertMessage, String icon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "" + alertMessage);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertMessage.class.getResourceAsStream("/com/superducks/laptopsales/icons/embems/" +icon+".png")));
        alert.showAndWait();
        stage.close();
    }

    public static Boolean showAlertYesNo() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(AlertMessage.class.getResourceAsStream("/com/superducks/laptopsales/icons/embems/warning.png")));
        alert.showAndWait();
        stage.close();
        return alert.getResult() == ButtonType.YES;
    }
}

