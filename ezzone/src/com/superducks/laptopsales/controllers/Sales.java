package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.AlertMessage;
import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Sales {
    static Stage salesStage = new Stage();
    public TableView<ClassSales> mainTable;
    public ComboBox<String> cbxCategories;
    public ComboBox<String> cbxProducts;
    public ImageView btnAdd;
    public TableColumn<ClassSales, String> clmType;
    public TableColumn<ClassSales, String> clmProducts;
    public TableColumn<ClassSales, Integer> clmAmount;
    public TableColumn<ClassSales, Integer> clmPrice;
    public TextField txtAmount;
    public ImageView btnRemove;
    public Label lblTotalePrice;
    public TextField txtCustomer;
    public TextField txtPhone;
    public Button btnConfirm;
    public Button btnClearAll;
    public ImageView btnNonAdd;
    public ImageView btnNonRemove;
    ObservableList<ClassSales> dataTable = FXCollections.observableArrayList();


    public void initialize() {
        getDataComboboxCategories();
        getDataComboboxProducts();
        check();
    }

    private void getDataComboboxCategories() {
        String sql = "select * from categories";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            while (rs.next()) {
                cbxCategories.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxCategories.getSelectionModel().selectFirst();
    }

    private void getDataComboboxProducts() {
        if(cbxProducts.getItems().size()>0)
            cbxProducts.getItems().clear();
        String category = cbxCategories.getValue().toString();
        String findCategogyID = "select * from categories where name ='"+category+"';";
        String categoryID = "";
        try {
            ResultSet rst = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(findCategogyID);
            while (rst.next()) {
                categoryID = rst.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from products where category_id ='"+categoryID+"';";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            while (rs.next()) {
                cbxProducts.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cbxProducts.getSelectionModel().selectFirst();
    }


    public void btnAdd_Click(MouseEvent mouseEvent) {
        if(!checkWarehouse()){
            addTableRow();
        } else {
            AlertMessage.showAlert("Not enough amount of products or products not available, please check again!", "error");
        }
    }

    private boolean checkWarehouse() {
        boolean check = true;
        int product_id = 0;
        String sql = "select * from products where name = '"+cbxProducts.getValue()+"';";
        try {
            ResultSet rs = ConnectDatabase.Connect().createStatement().executeQuery(sql);
            if(rs.next()) {
                product_id = rs.getInt("id");
            }
            String sqlFind = "select * from warehouse where product_id = " + product_id + ";";
            ResultSet rst = ConnectDatabase.Connect().createStatement().executeQuery(sqlFind);
            if(rst.next()) {
                if(rst.getInt("products_remaining")>0) {
                    check = Integer.parseInt(txtAmount.getText()) > rst.getInt("products_remaining");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    public void btnRemove_Click(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            removeTableRow();
        }
    }

    private void addTableRow() {
        String type, products;
        Integer amount, price = 0;
        type = cbxCategories.getValue().toString();
        products = cbxProducts.getValue().toString();
        amount = Integer.parseInt(txtAmount.getText());
        String sql = "select * from products where name = '" + products + "';";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            if(rs.next())
                price = Integer.parseInt(rs.getString("price"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //add Row to Table
        int row = mainTable.getItems().size();
        ClassSales cs;
        for(int i = 0 ; i < row ; i ++) {
            cs = dataTable.get(i);
            if(cs.getType().equals(cbxCategories.getValue().toString()) && cs.getProducts().equals(cbxProducts.getValue().toString())) {
                dataTable.remove(cs);
                break;
            }
        }
        dataTable.add(new ClassSales(type, products, amount, getFormattedAmount(amount * price)));
        clmType.setCellValueFactory(new PropertyValueFactory<ClassSales, String>("Type"));
        clmProducts.setCellValueFactory(new PropertyValueFactory<ClassSales, String>("Products"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<ClassSales, Integer>("Amount"));
        clmPrice.setCellValueFactory(new PropertyValueFactory<ClassSales, Integer>("Price"));
        mainTable.setItems(dataTable);
        mainTable.getSelectionModel().selectFirst();
        check();
        totalPrice();
    }

    private void removeTableRow() {
        ClassSales s = mainTable.getSelectionModel().getSelectedItem();
        s.getProducts();
        dataTable.remove(s);
        mainTable.setItems(dataTable);
        mainTable.getSelectionModel().selectFirst();
        check();
        totalPrice();
    }

    private void check() {
        checkAdd();
        checkRemove();
        checkConfirm();
    }

    private void tableRow_Clicked () {
        ClassSales s = mainTable.getSelectionModel().getSelectedItem();
        cbxCategories.setValue(s.getType());
        cbxProducts.setValue(s.getProducts());
        txtAmount.setText(""+s.getAmount());
    }

    private void totalPrice() {
        int totalPice = 0;
        int size = mainTable.getItems().size();
        for(int i = 0; i < size; i ++) {
            totalPice += getSplit(dataTable.get(i).getPrice());
        }
        lblTotalePrice.setText("Total Price: "+getFormattedAmount(totalPice)+" VND");
    }

    public void cbxValue_Changed(ActionEvent actionEvent) {
        check();
    }

    private void checkAdd() {
        if(isInteger(txtAmount.getText())) {

            if (Integer.parseInt(txtAmount.getText()) > 0 && Integer.parseInt(txtAmount.getText()) <= 100 && cbxProducts.getItems().size() > 0) {
                btnAdd.setVisible(true);
                btnNonAdd.setVisible(false);
            } else {
                btnAdd.setVisible(false);
                btnNonAdd.setVisible(true);
            }
        } else {
            btnAdd.setVisible(false);
            btnNonAdd.setVisible(true);
        }
    }

    private void checkConfirm() {
        String customerName = "^[a-zA-Z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ-]{2,50}$";
        String phone = "^[0-9_-]{10,11}$";
        if(mainTable.getItems().size() > 0 && txtCustomer.getText().matches(customerName) && txtPhone.getText().matches(phone)) {
            btnConfirm.setDisable(false);
        } else {
            btnConfirm.setDisable(true);
        }
    }

    private boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }


    public void cbxCategoriesValue_Changed(ActionEvent actionEvent) {
        if(cbxProducts.getItems().size() > 0)
            cbxProducts.getItems().clear();
        getDataComboboxProducts();
        check();
    }

    public void mainTable_MouseClicked(MouseEvent mouseEvent) {
        tableRow_Clicked();
        check();
    }

    private void checkRemove() {
        if(mainTable.getItems().size() > 0) {
            btnRemove.setVisible(true);
            btnNonRemove.setVisible(false);
        }
        else {
            btnRemove.setVisible(false);
            btnNonRemove.setVisible(true);
        }
    }

    private static Integer getSplit (String input) {
        StringBuilder output = new StringBuilder();
        if(input.contains(".")) {
            String [] inputSplit = input.split("\\.");
            for (String s : inputSplit) {
                output.append(s);
            }
            return Integer.parseInt(output.toString());
        }
        else
            return 0;
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

    public void txtAmount_textChanged(KeyEvent keyEvent) {
        check();
    }

    public void btnClear_Clicked(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            clearAll();
        }
    }
    private void clearAll() {
        txtCustomer.setText("");
        txtAmount.setText("1");
        txtPhone.setText("");
        lblTotalePrice.setText("Total Price: 0 VND");
        //cbxCategories.getSelectionModel().selectFirst();
        //getDataComboboxProducts();
        dataTable.clear();
        mainTable.getItems().clear();
        check();
    }

    public void textChanged(KeyEvent keyEvent) {
        check();
    }

    public void btnConfirm_Clicked(MouseEvent mouseEvent) {
        if(AlertMessage.showAlertYesNo()) {
            confirm();
            clearAll();
        }
    }
    private void confirm() {
        //Create new Bill
        int billID = 0;
        String createNewBill = "INSERT INTO `bill` (`user`, `customer_name`, `customer_phone`, `date`, `total`) VALUES (?, ?, ?, ?, ?);";
        Connection con = ConnectDatabase.Connect();
        int totalPice = 0;
        int size = mainTable.getItems().size();
        for(int i = 0; i < size; i ++) {
            totalPice += getSplit(dataTable.get(i).getPrice());
        }
        try {
            assert con != null;
            PreparedStatement ps = con.prepareStatement(createNewBill);
            ps.setString(1, MainForm.loggedID);
            ps.setString(2, txtCustomer.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, String.valueOf(LocalDateTime.now()));
            ps.setString(5, String.valueOf(totalPice));
            ps.executeUpdate();
            ResultSet rs = con.createStatement().executeQuery("select *from bill where id=(SELECT LAST_INSERT_ID());");
            if(rs.next()) {
                billID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Create Bill Info
        String product_name = "", total = "";
        int amount = 0, product_id = 0;
        int row = mainTable.getItems().size();
        for(int i = 0; i < row; i++) {
            product_name = dataTable.get(i).getProducts();
            total = dataTable.get(i).getPrice();
            amount = dataTable.get(i).getAmount();
            product_id = getProductID(product_name);
            String sql = "INSERT INTO `bill_info` (`bill_id`, `product_id`, `amount`, `total`) VALUES (?, ?, ?, ?);";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, String.valueOf(billID));
                ps.setString(2, String.valueOf(product_id));
                ps.setString(3, String.valueOf(amount));
                ps.setString(4, String.valueOf(getSplit(total)));
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sqlWh = "select * from warehouse where product_id=" + product_id + ";";
            try {
                ResultSet updateWh =  Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sqlWh);
                int remaining = 0, sold = 0;
                if(updateWh.next()) {
                    remaining = updateWh.getInt("products_remaining") - amount;
                    sold = updateWh.getInt("products_sold") + amount;
                }
                String upWh = "UPDATE `warehouse` SET `products_remaining` = ?, `products_sold` = ? WHERE (`product_id` = ?);";
                PreparedStatement pst = Objects.requireNonNull(ConnectDatabase.Connect()).prepareStatement(upWh);
                pst.setString(1, String.valueOf(remaining));
                pst.setString(2, String.valueOf(sold));
                pst.setString(3, String.valueOf(product_id));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        SellOrder.showForm(billID);
    }

    public Integer getProductID(String product_name) {
        int product_id = 0;
        String sql = "Select * from products where name='"+product_name+"';";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            if(rs.next()) {
                product_id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product_id;
    }

    public static class ClassSales {
        private final SimpleStringProperty Type;
        private final SimpleStringProperty Products;
        private final SimpleIntegerProperty Amount;
        private final SimpleStringProperty Price;

        private ClassSales(String Type, String Products, Integer Amount, String Price) {
            this.Type = new SimpleStringProperty(Type);
            this.Products = new SimpleStringProperty(Products);
            this.Amount = new SimpleIntegerProperty(Amount);
            this.Price = new SimpleStringProperty(Price);
        }

        public String getType() {
            return Type.get();
        }

        public SimpleStringProperty typeProperty() {
            return Type;
        }

        public void setType(String type) {
            this.Type.set(type);
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
