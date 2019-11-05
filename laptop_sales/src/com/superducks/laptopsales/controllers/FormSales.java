package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.swing.text.Element;
import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class FormSales {
    public AnchorPane Stagepane;
    public FlowPane fpn;
    public Label label;
    public Label lblcount;
    public static int cout;
    public void initialize() {

        ShowData();
    }

    private void ShowData() {

        String sql="select * from laptop_sales.products";
        ObservableList<ImageView> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                String pid=rs.getString(1),cid=rs.getString(2);
                Image img=new Image(rs.getString(6));
                ImageView imageView=new ImageView(img);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setOnMouseClicked(e-> {
                        event(pid,cid);

                });
                listIMG.add(imageView);
            }

            fpn.getChildren().addAll(listIMG);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Stage mainStage =new Stage();
    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/FormSales.fxml")));
            mainStage.setTitle("Sales Form");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/categories.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void event(String pid,String cid){
        EditProducts.CategoryID=cid;
        EditProducts.ProductID=Integer.parseInt(pid);
        EditProducts.chage=1;
        EditProducts.showForm();
    }
    public void seach(String categoryID){
        String sql="SELECT * FROM laptop_sales.products where categoryID='"+categoryID+"'";
        ObservableList<ImageView> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                String pid=rs.getString(1),cid=rs.getString(2);
                Image img=new Image(rs.getString(6));
                ImageView imageView=new ImageView(img);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setOnMouseClicked(e-> {
                    event(pid,cid);

                });
                listIMG.add(imageView);
            }
            fpn.getChildren().clear();
            fpn.getChildren().addAll(listIMG);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void itmLkPCshow(ActionEvent actionEvent) {
        seach("lkpc");
    }

    public void itmLaptopShow(ActionEvent actionEvent) {
        seach("laptop");
    }

    public void itmManHinhShow(ActionEvent actionEvent) {
        seach("mh");
    }

    public void itmBanPhimShow(ActionEvent actionEvent) {
        seach("bp");
    }

    public void itmchuotShow(ActionEvent actionEvent) {
        seach("ch");
    }

    public void itmTaiNgheShow(ActionEvent actionEvent) {
        seach("tn");
    }

    public void itmLoaShow(ActionEvent actionEvent) {
        seach("loa");
    }
}
