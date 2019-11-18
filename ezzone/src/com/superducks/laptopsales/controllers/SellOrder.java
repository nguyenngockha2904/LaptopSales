package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class SellOrder {
    private static int billID;
    public TableView<Bill> tblBill;
    public TableColumn clmProducts;
    public TableColumn clmAmount;
    public TableColumn clmPrice;
    public Label lblBuyer;
    public Label lblPhone;
    public Label lblTotalePrice;
    public Label lblBillNumber;
    public Label lblCreatedBy;
    static Stage mainStage =  new Stage();
    ObservableList<Bill> dataTable = FXCollections.observableArrayList();

    public void initialize () {
        showTable();
        setUP();
    }

    public static void showForm(int billID) {
        SellOrder.billID = billID;
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(LoginForm.class.getClassLoader().getResource("com/superducks/laptopsales/fxmls/SellOrder.fxml")));

            mainStage.setTitle("Hoá Đơn Bán Hàng");
            mainStage.setScene(new Scene(root));
            Image icon = new Image("/com/superducks/laptopsales/icons/main_icons/bill.png");
            mainStage.getIcons().add(icon);
            mainStage.setResizable(false);
            mainStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUP() {
        lblBillNumber.setText("Số: "+billID);
        String sql = "select * from bill where id="+billID;
        String userID = "", username = "";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            if(rs.next()) {
                lblBuyer.setText("Người mua hàng: "+ rs.getString("customer_name"));
                lblPhone.setText("Số điện thoại: 0"+ rs.getString("customer_phone"));
                lblTotalePrice.setText("Tổng tiền: "+ getFormattedAmount(rs.getInt("total")) +" VND");
                userID = rs.getString("user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlUser = "select * from accounts where id='"+userID+"';";
        try {
            ResultSet rst = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlUser);
            if(rst.next()) {
                if (!rst.getString("fullname").equals(""))
                    username = rst.getString("fullname");
                else
                    username = rst.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblCreatedBy.setText("Người tạo hoá đơn: " +username);
    }

    private void showTable() {
        String sql = "select * from bill_info where bill_id="+billID;
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            String products = "", price = "";
            int amount = 0;
            while (rs.next()) {
                amount = rs.getInt("amount");
                price = getFormattedAmount(rs.getInt("total"));
                int product_id = rs.getInt("product_id");
                String sqlPName = "select * from products where id="+product_id;
                ResultSet rst = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlPName);
                if(rst.next())
                    products = rst.getString("name");
                dataTable.addAll(new Bill(products, amount, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clmProducts.setCellValueFactory(new PropertyValueFactory<Sales.ClassSales, String>("Products"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<Sales.ClassSales, Integer>("Amount"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<Sales.ClassSales, Integer>("Price"));
        tblBill.setItems(dataTable);
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
        if(isNavigate){
            formatted_value.insert(0, String.format(Locale.getDefault(), "-%,d", amount));
        } else {
            formatted_value.insert(0, String.format(Locale.getDefault(), "%,d", amount));
        }
        return String.format(Locale.getDefault(), "%s", formatted_value.toString());
    }

    public static class Bill {
        private final SimpleStringProperty Products;
        private final SimpleIntegerProperty Amount;
        private final SimpleStringProperty Price;

        private Bill(String Products, Integer Amount, String Price) {
            this.Products = new SimpleStringProperty(Products);
            this.Amount = new SimpleIntegerProperty(Amount);
            this.Price = new SimpleStringProperty(Price);
        }

        public String getProducts() {
            return Products.get();
        }

        public SimpleStringProperty productsProperty() {
            return Products;
        }

        public void setProducts(String products) {
            this.Products.set(products);
        }

        public int getAmount() {
            return Amount.get();
        }

        public SimpleIntegerProperty amountProperty() {
            return Amount;
        }

        public void setAmount(int amount) {
            this.Amount.set(amount);
        }

        public String getPrice() {
            return Price.get();
        }

        public SimpleStringProperty priceProperty() {
            return Price;
        }

        public void setPrice(String price) {
            this.Price.set(price);
        }
    }
}
