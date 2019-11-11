package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static com.superducks.laptopsales.controllers.EditCategory.categoryID;

public class EditProducts {
    static Boolean changed = false;
    public TextField txtNameProduct;
    public ImageView imgProduct;
    public TextField txtPrice;
    public TextArea txtInfo;
    public TextField txtNSX;
    public HBox hboxImage;
    public Button btnNew;
    public Button btnADD;
    public DatePicker dtpDate;
    public TextField txtAmount;
    public TextField txtCaptial;
    public VBox VboxComboUpdate;
    public ComboBox cbCategory;
    public VBox vboxGroupAdd;
    public BorderPane br;
    public HBox HboxComboUpdate;
    String urlimage;
    static int ProductID;
    static String CategoryID;
    static int chage;
    public Button btnUpdate;
    public ImageView btnClose;
    static Stage mainStage = new Stage();
    public Button btnairt;
    String nameProduct, nsx, info, price,date,amount,captial;

    public void initialize() {

        if (chage==0){
            btnairt.setVisible(false);
            hboxImage.setVisible(true);
            showdatawithEditProduct(CategoryID);
            HboxComboUpdate.setVisible(true);
            showDatawithEditProduct();
        }else if (chage==1){
            vboxGroupAdd.setVisible(true);
            showdatawithAddProduct();
        }else{
            btnUpdate.setVisible(false);
            HboxComboUpdate.setVisible(true);
            showdatawithEditProduct(CategoryID);
            showDatawithEditProduct();
        }


        if(MainForm.loggedPosition.equals("staff")) {
            btnUpdate.setVisible(false);
            btnClose.setVisible(false);
            txtInfo.setEditable(false);
            txtNSX.setEditable(false);
            txtPrice.setEditable(false);
            txtNameProduct.setEditable(false);
        }
    }
    private void showdatawithEditProduct(String categoryID){
        String sql = "Select * from category where categoryID='" + categoryID + "'";
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                cbCategory.setValue(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showDatawithEditProduct() {
       String sql = "SELECT * FROM products where productID='"+ProductID+"'";
       try {
           ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                txtNameProduct.setText(rs.getString(3));
                txtNSX.setText(rs.getString(4));
                txtInfo.setText(rs.getString(5));
                if(!rs.getString(6).equals(""))
                    imgProduct.setImage(new Image(rs.getString(6)));
                txtPrice.setText(rs.getString(7));
                dtpDate.getEditor().setText(rs.getString(8));
                txtAmount.setText(rs.getString(9));
                txtCaptial.setText(rs.getString(10));
                nameProduct = txtNameProduct.getText();
                nsx = txtNSX.getText();
                info = txtInfo.getText();
                price = txtPrice.getText();
                date=dtpDate.getEditor().getText();
                amount=txtAmount.getText();
                captial=txtCaptial.getText();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    ArrayList<String> ListCategoryID;
    private void showdatawithAddProduct(){
        ObservableList<String> list= FXCollections.observableArrayList();
        ListCategoryID=new ArrayList<>();
        String sql="Select *from category ";
        try {
            ResultSet rs=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                ListCategoryID.add(rs.getString(1));
                list.add(rs.getString(2));
            }
            cbCategory.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void showForm(String title){
        Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditProducts.fxml")));
                EditProducts.mainStage.setTitle(title);
                EditProducts.mainStage.setScene(new Scene(root));
                Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/compose.png");
                EditProducts.mainStage.getIcons().add(icon);
                EditProducts.mainStage.setResizable(false);
                EditProducts.mainStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void btnUpdateClick(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            String sql="UPDATE products SET name = '"+txtNameProduct.getText()+"', producer = '"+txtNSX.getText()+"', info = '"+txtInfo.getText()+"', img = '"+imgProduct.getImage().getUrl()+"', price = '"+Integer.parseInt(txtPrice.getText())+"', `date` = '"+dtpDate.getEditor().getText()+"', `amount` = '"+txtAmount.getText()+"', `capital` = '"+txtCaptial.getText()+"' WHERE (productID = '"+ProductID+"')";
            try {
                int row = ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                System.out.println(row);
                AlertMessage.showAlert("Information has been updated", "tick");
                changed = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void textChanged(KeyEvent keyEvent) {
        if(chage==0){if(!nameProduct.equals(txtNameProduct.getText()) || !nsx.equals(txtNSX.getText()) || !info.equals(txtInfo.getText()) || !price.equals(txtPrice.getText())|| !date.equals(dtpDate.getEditor().getText())|| !amount.equals(txtAmount.getText())|| !captial.equals(txtCaptial.getText()))
            if(!txtNameProduct.getText().equals("") && !txtInfo.getText().equals("") && !txtNSX.getText().equals("") && !txtPrice.getText().equals("")&& !dtpDate.getEditor().equals("")&& !txtAmount.getText().equals("")&& !txtCaptial.getText().equals("")) {
                btnUpdate.setDisable(false);
            }
            else{
                btnUpdate.setDisable(true);
            }
        else{
            btnUpdate.setDisable(true);
            }
        }else{if(!txtNameProduct.getText().equals("") && !txtInfo.getText().equals("")&&!txtPrice.getText().equals("") && !txtNSX.getText().equals("")&& !dtpDate.getEditor().equals("")&& !txtAmount.getText().equals("")&& !txtCaptial.getText().equals("")){
            btnADD.setDisable(false);
        }
        else{
            btnADD.setDisable(true);
        }}
    }
    int i=0;
    public void btnaritClick(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()){
            FormSales.cout=i;
            i++;
            mainStage.close();
            String sql="INSERT INTO cart (img, name, total) VALUES ('"+imgProduct.getImage().getUrl().toString()+"', '"+txtNameProduct.getText()+"', '"+txtPrice.getText()+"')";
            try {
                int r= ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                System.out.println("insert +"+r);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    public void Editimage(MouseEvent mouseEvent) {
        Stage stage=(Stage) br.getScene().getWindow();
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

    public void btnNewClicked(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            txtNameProduct.clear();
            txtCaptial.clear();
            txtAmount.clear();
            txtPrice.clear();
            txtNSX.clear();
            txtInfo.clear();
            dtpDate.getEditor().clear();
            int index = cbCategory.getSelectionModel().getSelectedIndex();
            System.out.println(ListCategoryID.get(index));
        }

    }

    public void btnAddclicked(MouseEvent mouseEvent) {
        if (AlertMessage.showAlertYesNo()) {
            int index = cbCategory.getSelectionModel().getSelectedIndex();
            String sql = "INSERT INTO `products` (`categoryID`, `name`, `producer`, `info`, `img`, `price`,`date`, `amount`, `capital`) VALUES ('" + ListCategoryID.get(index) + "', '" + txtNameProduct.getText() + "', '" + txtNSX.getText() + "', '" + txtInfo.getText() + "', '" + urlimage + "', '" + Integer.parseInt(txtPrice.getText()) + "','" + dtpDate.getEditor().getText() + "', '" + Integer.parseInt(txtAmount.getText()) + "', '" + Integer.parseInt(txtCaptial.getText()) + "')";
            try {
                int row = ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                System.out.println("insert +" + row);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

