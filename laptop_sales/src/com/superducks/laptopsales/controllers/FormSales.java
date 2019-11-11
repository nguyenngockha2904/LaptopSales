package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FormSales {
    public Label label;
    public static int cout;
    public TilePane tpn;
    public ComboBox cbSearch;
    public TextField txtTo;
    public TextField txtForm;
    public ComboBox cbcategory;

    public void initialize() {
        showcbSearch();
        ShowData();
    }

    private void showcbSearch() {
//        "Nhập Kho lâu Nhất","Nhập Kho gần Đây","Giá Tăng Dần","Giá Giảm Dần","Tồn Kho nhiều nhất","Tồn Kho ít Nhất","Bán Chạy nhất","Bán Ế Nhất"
        ObservableList<String> list= FXCollections.observableArrayList();
        String sql="Select *from category ";
        try {
            ResultSet rs=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                list.add(rs.getString(2));
            }
            cbcategory.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void ShowData() {

        String sql="select * from products";
        ObservableList<VBox> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                VBox vBox=new VBox();
                String pid=rs.getString(1),cid=rs.getString(2);
                ImageView imageView=new ImageView(new Image(rs.getString(6)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setBlendMode(BlendMode.MULTIPLY);
                imageView.setEffect(new DropShadow(+25d,0d,+2d, Color.DARKSEAGREEN));
                imageView.setOnMouseClicked(e->event(pid,cid));
                Text text=new Text("  "+rs.getString(7));;
                text.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
                text.setFill(Color.BLACK);
                vBox.getChildren().add(imageView);
                vBox.getChildren().add(text);
                vBox.setStyle(" -fx-text-alignment: center");
                listIMG.add(vBox);
            }
            tpn.getChildren().addAll(listIMG);


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
            mainStage.setResizable(false);
            mainStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void event(String pid,String cid){
        EditProducts.CategoryID=cid;
        EditProducts.ProductID=Integer.parseInt(pid);
        EditProducts.chage=2;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditProducts.fxml")));
            EditProducts.mainStage.setTitle("Infomation Products");
            EditProducts.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/list.png");
            EditProducts.mainStage.getIcons().add(icon);
            EditProducts.mainStage.setResizable(false);
            EditProducts.mainStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCartClick(MouseEvent mouseEvent) {
        CartForm.showForm();
    }

    public void Search(MouseEvent mouseEvent) {
        String sql="SELECT * FROM products where price between '"+Integer.parseInt(txtForm.getText())+"' and '"+Integer.parseInt(txtTo.getText())+"'";
        ObservableList<VBox> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                VBox vBox=new VBox();
                String pid=rs.getString(1),cid=rs.getString(2);
                ImageView imageView=new ImageView(new Image(rs.getString(6)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setBlendMode(BlendMode.MULTIPLY);
                imageView.setEffect(new DropShadow(+25d,0d,+2d, Color.DARKSEAGREEN));
                imageView.setOnMouseClicked(e->event(pid,cid));
                Text text=new Text(" "+rs.getString(7));;
                text.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
                text.setFill(Color.BLACK);
                vBox.getChildren().add(imageView);
                vBox.getChildren().add(text);
                vBox.setStyle(" -fx-text-alignment: center");
                listIMG.add(vBox);
            }
            tpn.getChildren().clear();
            tpn.getChildren().addAll(listIMG);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CbCategoryClick(ActionEvent actionEvent) {
        String sql="SELECT * FROM products where name like'%"+cbcategory.getSelectionModel().getSelectedItem()+"%'";
        ObservableList<VBox> listIMG= FXCollections.observableArrayList();
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                VBox vBox=new VBox();
                String pid=rs.getString(1),cid=rs.getString(2);
                ImageView imageView=new ImageView(new Image(rs.getString(6)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setBlendMode(BlendMode.MULTIPLY);
                imageView.setEffect(new DropShadow(+25d,0d,+2d, Color.DARKSEAGREEN));
                imageView.setOnMouseClicked(e->event(pid,cid));
                Text text=new Text(" "+rs.getString(7));;
                text.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
                text.setFill(Color.BLACK);
                vBox.getChildren().add(imageView);
                vBox.getChildren().add(text);
                vBox.setStyle(" -fx-text-alignment: center");
                listIMG.add(vBox);
            }
            tpn.getChildren().clear();
            tpn.getChildren().addAll(listIMG);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
