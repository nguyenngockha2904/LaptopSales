package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ManageAccounts {
    public TableView <Person> tblAccounts;
    public ImageView btnAdd;
    public ImageView btnEdit;
    public ImageView btnDelete;
    private static Stage mainStage = new Stage();
    public TableColumn<Person, String> clmUsername;
    public TableColumn<Person, String> clmFullname;
    public TableColumn<Person, String> clmPosition;
    public TableColumn<Person, Integer> clmID;
    public ImageView btnClose;
    private Connection con = null;
    private ResultSet rst = null;
    private PreparedStatement pst = null;

    public void initialize() {
        showTable();
    }
    //Show Form
    static void showForm() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/ManageAccounts.fxml")));
            mainStage.setTitle("Manage Accounts");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons//accounts.png");
            mainStage.getIcons().add(icon);
            mainStage.setResizable(false);
            mainStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Show Table
    private void showTable() {
        ObservableList<Person> data = FXCollections.observableArrayList();
        String sql = "select * from accounts";
        try {
            con = ConnectDatabase.Connect();
            assert con != null;
            rst = con.createStatement().executeQuery(sql);
            while(rst.next()) {
                data.add(new Person(
                        rst.getInt("id"),
                        rst.getString("username"),
                        rst.getString("fullname"),
                        rst.getString("position"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clmID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
        clmUsername.setCellValueFactory(new PropertyValueFactory<Person, String>("username"));
        clmFullname.setCellValueFactory(new PropertyValueFactory<Person, String>("fullname"));
        clmPosition.setCellValueFactory(new PropertyValueFactory<Person, String>("position"));
        tblAccounts.setItems(data);
        tblAccounts.getSelectionModel().selectFirst();
    }

    //Add Accounts
    public void btnAdd_Click(MouseEvent mouseEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/AddAccounts.fxml")));
            AddAccounts.addAccountsStage.setTitle("Add Accounts");
            AddAccounts.addAccountsStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/add-person.png");
            AddAccounts.addAccountsStage.getIcons().add(icon);
            AddAccounts.addAccountsStage.setResizable(false);
            AddAccounts.addAccountsStage.showAndWait();
            if(AddAccounts.changed) {
                AddAccounts.changed = false;
                showTable();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Delete Accounts
    public void btnDelete_Clicked(MouseEvent mouseEvent) {
        Person account = tblAccounts.getSelectionModel().getSelectedItem();
        String username = account.getUsername();
        String position = account.getPosition();
        if(MainForm.loggedPosition.equals("admin") && !position.equals("admin")) {
            if (AlertMessage.showAlertYesNo()) {
                String sql = "delete from accounts where username = ?";
                con = ConnectDatabase.Connect();
                try {
                    assert con != null;
                    pst = con.prepareStatement(sql);
                    pst.setString(1, username);
                    pst.executeUpdate();
                    AlertMessage.showAlert("Deleted account " + username, "tick");
                    showTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (MainForm.loggedPosition.equals("manager") && position.equals("staff")) {
            if (AlertMessage.showAlertYesNo()) {
                String sql = "delete from accounts where username = ?";
                con = ConnectDatabase.Connect();
                try {
                    assert con != null;
                    pst = con.prepareStatement(sql);
                    pst.setString(1, username);
                    pst.executeUpdate();
                    AlertMessage.showAlert("Deleted account " + username, "tick");
                    showTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else
            AlertMessage.showAlert("You don't have permission to delete this account", "error");
    }

    //Edit Accounts
    public void btnEdit_Click(MouseEvent mouseEvent) {
        Person account = tblAccounts.getSelectionModel().getSelectedItem();
        EditAccounts.accountViewing = account.getUsername();
        EditAccounts.accountViewingPosition = account.getPosition();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditAccounts.fxml")));
            EditAccounts.editAccountsStage.setTitle("Edit Accounts");
            EditAccounts.editAccountsStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/edit.png");
            EditAccounts.editAccountsStage.getIcons().add(icon);
            EditAccounts.editAccountsStage.setResizable(false);
            EditAccounts.editAccountsStage.showAndWait();
            if(EditAccounts.changed) {
                EditAccounts.changed = false;
                showTable();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Close
    public void btnClose_Click(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            mainStage.close();
        }
    }

    //Class Person
    public static class Person {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty username;
        private final SimpleStringProperty fullname;
        private final SimpleStringProperty position;

        private Person(Integer Id, String uName, String fName, String psTion) {
            this.id = new SimpleIntegerProperty(Id);
            this.username = new SimpleStringProperty(uName);
            this.fullname = new SimpleStringProperty(fName);
            this.position = new SimpleStringProperty(psTion);
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(String fName) {
            username.set(fName);
        }


        public String getUsername() {
            return username.get();
        }

        public void setUsername(String fName) {
            username.set(fName);
        }

        public String getFullname() {
            return fullname.get();
        }

        public void setFullname(String fName) {
            fullname.set(fName);
        }

        public String getPosition() {
            return position.get();
        }

        public void setPosition(String fName) {
            position.set(fName);
        }
    }
}
