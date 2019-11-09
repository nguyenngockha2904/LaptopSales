package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.Category;
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
    public TableColumn clmCateID;
    public TableColumn clmCategoryName;
    public TableView<Category> tblCategory;
    public ImageView btnCatedelete;
    public ImageView btnCateEdit;
    public ImageView btnAddCategory;
    public static Stage mainStage = new Stage();

    public ManageCategories() {
    }

    public void initialize() {
        showDatatableCategory();
        showdataTableProduct();
    }

    public  void showDatatableCategory() {
        String sql="select *from `category`";
        ObservableList<Category> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()){
                Category c=new Category(rs.getString(1),rs.getString(2));
                data.add(c);
            }
            clmCateID.setCellValueFactory(new PropertyValueFactory<Category,String>("CategoryID"));
            clmCategoryName.setCellValueFactory(new PropertyValueFactory<Category,String>("CategoryName"));
            tblCategory.getItems().clear();
            tblCategory.setItems(data);
            tblCategory.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showdataTableProduct(){
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

    static void showForm(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/ManageCategories.fxml")));
            mainStage.setTitle("Manage Categories");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/categories.png");
            mainStage.getIcons().add(icon);
            mainStage.setResizable(false);
            mainStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnAddclick(MouseEvent mouseEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/AddProducts.fxml")));
            AddProducts.mainStage.setTitle("Add Products");
            AddProducts.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/plus.png");
            AddProducts.mainStage.getIcons().add(icon);
            AddProducts.mainStage.setResizable(false);
            AddProducts.mainStage.showAndWait();
            if(AddProducts.changed) {
                AddProducts.changed = false;
                showDatatableCategory();
                showdataTableProduct();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnEditClick(MouseEvent mouseEvent) {

        Products p=tblProducts.getSelectionModel().getSelectedItem();
        EditProducts.ProductID=p.getProductId();
        EditProducts.CategoryID=p.getCategoryId();
        EditProducts.chage = 0;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditProducts.fxml")));
            EditProducts.mainStage.setTitle("Edit Products");
            EditProducts.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/compose.png");
            EditProducts.mainStage.getIcons().add(icon);
            EditProducts.mainStage.setResizable(false);
            EditProducts.mainStage.showAndWait();
            if(EditProducts.changed) {
                EditProducts.changed = false;
                showDatatableCategory();
                showdataTableProduct();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mouseclick(MouseEvent mouseEvent) {
        btnEdit.setVisible(true);
        btnDelete.setVisible(true);
    }

    public void btnDeleteClick(MouseEvent mouseEvent) {
        Products p=tblProducts.getSelectionModel().getSelectedItem();
        if(AlertMessage.showAlertYesNo()){
            String sql="DELETE FROM products WHERE productID ="+p.getProductId();
            try {
                ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                showdataTableProduct();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("no");
        }
    }

    public void btnAddCategory(MouseEvent mouseEvent) {
        EditCategory.chage = 0;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditCategory.fxml")));
            EditCategory.mainStage = new Stage();
            EditCategory.mainStage.setTitle("Add Categories");
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/plus.png");
            EditCategory.mainStage.getIcons().add(icon);
            EditCategory.mainStage.setScene(new Scene(root));
            EditCategory.mainStage.setResizable(false);
            EditCategory.mainStage.showAndWait();
            if(EditCategory.changed) {
                showDatatableCategory();
                showdataTableProduct();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tblCategoryClicked(MouseEvent mouseEvent) {
        btnCateEdit.setVisible(true);
        btnCatedelete.setVisible(true);
        Category c=tblCategory.getSelectionModel().getSelectedItem();
        String sql = "SELECT * FROM products where categoryID='" + c.getCategoryID() + "'";
        ObservableList<Products> data = FXCollections.observableArrayList();
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
            tblProducts.setItems(data);
            tblProducts.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void btnCateEditClick(MouseEvent mouseEvent) {
        Category c=tblCategory.getSelectionModel().getSelectedItem();
        EditCategory.categoryID = c.getCategoryID();
        EditCategory.categoryName = c.getCategoryName();
        EditCategory.chage = 1;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditCategory.fxml")));
            EditCategory.mainStage = new Stage();
            EditCategory.mainStage.setTitle("Edit Categories");
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/compose.png");
            EditCategory.mainStage.getIcons().add(icon);
            EditCategory.mainStage.setScene(new Scene(root));
            EditCategory.mainStage.setResizable(false);
            EditCategory.mainStage.showAndWait();
            EditCategory.categoryID = "";
            EditCategory.categoryName = "";
            if(EditCategory.changed) {
                showDatatableCategory();
                showdataTableProduct();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCateDeleteClick(MouseEvent mouseEvent) {
        Category c=tblCategory.getSelectionModel().getSelectedItem();
        if (AlertMessage.showAlertYesNo()){
        String sql="DELETE FROM category WHERE categoryID = '"+c.getCategoryID()+"'";
            try {
                ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                showDatatableCategory();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
