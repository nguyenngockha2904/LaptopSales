package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Main;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.sql.*;

    public class LoginForm {
        public TextField txtUsername;
        public PasswordField txtPassword;
        public ImageView btnClose;
        public ImageView btnLogIn;
        static Connection con = null;
        ResultSet rst = null;

        public void closeApp() {
        if (AlertMessage.showAlertYesNo()) {
            Platform.exit();
        }
    };

    public void logIn() {
        String sql = "select * from accounts where username=\""+txtUsername.getText()+"\"";

        try {
            con = ConnectDatabase.Connect();
            assert con != null;
            rst = con.createStatement().executeQuery(sql);
            String username = "";
            String password = "";
            String fullname = "";
            if (rst.next()) {
                username = rst.getString("username");
                password = rst.getString("password");
                fullname = rst.getString("fullname");
                if(txtUsername.getText().toLowerCase().equals(username) && txtPassword.getText().toLowerCase().equals(password)) {
                    AlertMessage.showAlert("Logged in successfully with account " + username, "tick");
                    MainForm.loggedAccount = username;
                    MainForm.loggedFullname = fullname;
                    Main.getPrimaryStage().hide();
                    MainForm.showForm();
                }
                else {
                    AlertMessage.showAlert("Incorrect password", "warning");
                }
            } else {
                AlertMessage.showAlert("Account does not exist", "warning");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void keyPressEnter(javafx.scene.input.KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            logIn();
        }
    }
}
