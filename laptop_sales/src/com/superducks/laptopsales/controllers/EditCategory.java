package com.superducks.laptopsales.controllers;

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
    public static String categoryID;
    public static String categoryName;
    private static Stage mainStage =new Stage();
    public static int chage;
    public TextField txtcategoryID;
    public TextField txtcategoryName;
    public ImageView btnAdd;
    public ImageView btnOut;
    public ImageView btnAccept;
    public void initialize(){
        if(chage==1){
            btnAccept.setVisible(true);
            txtcategoryID.setDisable(true);
            showDataWithEdit();
        }else{
            txtcategoryID.setEditable(true);
            btnAccept.setVisible(false);
            btnAdd.setVisible(true);
        }
        chage=0;
    }

    private void showDataWithEdit() {
        txtcategoryID.setText(categoryID);
        txtcategoryName.setText(categoryName);
    }

    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditCategory.fxml")));
            mainStage.setTitle("Manager Category");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/categories.png");
            mainStage.getIcons().add(icon);
            mainStage.showAndWait();
            mainStage.setResizable(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAcceptClicked(MouseEvent mouseEvent) {
        String sql="UPDATE laptop_sales.category SET categoryName = '"+txtcategoryName.getText()+"' WHERE categoryID = '"+txtcategoryID.getText()+"'";
        try {
            ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
            mainStage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddClicked(MouseEvent mouseEvent) {
        String sql="INSERT INTO laptop_sales.category(categoryID,categoryName) VALUES ('"+txtcategoryID.getText()+"', '"+txtcategoryName.getText()+"')";
        try {
            ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
            mainStage.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnOutClicked(MouseEvent mouseEvent) {
        mainStage.close();
    }
}
