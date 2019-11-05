package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javax.swing.text.Element;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormSales {
    public AnchorPane Stagepane;
    public FlowPane fpn;
    public Label label;
    ArrayList<Integer> ProductIDlist;
    ArrayList<String> CategoryIDlist;
    public void initialize() {
        ShowData();
    }

    private void ShowData() {
        ProductIDlist=new ArrayList<>();
        CategoryIDlist=new ArrayList<>();
        String sql="select * from laptop_sales.products";
        ObservableList<ImageView> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                ProductIDlist.add(rs.getInt(1));
                CategoryIDlist.add(rs.getString(2));
                Image img=new Image(rs.getString(6));
                ImageView imageView=new ImageView(img);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setOnMouseClicked(e->event());
                listIMG.add(imageView);
            }

            fpn.getChildren().addAll(listIMG);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void event(){
        EditProducts.CategoryID=CategoryIDlist.get(0);
        EditProducts.ProductID=ProductIDlist.get(0);
        EditProducts.showForm();
    }

    public void itmLkPCshow(ActionEvent actionEvent) {
    }

    public void itmLaptopShow(ActionEvent actionEvent) {
    }

    public void itmManHinhShow(ActionEvent actionEvent) {
    }

    public void itmBanPhimShow(ActionEvent actionEvent) {
    }

    public void itmchuotShow(ActionEvent actionEvent) {
    }

    public void itmTaiNgheShow(ActionEvent actionEvent) {
    }

    public void itmLoaShow(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }
}
