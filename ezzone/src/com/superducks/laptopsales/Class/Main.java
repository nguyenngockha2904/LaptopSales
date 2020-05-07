package com.superducks.laptopsales.Class;

import com.superducks.laptopsales.controllers.SellOrder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image ;

public class Main extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/LoginForm.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/lt-cc.png");
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e-> Platform.exit());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
