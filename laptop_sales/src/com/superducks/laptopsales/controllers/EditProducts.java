package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditProducts {
    public TextField txtNameProduct;
    public ImageView imgProduct;
    public TextField txtPrice;
    public TextArea txtInfo;
    public TextField txtNSX;
    public TextField txtCategoryName;
    public Button btnImage;
    public String urlimage;
    public AnchorPane ap;
    public static int value;
    public static String Name;
    public void initialize(){showData();}

    private void showData() {
       String sql = "SELECT * FROM laptop_sales.products where productID='"+value+"'";
       try {
           ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                txtCategoryName.setText(Name);
                txtNameProduct.setText(rs.getString(3));
                txtNSX.setText(rs.getString(4));
                txtInfo.setText(rs.getString(5));
                imgProduct.setImage(new Image(rs.getString(6)));
                txtPrice.setText(rs.getString(7));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static Stage mainStage = new Stage();
    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditProducts.fxml")));
            mainStage.setTitle("Main Form");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/laptop-icon.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Changed(KeyEvent keyEvent) {
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
}

