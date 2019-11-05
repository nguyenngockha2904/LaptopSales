package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditProducts {
    private static Boolean changed = false;
    public TextField txtNameProduct;
    public ImageView imgProduct;
    public TextField txtPrice;
    public TextArea txtInfo;
    public TextField txtNSX;
    public TextField txtCategoryName;
    public Button btnImage;
    public String urlimage;
    public AnchorPane ap;
    public static int ProductID;
    public static String CategoryID;
    public ImageView btnUpdate;
    public ImageView btnClose;
    private static Stage mainStage = new Stage();
    public ImageView btnNonUpdate;
    String nameProduct, nsx, info, price;

    public void initialize() {
        String sql = "Select categoryName from laptop_sales.category where categoryID='" + CategoryID + "'";
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                txtCategoryName.setText(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showData();
        if(MainForm.loggedPosition.equals("staff")) {
            btnUpdate.setVisible(false);
            btnClose.setVisible(false);
            txtInfo.setEditable(false);
            txtNSX.setEditable(false);
            txtPrice.setEditable(false);
            txtNameProduct.setEditable(false);
            btnNonUpdate.setVisible(false);
        }
    }

    private void showData() {
       String sql = "SELECT * FROM laptop_sales.products where productID='"+ProductID+"'";
       try {
           ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                txtNameProduct.setText(rs.getString(3));
                txtNSX.setText(rs.getString(4));
                txtInfo.setText(rs.getString(5));
                if(!rs.getString(6).equals(""))
                    imgProduct.setImage(new Image(rs.getString(6)));
                txtPrice.setText(rs.getString(7));

                nameProduct = txtNameProduct.getText();
                nsx = txtNSX.getText();
                info = txtInfo.getText();
                price = txtPrice.getText();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditProducts.fxml")));
            mainStage.setTitle("Edit Products");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/compose.png");
            mainStage.getIcons().add(icon);
            mainStage.show();
            mainStage.setResizable(false);
            mainStage.setOnCloseRequest(e->rsFormMC());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        }
        System.out.println(urlimage);
    }

    private static void rsFormMC() {
        if(changed.equals(true)) {
            ManageCategories.resetForm();
            changed = false;
        }
    }

    public void btnUpdateClick(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            String sql="UPDATE laptop_sales.products SET name = '"+txtNameProduct.getText()+"', producer = '"+txtNSX.getText()+"', info = '"+txtInfo.getText()+"', img = '"+imgProduct.getImage().getUrl().toString()+"', price = '"+Integer.parseInt(txtPrice.getText())+"' WHERE (productID = '"+ProductID+"')";
            try {
                int row = ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                System.out.println(row);
                AlertMessage.showAlert("Information has been updated", "tick");
                changed = true;
                mainStage.close();
                showForm();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnClose_Click(MouseEvent mouseEvent) {
        mainStage.close();
        rsFormMC();
    }

    public void textChanged(KeyEvent keyEvent) {
        if(!nameProduct.equals(txtNameProduct.getText()) || !nsx.equals(txtNSX.getText()) || !info.equals(txtInfo.getText()) || !price.equals(txtPrice.getText()))
            if(!txtNameProduct.getText().equals("") && !txtInfo.getText().equals("") && !txtNSX.getText().equals("") && !txtPrice.getText().equals("")) {
                btnUpdate.setVisible(true);
                btnNonUpdate.setVisible(false);
            }
            else{
                btnUpdate.setVisible(false);
                btnNonUpdate.setVisible(true);
            }
        else{
            btnUpdate.setVisible(false);
            btnNonUpdate.setVisible(true);
        }
    }
}

