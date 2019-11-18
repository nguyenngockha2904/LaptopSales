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
    static String loggedID = "";
    static String loggedPosition = "";
    public MenuItem itmAccount;
    public MenuItem itmLogOut;
    private static Stage mainStage = new Stage();
    public MenuItem btnClose;
    public ImageView btnSales;
    public Label lblSales;
    public Label lblInfo;
    public ImageView btnInfo;
    public ImageView btnAccounts;
    public Label lblAccounts;
    public ImageView btnStatistics;
    public ImageView btnCategories;
    public Label lblStatistics;
    public Label lblCategories;
    public Tab tabAdmin;
    public Tab tabHome;
    public ImageView btnWarehouse;
    public Label lblWarehouse;
    public ImageView btnProducts;
    public Label lblProducts;

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
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/lt-cc.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);

            mainStage.setOnCloseRequest(e->System.exit(0));
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
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/laptop-icon.png");
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

    public void btnProducts_MouseExited(MouseEvent mouseEvent) {
        lblProducts.setVisible(false);
    }

    public void btnProducts_MouseMoved(MouseEvent mouseEvent) {
        lblProducts.setVisible(true);
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

    public void btnWarehouse_MouseExited(MouseEvent mouseEvent) {
        lblWarehouse.setVisible(false);
    }

    public void btnWarehouse_MouseMoved(MouseEvent mouseEvent) {
        lblWarehouse.setVisible(true);
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
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/Sales.fxml")));
            Sales.salesStage.setTitle("Sales");
            Sales.salesStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/laptop-icon.png");
            Sales.salesStage.getIcons().add(icon);
            Sales.salesStage.setResizable(false);
            Sales.salesStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnInfo_Click(MouseEvent mouseEvent) {
        openFormInfo();
    }

    private void openFormInfo() {
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

    public void btnInfoMenu_Click(ActionEvent actionEvent) {
        openFormInfo();
    }

    public void btnStatistics_Clicked(MouseEvent mouseEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/Statistics.fxml")));
            Statistics.mainStage.setTitle("Statistics");
            Statistics.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/statistics.png");
            Statistics.mainStage.getIcons().add(icon);
            Statistics.mainStage.setResizable(false);
            Statistics.mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnWarehouse_MouseClicked(MouseEvent mouseEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/ManageWarehouse.fxml")));
            ManageWarehouse.mainStage.setTitle("Manage Warehouse");
            ManageWarehouse.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/warehouse.png");
            ManageWarehouse.mainStage.getIcons().add(icon);
            ManageWarehouse.mainStage.setResizable(false);
            ManageWarehouse.mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnProducts_Clicked(MouseEvent mouseEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/Products.fxml")));
            Products.mainStage.setTitle("Products");
            Products.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/items.png");
            Products.mainStage.getIcons().add(icon);
            Products.mainStage.setResizable(false);
            Products.mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
