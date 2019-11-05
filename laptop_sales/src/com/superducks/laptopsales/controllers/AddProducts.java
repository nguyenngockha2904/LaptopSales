package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Products;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AddProducts {
    public TextField txtNameProduct;
    public TextField txtNSX;
    public ImageView btnNonAdd;
    public ImageView btnAdd;
    public ImageView btnClose;
    public ImageView imgProduct;
    public TextField txtPrice;
    public TextArea txtInfo;
    public TextField txtCategoryName;
    public ComboBox cbocategory;
    public Button btnImage;
    public String urlimage;
    public AnchorPane ap;
    ArrayList<String> data;
   public void initialize(){
       cbocategory.setValue("chọn danh mục");
       txtCategoryName.setText("tên danh mục");
       data = new ArrayList<String>();
       String sql="SELECT * FROM `category`";
       Connection conn=ConnectDatabase.Connect();
       try {
           ResultSet rs=conn.createStatement().executeQuery(sql);
           while(rs.next()){
               cbocategory.getItems().add(rs.getString(1));
               data.add(rs.getString(2));
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

    public void btnAdd_Click(MouseEvent mouseEvent) {
        String categoryID = cbocategory.getSelectionModel().getSelectedItem().toString();
        String name = txtNameProduct.getText();
        String nsx = txtNSX.getText();
        String info=txtInfo.getText();
        Double price=Double.parseDouble(txtPrice.getText());
        String url=urlimage;
            if(AlertMessage.showAlertYesNo()) {
                String sql = "INSERT INTO `products` (`categoryID`, `name`, `producer`, `info`, `img`, `price`) VALUES (?,?,?,?,?,?);";
                Connection con = ConnectDatabase.Connect();
                try {
                    assert con != null;
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, categoryID);
                    pst.setString(2, name);
                    pst.setString(3, nsx);
                    pst.setString(4, info);
                    pst.setString(5, urlimage);
                    pst.setDouble(6, price);
                    int row =pst.executeUpdate();
                    AlertMessage.showAlert("Added new account " +row, "tick");
                    mainStage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    private static Stage mainStage = new Stage();
   static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/AddProducts.fxml")));
            mainStage.setTitle("Add Products");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/plus.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void click(ActionEvent actionEvent) {
        int index=cbocategory.getSelectionModel().getSelectedIndex();
        txtCategoryName.setText( data.get(index));
    }

    public void addImage(ActionEvent actionEvent) {
        Stage stage=(Stage) ap.getScene().getWindow();
        FileChooser fc= new FileChooser();
        fc.setTitle("Choose a image ");
        FileChooser.ExtensionFilter imageFilter=new FileChooser.ExtensionFilter("image Files","*.jpg","*.png");
        fc.getExtensionFilters().add(imageFilter);
        File file=fc.showOpenDialog(stage);
        if(file !=null){
            urlimage=file.toURI().toString();
            Image image=new Image(urlimage);
            imgProduct.setImage(image);
            btnImage.setVisible(false);
        }
        System.out.println(urlimage);
    }

    public void Changed(KeyEvent keyEvent) {
        if(!txtNameProduct.getText().equals("") && !txtInfo.getText().equals("") && !txtPrice.getText().equals("") && !txtNSX.getText().equals("")){
            btnAdd.setVisible(true);
            btnNonAdd.setVisible(false);
        }
        else{
            btnAdd.setVisible(false);
            btnNonAdd.setVisible(true);
        }
    }
}
