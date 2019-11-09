package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class EditCategory {
    static boolean changed = false;
    public static String categoryID;
    public static String categoryName;
    static Stage mainStage = new Stage();
    public static int chage;
    public TextField txtcategoryID;
    public TextField txtcategoryName;
    public ImageView btnAdd;
    public ImageView btnOut;
    public ImageView btnAccept;
    public ImageView btnNonAdd;
    public ImageView btnNonAccept;

    public void initialize(){
        if(chage==1){
            btnAccept.setVisible(true);
            showDataWithEdit();
        }else{
            txtcategoryID.setEditable(true);
            btnAdd.setVisible(true);
        }
    }

    private void showDataWithEdit() {
        txtcategoryID.setText(categoryID);
        txtcategoryName.setText(categoryName);
    }


    public void btnAcceptClicked(MouseEvent mouseEvent) {
        String sql="UPDATE category SET categoryName = '"+txtcategoryName.getText()+"' WHERE categoryID = '"+txtcategoryID.getText()+"'";
        try {
            ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
            AlertMessage.showAlert("Updated all information", "tick");
            changed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddClicked(MouseEvent mouseEvent) {
        String sql="INSERT INTO category(categoryID,categoryName) VALUES ('"+txtcategoryID.getText()+"', '"+txtcategoryName.getText()+"')";
        try {
            ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
            AlertMessage.showAlert("Added new category", "tick");
            txtcategoryID.setText("");
            txtcategoryName.setText("");
            changed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnOutClicked(MouseEvent mouseEvent) {
        mainStage.close();
    }
}
