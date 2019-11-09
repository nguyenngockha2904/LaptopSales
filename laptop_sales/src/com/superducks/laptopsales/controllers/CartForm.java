package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartForm {
    public Button btnPayment;
    public TextField txtTotalPayment;
    public DatePicker dpkSalesDate;
    public TextField txtBillID;
    public GridPane gridPane;
    public ScrollPane scrollPane;
    ArrayList<Integer> idCart;
    int nRowsCT = 0;
    List<ImageView> listIMG=new ArrayList<>(),ListDelete=new ArrayList<>();
    List<TextField> ListtxtName=new ArrayList<>(),ListAmount=new ArrayList<>(),ListTotal=new ArrayList<>(),ListtxtValues=new ArrayList<>();
    public void initialize() {
        showDateAndIDbiLL();
        showdata();}

    private void showDateAndIDbiLL() {

    }

    private void showdata() {
        dpkSalesDate.setValue(LocalDate.now());
        nRowsCT=0;
        gridPane.getChildren().clear();
        idCart=new ArrayList<>();
        String sql="SELECT * FROM cart";
        try {
            ResultSet rs=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while(rs.next()){
                idCart.add(nRowsCT);
               String id=rs.getString(1);
                ImageView imageView=new ImageView(new Image(rs.getString(2)));
                imageView.setFitWidth(70);
                imageView.setFitHeight(70);
                imageView.setOnMouseClicked(e->ImgClick());
                listIMG.add(imageView);
                gridPane.add(imageView,0,nRowsCT);
                TextField txtNameProduct = new TextField(rs.getString(3));
                ListtxtName.add(txtNameProduct);
                txtNameProduct.setMaxWidth(300);
                txtNameProduct.setEditable(false);
                gridPane.add(txtNameProduct,1,nRowsCT);
                TextField txtAmount = new TextField();
                ListAmount.add(txtAmount);
                txtAmount.setPrefWidth(100);
                txtAmount.setOnKeyReleased(e->TriGia_Change());
                gridPane.add(txtAmount,2,nRowsCT);
                TextField txtTotal=new TextField(rs.getString(4));
                ListTotal.add(txtTotal);
                txtTotal.setPrefWidth(100);
                gridPane.add(txtTotal,3,nRowsCT);
                TextField txtValue=new TextField();
                ListtxtValues.add(txtValue);
                txtValue.setPrefWidth(100);
                gridPane.add(txtValue,4,nRowsCT);
                txtValue.setOnKeyReleased(e->TriGia_Change());
                ImageView imgDelete=new ImageView(new Image("com/superducks/laptopsales/icons/close.png"));
                ListDelete.add(imgDelete);
                imgDelete.setFitWidth(20);
                imgDelete.setFitHeight(20);
                 imgDelete.setOnMouseClicked(e->DeleteClick(id));
                gridPane.add(imgDelete,5,nRowsCT);
                nRowsCT++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ImgClick() {
        System.out.println(idCart);
    }

    private void TriGia_Change() {
        float TongTriGia = 0;
        for (int i = 0; i < nRowsCT; i++){
            float SoLuong = Float.parseFloat(ListAmount.get(i).getText());
            float DonGia = Float.parseFloat(ListTotal.get(i).getText());
            float TriGia = SoLuong * DonGia;
            ListtxtValues.get(i).setText(String.format("%,12.0f", TriGia));
            TongTriGia += TriGia;
        }
        txtTotalPayment.setText(String.format("%,12.0f", TongTriGia));
    }

    private void DeleteClick(String idcart) {
        String sql="DELETE FROM cart WHERE (idcart = '"+idcart+"')";
        try {
            int row=ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
            txtTotalPayment.clear();
            showdata();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Stage mainStage =new Stage();
    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/CartForm.fxml")));
            mainStage.setTitle("Cart Form");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/cart1.png");
            mainStage.getIcons().add(icon);
            mainStage.setResizable(false);
            mainStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
