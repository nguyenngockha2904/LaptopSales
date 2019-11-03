package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class EditAccounts {
    static String accountViewing = "";
    static Boolean mainFormClick = false;
    private static Boolean changed = false;
    static String accountViewingPosition = "";
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtFullname;
    public PasswordField txtPassword;
    public DatePicker dtpBirthday;
    public ComboBox<String> cbxPosition;
    public TextField txtAddress;
    public TextField txtPhone;
    public ImageView btnClose;
    public ImageView btnEdit;
    public ImageView btnNonEdit;
    private static Stage editAccountsStage = new Stage();
    private String email, fullname, password, position, birthday, address, phone;

    public void initialize() {
        cbxPosition.getItems().addAll("Admin", "Manager", "Staff");
        setUpAnyThings();
    }

    static void showForm() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditAccounts.fxml")));
            editAccountsStage.setTitle("Edit Accounts");
            editAccountsStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/edit.png");
            editAccountsStage.getIcons().add(icon);
            editAccountsStage.show();
            editAccountsStage.setResizable(false);
            editAccountsStage.setOnCloseRequest(e->rsFormMA());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void rsFormMA() {
        if(changed.equals(true)) {
            if(mainFormClick.equals(false))
                ManageAccounts.resetForm();
            changed = false;
            mainFormClick = false;
        }
    }

    private void setUpAnyThings() {
        cbxPosition.setDisable(true);
        txtPassword.setDisable(true);
        if(MainForm.loggedPosition.equals("admin") && !accountViewingPosition.equals("admin")) {
            cbxPosition.setDisable(false);
            txtPassword.setDisable(false);
        }
        if(MainForm.loggedAccount.equals(accountViewing)) {
            txtPassword.setDisable(false);
        }
        String sql = "select * from accounts where username = \""+accountViewing+"\"";
        Connection con = ConnectDatabase.Connect();
        try {
            assert con != null;
            ResultSet rst = con.createStatement().executeQuery(sql);
            while (rst.next()) {
                txtAddress.setText(rst.getString("address"));
                txtEmail.setText(rst.getString("email"));
                txtUsername.setText(rst.getString("username"));
                txtFullname.setText((rst.getString("fullname")));
                txtPassword.setText(rst.getString("password"));
                txtPhone.setText((rst.getString("phone")));
                LocalDate date = LocalDate.parse(rst.getString("birthday"));
                dtpBirthday.setValue(date);
                char[] c = rst.getString("position").toCharArray();
                c[0] = Character.toUpperCase(c[0]);
                String positionNew = new String(c);
                cbxPosition.setValue(positionNew);
                email = txtEmail.getText();
                fullname = txtFullname.getText();
                birthday = dtpBirthday.getValue().toString();
                address = txtAddress.getText();
                phone = txtPhone.getText();
                password = txtPassword.getText();
                position = cbxPosition.getValue().toString().toLowerCase();
                btnEdit.setVisible(false);
                btnNonEdit.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void textChanged(KeyEvent keyEvent) {
        if(!password.equals(txtPassword.getText()) || !email.equals(txtEmail.getText()) || !fullname.equals(txtFullname.getText()) || !address.equals(txtAddress.getText()) || !phone.equals(txtPhone.getText())) {
            btnEdit.setVisible(true);
            btnNonEdit.setVisible(false);
        }
        else {
            btnEdit.setVisible(false);
            btnNonEdit.setVisible(true);
        }
    }

    public void btnEdit_Click(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            String sql = "UPDATE `accounts` SET `password` = ?, `email` = ?, `fullname` = ?, `birthday` = ?, `position` = ?, `address` = ?, `phone` = ? WHERE (`username` = ?);";
            Connection con = ConnectDatabase.Connect();
            try {
                assert con != null;
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtPassword.getText());
                pst.setString(2, txtEmail.getText());
                pst.setString(3, txtFullname.getText());
                pst.setString(4, dtpBirthday.getValue().toString());
                pst.setString(5, cbxPosition.getValue().toString().toLowerCase());
                pst.setString(6, txtAddress.getText());
                pst.setString(7, txtPhone.getText());
                pst.setString(8, txtUsername.getText());
                pst.executeUpdate();
                changed = true;
                AlertMessage.showAlert("Saved changes", "tick");
                if(MainForm.loggedAccount.equals(txtUsername.getText()))
                    if(!MainForm.loggedPosition.equals(cbxPosition.getValue().toString().toLowerCase())) {
                        AlertMessage.showAlert("Your position has been changed, You will have to login again", "warning");
                        editAccountsStage.close();
                        ManageAccounts.closeForm();
                        MainForm.closeForm();
                        Main.getPrimaryStage().close();
                        Main.getPrimaryStage().show();
                    }
                setUpAnyThings();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnClose_Click(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            editAccountsStage.close();
            ManageAccounts.resetForm();
        }
    }


    public void textChanged_Second(ActionEvent actionEvent) {
        if(!birthday.equals(dtpBirthday.getValue().toString()) || !position.equals(cbxPosition.getValue().toString().toLowerCase())) {
            btnEdit.setVisible(true);
            btnNonEdit.setVisible(false);
        }
        else {
            btnEdit.setVisible(false);
            btnNonEdit.setVisible(true);
        }
    }
}
