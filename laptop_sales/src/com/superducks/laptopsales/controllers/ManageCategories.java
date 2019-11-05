package com.superducks.laptopsales.controllers;
import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
public class ManageCategories {

    public ComboBox cboCategory;
    public TableView<Products> tblProducts;
    public TableColumn clmCategoryID;
    public ImageView btnAdd;
    public TableColumn clmID;
    public TableColumn clmName;
    public TableColumn clmPrice;
    public AnchorPane ap;
    public ImageView btnEdit;
    public ImageView btnDelete;
    static ArrayList<String> categoryid;

    public void initialize() {
        cboCategory.setValue("All");
        String sql="select *from `category`";
        categoryid=new ArrayList<>();
        try {
            ResultSet s=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (s.next()) {
                categoryid.add(s.getString(1));
                cboCategory.getItems().add(s.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        categoryid.add("All");
        cboCategory.getItems().add("All");
        showdata();
    }

    public void showdata(){
        ObservableList<Products> data = FXCollections.observableArrayList();
        String query="SELECT * FROM `products`";
        try {
            ResultSet rs= ConnectDatabase.Connect().createStatement().executeQuery(query);
            while(rs.next()) {
                Products p= new Products(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(7));
                data.add(p);

            }

            clmID.setCellValueFactory(new PropertyValueFactory<Products,String>("productId"));
            clmCategoryID.setCellValueFactory(new PropertyValueFactory<Products,String>("categoryId"));
            clmName.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<Products,String>("price"));
            tblProducts.getItems().clear();
            tblProducts.setItems(data);
            tblProducts.getSelectionModel().selectFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cboCategory(ActionEvent actionEvent)    {
        System.out.println(cboCategory.getSelectionModel().getSelectedItem().toString());

//        System.out.println(categoryid.get(index));
        int index=cboCategory.getSelectionModel().getSelectedIndex();
        ObservableList<Products> data = FXCollections.observableArrayList();
        String sql="";
        if(categoryid.get(index).equals("All"))
        {
            sql="SELECT * FROM laptop_sales.products";
        } else {
            sql = "SELECT * FROM laptop_sales.products where categoryID='" + categoryid.get(index) + "'";
        }
        try {
            ResultSet rs=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while(rs.next()) {
                Products p= new Products(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(7));
                data.add(p);

            }
            clmID.setCellValueFactory(new PropertyValueFactory<Products,String>("productId"));
            clmCategoryID.setCellValueFactory(new PropertyValueFactory<Products,String>("categoryId"));
            clmName.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<Products,String>("price"));
            clmCategoryID.setCellFactory(TextFieldTableCell.<Products>forTableColumn());

            tblProducts.getItems().clear();
            tblProducts.setItems(data);
            tblProducts.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Stage mainStage =new Stage();

    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/ManageCategories.fxml")));
            mainStage.setTitle("Manage Categories");
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
    public void btnAddclick(MouseEvent mouseEvent) {
        AddProducts.showForm();

        showdata();
    }
    static void resetForm() {
        mainStage.close();
        showForm();
    }

    public void btnEditClick(MouseEvent mouseEvent) {

        Products p=tblProducts.getSelectionModel().getSelectedItem();
        EditProducts.ProductID=p.getProductId();
        EditProducts.CategoryID=p.getCategoryId();
        EditProducts.showForm();
        showdata();
    }
    public void mouseclick(MouseEvent mouseEvent) {
        btnEdit.setVisible(true);
        btnDelete.setVisible(true);


    }

    public void btnDeleteClick(MouseEvent mouseEvent) {
        Products p=tblProducts.getSelectionModel().getSelectedItem();
        if(AlertMessage.showAlertYesNo()){
//            System.out.println("yes");

            String sql="DELETE FROM products WHERE productID ="+p.getProductId();
            try {
                ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                showdata();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else{
            System.out.println("no");
        }
//

    }
}
