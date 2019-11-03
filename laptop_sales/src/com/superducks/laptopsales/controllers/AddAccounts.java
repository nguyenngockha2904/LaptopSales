package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class AddAccounts {
    private static Boolean changed = false;
    public TextField txtUsername;
    public TextField txtFullname;
    public TextField txtEmail;
    public DatePicker dtpBirthday;
    public ComboBox<String> cbxPosition;
    public TextField txtAddress;
    public TextField txtPhone;
    private static Stage addAccountsStage = new Stage();
    public ImageView btnNonAdd;
    public ImageView btnAdd;
    public ImageView btnClose;
    public PasswordField txtPassword;
    public PasswordField txtRetypePassword;

    public void initialize() {
        setUpAnyThings();
    }

    static void showForm() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/AddAccounts.fxml")));
            addAccountsStage.setTitle("Add Accounts");
            addAccountsStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/add-person.png");
            addAccountsStage.getIcons().add(icon);
            addAccountsStage.show();
            addAccountsStage.setResizable(false);
            addAccountsStage.setOnCloseRequest(e->rsFormMA());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setUpAnyThings() {
        if(MainForm.loggedPosition.equals("admin")) {
            cbxPosition.getItems().addAll("Admin", "Manager", "Staff");
        }
        else if(MainForm.loggedPosition.equals("manager"))
            cbxPosition.getItems().addAll( "Manager", "Staff");
        cbxPosition.setValue("Staff");
        dtpBirthday.setValue(LocalDate.now());
    }

    private static void rsFormMA() {
        if(changed.equals(true)) {
            ManageAccounts.resetForm();
            changed = false;
        }
    }
    public void btnAdd_Click(MouseEvent mouseEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String rePassword = txtRetypePassword.getText();
        String position = cbxPosition.getValue().toString().toLowerCase();
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
                String sql = "INSERT INTO `accounts` ( `username`, `password`, `email`, `fullname`, `birthday`, `position`, `address`, `phone`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
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

    public void btnClose_Click(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            addAccountsStage.close();
            ManageAccounts.resetForm();
        }
    }

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
        if(!txtUsername.getText().equals("") && !txtPassword.getText().equals("") && txtPassword.getText().equals(txtRetypePassword.getText()) && !txtUsername.getText().contains(" ") && !txtPassword.getText().contains(" ")) {
            btnAdd.setVisible(true);
            btnNonAdd.setVisible(false);
        }
        else {
            btnAdd.setVisible(false);
            btnNonAdd.setVisible(true);
        }
    }

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
}
