package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewProducts {
    static Stage mainStage = new Stage();
    static String product_id = "";
    public ImageView imgAvatar;
    public Label lblType;
    public Label lblProducer;
    public Label lblName;
    public Label lblInformation;
    public Label lblPrice;

    public void initialize() {
        showData();
    }

    private void showData() {
        String sql = "SELECT * FROM products where id='" + product_id + "'";
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                lblName.setText(rs.getString(3));
                lblProducer.setText(rs.getString(4));
                lblInformation.setText(rs.getString(5));
                if (!rs.getString(6).equals(""))
                    imgAvatar.setImage(new Image(rs.getString(6)));
                lblPrice.setText(String.format("%,2d", rs.getInt(7))+" VND");
                String sqlC = "select * from categories where id='"+rs.getString("category_id") +"';";
                ResultSet rst = ConnectDatabase.Connect().createStatement().executeQuery(sqlC);
                if(rst.next())
                    lblType.setText(rst.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
