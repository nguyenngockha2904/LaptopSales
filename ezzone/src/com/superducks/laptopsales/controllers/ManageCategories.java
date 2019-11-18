package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.Categories;
import com.superducks.laptopsales.Class.ConnectDatabase;
import com.superducks.laptopsales.Class.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class ManageCategories {

    public TableView<Products> tblProducts;
    public TableColumn clmCategoryID;
    public ImageView btnAdd;
    public TableColumn clmID;
    public TableColumn clmName;
    public TableColumn clmPrice;
    public AnchorPane ap;
    public ImageView btnEdit;
    public ImageView btnDelete;
    public TableColumn clmCateID;
    public TableColumn clmCategoryName;
    public TableView<Categories> tblCategory;
    public ImageView btnCatedelete;
    public ImageView btnCateEdit;
    public ImageView btnAddCategory;
    private static Stage mainStage = new Stage();

    public ManageCategories() {
    }

    public void initialize() {
        showDatatableCategory();
        showdataTableProduct();
    }

    public void showDatatableCategory() {
        String sql = "select *from `categories`";
        ObservableList<Categories> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                Categories c = new Categories(rs.getString(1), rs.getString(2));
                data.add(c);
            }
            clmCateID.setCellValueFactory(new PropertyValueFactory<Categories, String>("CategoryID"));
            clmCategoryName.setCellValueFactory(new PropertyValueFactory<Categories, String>("CategoryName"));
            tblCategory.getItems().clear();
            tblCategory.setItems(data);
            tblCategory.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showdataTableProduct() {
        Categories c = tblCategory.getSelectionModel().getSelectedItem();
        String sql = "SELECT * FROM products where category_id='" + c.getCategoryID() + "'";
        ObservableList<Products> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while (rs.next()) {
                Products p = new Products(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        getFormattedAmount(rs.getInt(7)));
                data.add(p);

            }
            clmID.setCellValueFactory(new PropertyValueFactory<Products, String>("productId"));
            clmCategoryID.setCellValueFactory(new PropertyValueFactory<Products, String>("categoryId"));
            clmName.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<Products, String>("price"));
            clmCategoryID.setCellFactory(TextFieldTableCell.<Products>forTableColumn());
            tblProducts.setItems(data);
            tblProducts.getSelectionModel().selectFirst();
            int row = tblProducts.getItems().size();
            if (row > 0) {
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
            } else {
                btnEdit.setVisible(false);
                btnDelete.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void showForm() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/ManageCategories.fxml")));
            mainStage.setTitle("Manage Categories");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/categories.png");
            mainStage.getIcons().add(icon);
            mainStage.setResizable(false);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnAddclick(MouseEvent mouseEvent) {
        Categories c = tblCategory.getSelectionModel().getSelectedItem();
        AddProducts.category = c.getCategoryID();
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/AddProducts.fxml")));
            AddProducts.mainStage.setTitle("Add Products");
            AddProducts.mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/plus.png");
            AddProducts.mainStage.getIcons().add(icon);
            AddProducts.mainStage.setResizable(false);
            AddProducts.mainStage.showAndWait();
            if (AddProducts.changed) {
                AddProducts.changed = false;
                showDatatableCategory();
                showdataTableProduct();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnEditClick(MouseEvent mouseEvent) {

        Products p = tblProducts.getSelectionModel().getSelectedItem();
        EditProducts.ProductID = p.getProductId();
        EditProducts.CategoryID = p.getCategoryId();
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
            if (EditProducts.changed) {
                EditProducts.changed = false;
                showDatatableCategory();
                showdataTableProduct();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseclick(MouseEvent mouseEvent) {
        btnEdit.setVisible(true);
        btnDelete.setVisible(true);
    }

    public void btnDeleteClick(MouseEvent mouseEvent) {
        Products p = tblProducts.getSelectionModel().getSelectedItem();
        if (AlertMessage.showAlertYesNo()) {
            String sql = "DELETE FROM products WHERE id =" + p.getProductId();
            try {
                ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                showdataTableProduct();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("no");
        }
    }

    public void btnAddCategory(MouseEvent mouseEvent) {
        EditCategories.chage = 0;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditCategories.fxml")));
            EditCategories.mainStage = new Stage();
            EditCategories.mainStage.setTitle("Add Categories");
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/plus.png");
            EditCategories.mainStage.getIcons().add(icon);
            EditCategories.mainStage.setScene(new Scene(root));
            EditCategories.mainStage.setResizable(false);
            EditCategories.mainStage.showAndWait();
            if (EditCategories.changed) {
                showDatatableCategory();
                showdataTableProduct();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFormattedAmount(int amount) {
        StringBuilder formatted_value = new StringBuilder();
        boolean isNavigate = amount < 0;
        amount = Math.abs(amount);
        while (amount > 999) {
            int du = amount % 1000;
            amount = amount / 1000;
            formatted_value.insert(0, String.format(Locale.getDefault(), ".%,03d", du));
        }
        if (isNavigate) {
            formatted_value.insert(0, String.format(Locale.getDefault(), "-%,d", amount));
        } else {
            formatted_value.insert(0, String.format(Locale.getDefault(), "%,d", amount));
        }
        return String.format(Locale.getDefault(), "%s", formatted_value.toString());
    }


    public void tblCategoryClicked(MouseEvent mouseEvent) {
        btnCateEdit.setVisible(true);
        btnCatedelete.setVisible(true);
        Categories c=tblCategory.getSelectionModel().getSelectedItem();
        String sql = "SELECT * FROM products where category_id='" + c.getCategoryID() + "'";
        ObservableList<Products> data = FXCollections.observableArrayList();
        try {
            ResultSet rs=ConnectDatabase.Connect().createStatement().executeQuery(sql);
            while(rs.next()) {
                Products p = new Products(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        getFormattedAmount(rs.getInt(7)));
                data.add(p);
            }
            clmID.setCellValueFactory(new PropertyValueFactory<Products,String>("productId"));
            clmCategoryID.setCellValueFactory(new PropertyValueFactory<Products,String>("categoryId"));
            clmName.setCellValueFactory(new PropertyValueFactory<Products,String>("name"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<Products,String>("price"));
            clmCategoryID.setCellFactory(TextFieldTableCell.<Products>forTableColumn());
            tblProducts.setItems(data);
            tblProducts.getSelectionModel().selectFirst();
            int row = tblProducts.getItems().size();
            if(row > 0) {
                btnEdit.setVisible(true);
                btnDelete.setVisible(true);
            } else {
                btnEdit.setVisible(false);
                btnDelete.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void btnCateEditClick(MouseEvent mouseEvent) {
        Categories c = tblCategory.getSelectionModel().getSelectedItem();
        EditCategories.categoryID = c.getCategoryID();
        EditCategories.categoryName = c.getCategoryName();
        EditCategories.chage = 1;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/EditCategories.fxml")));
            EditCategories.mainStage = new Stage();
            EditCategories.mainStage.setTitle("Edit Categories");
            Image icon = new Image("/com/superducks/laptopsales/icons/web_ui_color/compose.png");
            EditCategories.mainStage.getIcons().add(icon);
            EditCategories.mainStage.setScene(new Scene(root));
            EditCategories.mainStage.setResizable(false);
            EditCategories.mainStage.showAndWait();
            EditCategories.categoryID = "";
            EditCategories.categoryName = "";
            if(EditCategories.changed) {
                showDatatableCategory();
                showdataTableProduct();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCateDeleteClick(MouseEvent mouseEvent) {
        Categories c = tblCategory.getSelectionModel().getSelectedItem();
        if (AlertMessage.showAlertYesNo()){
        String sql="DELETE FROM categories WHERE id = '"+c.getCategoryID()+"'";
            try {
                ConnectDatabase.Connect().prepareStatement(sql).executeUpdate();
                showDatatableCategory();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
