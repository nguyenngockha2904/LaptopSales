package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditWarehouse {
    static String product_id = "";
    static boolean change = false;
    public TextField txtProduct;
    public TextField txtRemaining;
    public ImageView btnNonAccept;
    public ImageView btnAccept;
    static Stage mainStage = new Stage();
    private String product_name = "", products_remaining = "0";

    public void initialize() {
        showData();
        check();
    }

    private void showData() {
        String sql = "select * from products where id='"  +product_id + "';";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            if(rs.next()) {
                product_name = rs.getString("name");
                txtProduct.setText(product_name);
            }
            String sqlCheck = "select * from warehouse where product_id =" + product_id + ";";
            ResultSet rst = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlCheck);
            if (rst.next()) {
                products_remaining = rst.getString("products_remaining");
                txtRemaining.setText(products_remaining);
            } else {
                txtRemaining.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        check();
    }
    public void btnAccept_Clicked(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            String sqlCheck = "select * from warehouse where product_id =" + product_id + ";";
            try {
                ResultSet rst = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlCheck);
                if (rst.next()) {
                    String update = "UPDATE `warehouse` SET `products_remaining` = ? WHERE (`product_id` = ?);";
                    PreparedStatement pst = Objects.requireNonNull(ConnectDatabase.Connect()).prepareStatement(update);
                    pst.setString(1, txtRemaining.getText());
                    pst.setString(2, product_id);
                    pst.executeUpdate();
                    AlertMessage.showAlert("All information updated", "tick");
                    change = true;
                } else {
                    String add = "INSERT INTO `warehouse` (`product_id`, `products_remaining`, `products_sold`) VALUES (?, ?, '0');";
                    PreparedStatement pst = Objects.requireNonNull(ConnectDatabase.Connect()).prepareStatement(add);
                    pst.setString(1, product_id);
                    pst.setString(2, txtRemaining.getText());
                    pst.executeUpdate();
                    AlertMessage.showAlert("All information updated", "tick");
                    change = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        showData();
        check();
    }

    private void check() {
        String regex = "^[0-9_-]{1,3}$";
        if(txtRemaining.getText().matches(regex) && !txtRemaining.getText().equals(products_remaining)) {
            btnAccept.setVisible(true);
            btnNonAccept.setVisible(false);
        } else {
            btnAccept.setVisible(false);
            btnNonAccept.setVisible(true);
        }
        txtRemaining.requestFocus();
    }

    public void text_Changed(KeyEvent keyEvent) {
        check();
    }
}
