package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddAccounts {
    static Boolean changed = false;
    public TextField txtUsername;
    public TextField txtFullname;
    public TextField txtEmail;
    public DatePicker dtpBirthday;
    public ComboBox<String> cbxPosition;
    public TextField txtAddress;
    public TextField txtPhone;
    static Stage addAccountsStage = new Stage();
    public ImageView btnNonAdd;
    public ImageView btnAdd;
    public ImageView btnClose;
    public PasswordField txtPassword;
    public PasswordField txtRetypePassword;
    public Button btnImage;
    public ImageView imgAvatar;
    private String urlImage = "";
    public AnchorPane addAccountsPane;

    public void initialize() {
        setUpAnyThings();
    }

    // First Set Up
    private void setUpAnyThings() {
        if(MainForm.loggedPosition.equals("admin")) {
            cbxPosition.getItems().addAll("Admin", "Manager", "Staff");
        }
        else if(MainForm.loggedPosition.equals("manager"))
            cbxPosition.getItems().addAll( "Manager", "Staff");
        cbxPosition.setValue("Staff");
        dtpBirthday.setValue(LocalDate.now());
    }

    //Add
    public void btnAdd_Click(MouseEvent mouseEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String rePassword = txtRetypePassword.getText();
        String position = cbxPosition.getValue().toLowerCase();
        String email = "", fullname = "", birthday = "", address = "", phone = "";
        birthday = dtpBirthday.getValue().toString();
        if (!txtEmail.getText().equals(""))
            email = txtEmail.getText().toLowerCase();
        if (!txtFullname.getText().equals(""))
            fullname = txtFullname.getText();
        if (!txtAddress.getText().equals(""))
            address = txtAddress.getText();
        if (!txtPhone.getText().equals(""))
            phone = txtPhone.getText();
        if(checkUsername(username)) {
            if(AlertMessage.showAlertYesNo()) {
                String sql = "INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`, `avatar`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                Connection con = ConnectDatabase.Connect();
                try {
                    assert con != null;
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, email);
                    pst.setString(4, fullname);
                    pst.setString(5, birthday);
                    pst.setString(6, position);
                    pst.setString(7, address);
                    pst.setString(8, phone);
                    pst.setString(9, urlImage);
                    pst.executeUpdate();
                    changed = true;
                    AlertMessage.showAlert("Added new account " + username, "tick");
                    clearAllField();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            AlertMessage.showAlert("Account " + username + " already exited, please choose other username", "error");
        }
    }

    //Close
    public void btnClose_Click(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            addAccountsStage.close();
        }
    }

    //Check Username
    private Boolean checkUsername(String username) {
        Connection con = ConnectDatabase.Connect();
        String sql = "select * from accounts where username=\"" + username + "\"";
        try {
            assert con != null;
            ResultSet rst = con.createStatement().executeQuery(sql);
            return !rst.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void textChanged(KeyEvent inputMethodEvent) {
        String reUsername = "^[a-z0-9_-]{3,15}$";
        String rePassword = "^.*(?=.{4,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{4,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{4,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$";
        if (txtUsername.getText().matches(reUsername) && txtPassword.getText().matches(rePassword) && txtPassword.getText().equals(txtRetypePassword.getText())) {
            btnAdd.setVisible(true);
            btnNonAdd.setVisible(false);
        } else {
            btnAdd.setVisible(false);
            btnNonAdd.setVisible(true);
        }
    }

    //Clear All Field
    private void clearAllField() {
        cbxPosition.setValue("Staff");
        dtpBirthday.setValue(LocalDate.now());
        txtUsername.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtFullname.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtRetypePassword.setText("");
        btnAdd.setVisible(false);
        btnNonAdd.setVisible(true);
    }

    //Choose Image
    public void btnImage_Click(MouseEvent mouseEvent) {
        Stage stage = (Stage) addAccountsPane.getScene().getWindow();
        FileChooser fc= new FileChooser();
        fc.setTitle("Choose a image ");
        FileChooser.ExtensionFilter imageFilter=new FileChooser.ExtensionFilter("image Files","*.jpg","*.png");
        fc.getExtensionFilters().add(imageFilter);
        File file=fc.showOpenDialog(stage);
        if(file !=null){
            urlImage = file.toURI().toString();
            Image image=new Image(urlImage);
            imgAvatar.setImage(image);
        }
    }
}
