package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MainForm {
    static String loggedAccount = "";
    static String loggedFullname = "";
    static String loggedPosition = "";
    public MenuItem itmAccount;
    public MenuItem itmLogOut;
    private static Stage mainStage = new Stage();
    public MenuItem btnClose;
    public ImageView btnSales;
    public Label lblSales;
    public Label lblInfo;
    public ImageView btnInfo;
    public Label lblLog;
    public ImageView btnAccounts;
    public Label lblAccounts;
    public ImageView btnStatistics;
    public ImageView btnCategories;
    public Label lblStatistics;
    public Label lblCategories;
    public Tab tabAdmin;
    public Tab tabHome;

    public void initialize() {
        setTab();
    }

    private void setTab() {
        Connection con = null;
        ResultSet rst = null;
        String sql = "select * from accounts where username = " + "\"" + loggedAccount + "\"";
        if(loggedFullname.equals(""))
            itmAccount.setText(loggedAccount);
        else
            itmAccount.setText(loggedFullname);
        con = ConnectDatabase.Connect();
        try {
            assert con != null;
            rst = con.createStatement().executeQuery(sql);
            while (rst.next()) {
                loggedPosition = rst.getString("position");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(loggedPosition.equals("staff")) {
            tabAdmin.setDisable(true);
        }
    }
    static void showForm() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/MainForm.fxml")));
            mainStage.setTitle("Main Form");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/laptop-icon.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);
            mainStage.setOnCloseRequest(e->Platform.exit());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void itmLogOut_Click(ActionEvent actionEvent) throws IOException {
        if (AlertMessage.showAlertYesNo()) {
            itmAccount.setText("");
            itmAccount.setVisible(false);
            itmLogOut.setVisible(false);
            mainStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/LoginForm.fxml"));
            Main.getPrimaryStage().setScene(new Scene(root));
            Main.getPrimaryStage().setTitle("Log In");
            Image icon = new Image("/com/superducks/laptopsales/icons/laptop-icon.png");
            Main.getPrimaryStage().getIcons().add(icon);
            Main.getPrimaryStage().show();
            Main.getPrimaryStage().setResizable(false);
        }
    }

    public void btnSales_MouseExited(MouseEvent mouseDragEvent) {
        lblSales.setVisible(false);
    }

    public void btnSales_MouseMoved(MouseEvent mouseDragEvent) {
        lblSales.setVisible(true);
    }

    public void btnInfo_MouseExited(MouseEvent mouseEvent) {
        lblInfo.setVisible(false);
    }

    public void btnInfo_MouseMoved(MouseEvent mouseEvent) {
        lblInfo.setVisible(true);
    }

    public void btnLog_MouseExited(MouseEvent mouseEvent) {
        lblLog.setVisible(false);
    }

    public void btnLog_MouseMoved(MouseEvent mouseEvent) {
        lblLog.setVisible(true);
    }

    public void btnAccount_MouseExited(MouseEvent mouseEvent) {
        lblAccounts.setVisible(false);
    }

    public void btnAccount_MouseMoved(MouseEvent mouseEvent) {
        lblAccounts.setVisible(true);
    }

    public void btnStatistics_MouseExited(MouseEvent mouseEvent) {
        lblStatistics.setVisible(false);
    }

    public void btnStatistics_MouseMoved(MouseEvent mouseEvent) {
        lblStatistics.setVisible(true);
    }

    public void btnCategories_MouseExited(MouseEvent mouseEvent) {
        lblCategories.setVisible(false);
    }

    public void btnCategories_MouseMoved(MouseEvent mouseEvent) {
        lblCategories.setVisible(true);
    }

    public void btnAccounts_Click(MouseEvent mouseEvent) {
        ManageAccounts.showForm();
    }

    public void btnClose_Click(ActionEvent actionEvent) {
        if (AlertMessage.showAlertYesNo()) {
            Platform.exit();
        }
    }

    public void btnSales_Click(MouseEvent mouseEvent) {
        FormSales.showForm();
    }

    public void btnInfo_Click(MouseEvent mouseEvent) {
        EditAccounts.accountViewing = loggedAccount;
        EditAccounts.mainFormClick = true;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditAccounts.fxml")));
            EditAccounts.editAccountsStage.setTitle("Edit Accounts");
            EditAccounts.editAccountsStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/edit.png");
            EditAccounts.editAccountsStage.getIcons().add(icon);
            EditAccounts.editAccountsStage.setResizable(false);
            EditAccounts.editAccountsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCategories_MouseClicked(MouseEvent mouseEvent) {
        ManageCategories.showForm();
    }
}
