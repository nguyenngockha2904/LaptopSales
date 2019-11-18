package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditCategories {
    static boolean changed = false;
    static String categoryID;
    static String categoryName;
    static Stage mainStage = new Stage();
    static int chage;
    public TextField txtcategoryID;
    public TextField txtcategoryName;
    public ImageView btnAdd;
    public ImageView btnOut;
    public ImageView btnAccept;
    public ImageView btnNonAdd;
    public ImageView btnNonAccept;

    public void initialize() {
        if (chage == 1) {
            btnNonAccept.setVisible(true);
            showDataWithEdit();
        } else {
            txtcategoryID.setEditable(true);
            btnNonAdd.setVisible(true);
        }
    }

    private void showDataWithEdit() {
        txtcategoryID.setText(categoryID);
        txtcategoryName.setText(categoryName);
    }


    public void btnAcceptClicked(MouseEvent mouseEvent) {
        String sqlCheckName = "select * from categories where name ='" + txtcategoryName.getText() + "';";
        try {
            ResultSet rsName = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlCheckName);
            if (!rsName.next()) {
                if (AlertMessage.showAlertYesNo()) {
                    String sql = "UPDATE categories SET name = '" + txtcategoryName.getText() + "' WHERE id = '" + txtcategoryID.getText() + "'";
                    try {
                        Objects.requireNonNull(ConnectDatabase.Connect()).prepareStatement(sql).executeUpdate();
                        AlertMessage.showAlert("Updated all information", "tick");
                        categoryName = txtcategoryName.getText();
                        check();
                        changed = true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                AlertMessage.showAlert("This Category Name already existed, please choose another", "error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnAddClicked(MouseEvent mouseEvent) {
        String sqlCheck = "select * from categories where id ='" + txtcategoryID.getText() + "';";
        String sqlCheckName = "select * from categories where name ='" + txtcategoryName.getText() + "';";
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sqlCheck);
            ResultSet rsName = ConnectDatabase.Connect().createStatement().executeQuery(sqlCheckName);
            if (!rs.next() && !rsName.next()) {
                if (AlertMessage.showAlertYesNo()) {
                    String sql = "INSERT INTO categories(id,name) VALUES ('" + txtcategoryID.getText() + "', '" + txtcategoryName.getText() + "')";
                    try {
                        Objects.requireNonNull(ConnectDatabase.Connect()).prepareStatement(sql).executeUpdate();
                        AlertMessage.showAlert("Added new category", "tick");
                        txtcategoryID.setText("");
                        txtcategoryName.setText("");
                        check();
                        changed = true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else
                AlertMessage.showAlert("This Category ID or Name already existed, please choose another", "error");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnOutClicked(MouseEvent mouseEvent) {
        mainStage.close();
    }

    public void text_Changed(KeyEvent keyEvent) {
        check();
    }

    private void check() {
        String reCategoryID = "^[a-z0-9_-]{2,10}$";
        String reCategoryName = "^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ-]{2,50}$";
        if(chage == 1) {
            if(!categoryName.equals(txtcategoryName.getText()) && txtcategoryName.getText().matches(reCategoryName)) {
                btnAccept.setVisible(true);
                btnNonAccept.setVisible(false);
            }
            else {
                btnAccept.setVisible(false);
                btnNonAccept.setVisible(true);
            }
        }
        else {
            if(txtcategoryID.getText().matches(reCategoryID) && txtcategoryName.getText().matches(reCategoryName)) {
                btnAdd.setVisible(true);
                btnNonAdd.setVisible(false);
            } else {
                btnAdd.setVisible(false);
                btnNonAdd.setVisible(true);
            }
        }
    }
}

