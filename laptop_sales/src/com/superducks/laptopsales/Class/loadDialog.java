package com.superducks.laptopsales.Class;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class loadDialog {
    private loadDialog() {
    }

    public static Parent loadDialogFXML(String resource){
        Parent  root=null;
        try {
            root=new FXMLLoader().load(new loadDialog().getClass().getResource(resource));
        } catch (IOException e) {

        }
        return root;
    }
}
