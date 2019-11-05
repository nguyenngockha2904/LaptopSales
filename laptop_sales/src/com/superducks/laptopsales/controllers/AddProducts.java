package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class AddProducts {
    private static Boolean changed = false;
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
    private String urlImage = "";
    public AnchorPane ap;
    ArrayList<String> data;
   public void initialize(){
       data = new ArrayList<String>();
       String sql="SELECT * FROM `category`";
       Connection conn=ConnectDatabase.Connect();
       try {
           ResultSet rs=conn.createStatement().executeQuery(sql);
           while(rs.next()){
               cbocategory.getItems().add(rs.getString(1));
               data.add(rs.getString(2));;
           }
               } catch (SQLException e) {
           e.printStackTrace();
       }
       cbocategory.getSelectionModel().selectFirst();
       txtCategoryName.setText(data.get(0));
   }

    public void btnAdd_Click(MouseEvent mouseEvent) {
        String categoryID = cbocategory.getSelectionModel().getSelectedItem().toString();
        String name = txtNameProduct.getText();
        String nsx = txtNSX.getText();
        String info=txtInfo.getText();
        Double price=Double.parseDouble(txtPrice.getText());
        String url = urlImage;
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
                    pst.setString(5, url);
                    pst.setDouble(6, price);
                    int row =pst.executeUpdate();
                    AlertMessage.showAlert("Added new product " +row, "tick");
                    changed = true;
                    mainStage.close();
                    showForm();
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
            mainStage.setOnCloseRequest(e->rsFormMC());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void rsFormMC() {
        if(changed.equals(true)) {
            ManageCategories.resetForm();
            changed = false;
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
            urlImage=file.toURI().toString();
            Image image=new Image(urlImage);
            imgProduct.setImage(image);
            btnAdd.setVisible(true);
            btnNonAdd.setVisible(false);
        }
        System.out.println(urlImage);
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

    public void btnClose_Clck(MouseEvent mouseEvent) {
       mainStage.close();
       rsFormMC();
    }
}
