package com.superducks.laptopsales.controllers;

import com.superducks.laptopsales.Class.ConnectDatabase;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Statistics {
    static Stage mainStage = new Stage();
    public TableView tblAccounts;
    public TableView tblBills;
    public TableColumn clmUsername;
    public TableColumn clmFullname;
    public TableColumn clmBillID;
    public TableColumn clmBuyer;
    public TableColumn clmDateCreated;
    public TableColumn clmTotalPrice;
    public PieChart pieChartStatistics;
    public TableColumn clmUser;
    public ImageView btnNonView;
    public ImageView btnView;
    public BarChart barChartStatistics;
    public RadioButton radBetween;
    public DatePicker dtpTo;
    public DatePicker dtpFrom;
    public RadioButton radDay;
    public RadioButton radMonth;
    public RadioButton radYear;
    public RadioButton radallUser;
    private ObservableList<Accounts> dataAccounts = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
    private ObservableList<Bills> dataBills = FXCollections.observableArrayList();

    public void initialize() {
        setDatetimePickerAndRadiobutton();
        showTableAccounts();
        showTableBills();
        check();
        pieChartData.clear();
        Accounts accounts=(Accounts) tblAccounts.getSelectionModel().getSelectedItem();
        pieChartStatistics.setTitle("Quantity of products sold of "+accounts.getFullname());
        showPieChartWithUser(accounts.getId());
        clearBarChart();
        showBarChart();
    }

    private void setDatetimePickerAndRadiobutton(){
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dtpFrom.setConverter(converter);
        dtpTo.setConverter(converter);
        dtpFrom.setValue(LocalDate.now());
        dtpTo.setValue(LocalDate.now());
        radDay.setSelected(true);
        ToggleGroup group = new ToggleGroup();
        radDay.setToggleGroup(group);
        radYear.setToggleGroup(group);
        radMonth.setToggleGroup(group);
        radBetween.setToggleGroup(group);
    }

    //SHOW TABLE ACCOUNT
    private void showTableAccounts() {
        String sql = "select * from accounts";
        try {
            ResultSet rs = Objects.requireNonNull(ConnectDatabase.Connect()).createStatement().executeQuery(sql);
            while (rs.next()) {
                dataAccounts.add(new Accounts(rs.getInt("id"),rs.getString("username"), rs.getString("fullname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clmUsername.setCellValueFactory(new PropertyValueFactory<Accounts, String>("username"));
        clmFullname.setCellValueFactory(new PropertyValueFactory<Accounts, String>("fullname"));
        tblAccounts.setItems(dataAccounts);
        tblAccounts.getSelectionModel().selectFirst();
        check();
    }

    //SHOW TABLE BILLS
    private void showTableBills() {
        dataBills.clear();
        Accounts db = (Accounts) tblAccounts.getSelectionModel().getSelectedItem();
        int billID = 0;
        String user = "", buyer = "", datetime = "", totalPrice = "";
        String sql = "call showBillwithUser('" + db.getId() + "')";
        CallableStatement cs = null;
        try {
            cs = ConnectDatabase.Connect().prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                billID = rs.getInt("id");
                user = rs.getString("fullname");
                buyer = rs.getString("customer_name");
                datetime = rs.getString("date");
                totalPrice = getFormattedAmount(rs.getInt("total"));
                dataBills.add(new Bills(billID, user, buyer, datetime, totalPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tblBills.getSelectionModel().selectFirst();
    }

    //SHOW BAR CHART
    private void showBarChart() {
        Bills bl;
        bl = (Bills) tblBills.getSelectionModel().getSelectedItem();
        String sql="call showBarChart("+bl.getBillID()+")";
        barChartStatistics.setTitle("Quantity of products sold from bill "+bl.getBillID());
        CallableStatement cs = null;
        XYChart.Series dataSeries = new XYChart.Series();
        try {
            cs = Objects.requireNonNull(ConnectDatabase.Connect()).prepareCall(sql);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                dataSeries.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        barChartStatistics.getData().add(dataSeries);
    }

    private void clearBarChart() {
        barChartStatistics.getData().clear();
        barChartStatistics.setTitle("");
    }

    private void showPieChart() {
        String sql="call showPieChart()";
        PieChart(sql);
    }
    //SHOW PIE CHART
    private void showPieChartWithUser(int user){
        String sql="call showPieChartwithUser("+user+")";
        PieChart(sql);
    }
    public void PieChart(String sql){
        pieChartData.clear();
        try {
            CallableStatement cs = ConnectDatabase.Connect().prepareCall(sql);
            ResultSet rs=cs.executeQuery();
            while(rs.next()) {
                PieChart.Data slice1 = new PieChart.Data(rs.getString(1) +" ("+rs.getInt(2)+")", rs.getInt(2));
                pieChartData.add(slice1);
            }
            pieChartStatistics.getData().clear();
            pieChartStatistics.getData().addAll(pieChartData);
            pieChartStatistics.setLegendSide(Side.BOTTOM);
            pieChartStatistics.setClockwise(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clmBillID.setCellValueFactory(new PropertyValueFactory<Bills, Integer>("billID"));
        clmUser.setCellValueFactory(new PropertyValueFactory<Bills, String>("user"));
        clmBuyer.setCellValueFactory(new PropertyValueFactory<Bills, String>("buyer"));
        clmDateCreated.setCellValueFactory(new PropertyValueFactory<Bills, String>("datetime"));
        clmTotalPrice.setCellValueFactory(new PropertyValueFactory<Bills, String>("totalPrice"));
        tblBills.setItems(dataBills);
        tblBills.getSelectionModel().selectFirst();
        check();
    }

    public void clearPieChart() {
        pieChartStatistics.setTitle("");
        pieChartStatistics.getData().clear();
    }

    public void showbillWithExcute(String sql){
        dataBills.clear();
        int billID = 0;
        String user = "", buyer = "", datetime = "", totalPrice = "";
        CallableStatement cs = null;
        try {
            cs = ConnectDatabase.Connect().prepareCall(sql);
            ResultSet rs=cs.executeQuery();
            if (rs.next()) {
                while (rs.next()) {
                    billID = rs.getInt("id");
                    user = rs.getString("fullname");
                    buyer = rs.getString("customer_name");
                    datetime = rs.getString("date");
                    totalPrice = getFormattedAmount(rs.getInt("total"));
                    dataBills.add(new Bills(billID, user, buyer, datetime, totalPrice));
                }
            }else{
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clmBillID.setCellValueFactory(new PropertyValueFactory<Bills, Integer>("billID"));
        clmUser.setCellValueFactory(new PropertyValueFactory<Bills, String>("user"));
        clmBuyer.setCellValueFactory(new PropertyValueFactory<Bills, String>("buyer"));
        clmDateCreated.setCellValueFactory(new PropertyValueFactory<Bills, String>("datetime"));
        clmTotalPrice.setCellValueFactory(new PropertyValueFactory<Bills, String>("totalPrice"));
        tblBills.setItems(dataBills);
        tblBills.getSelectionModel().selectFirst();
        check();
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

    public void btnView_Clicked(MouseEvent mouseEvent) {
        Bills bl = (Bills) tblBills.getSelectionModel().getSelectedItem();
        SellOrder.showForm(bl.getBillID());
    }

    public void tblAccounts_Clicked(MouseEvent mouseEvent) {
        tblAccount_action();
    }

    private void tblAccount_action() {
        showTableBills();
        Accounts db = (Accounts) tblAccounts.getSelectionModel().getSelectedItem();
        pieChartStatistics.setTitle("Quantity of products sold of "+db.getFullname());
        showPieChartWithUser(db.getId());
        Bills bl = (Bills) tblBills.getSelectionModel().getSelectedItem();
        clearBarChart();
        showBarChart();
    }

    private void check() {
        if(tblBills.getItems().size()>0) {
            btnView.setVisible(true);
            btnNonView.setVisible(false);
        }
        else {
            btnView.setVisible(false);
            btnNonView.setVisible(true);
        }
    }

    public void tblBills_Clicked(MouseEvent mouseEvent) {
        Bills bl = (Bills) tblBills.getSelectionModel().getSelectedItem();
        clearBarChart();
        showBarChart();
    }

    public void radBetweenClicked(MouseEvent mouseEvent) {
        if (radBetween.isSelected()) {
            dtpTo.setDisable(false);
        }else{
            dtpTo.setDisable(true);
        }
        clearBarChart();
        showBarChart();
    }

    //DATETIME PICKER
    public void dtpFromSearch(ActionEvent actionEvent) {
        clearPieChart();
        showDataDtpFromSearch();
    }

    private void showDataDtpFromSearch() {
        Accounts db = (Accounts) tblAccounts.getSelectionModel().getSelectedItem();
        if(radallUser.isSelected()) {
            pieChartStatistics.setTitle("Quantity of products sold");
            showPieChart();
        }
        else {
            pieChartStatistics.setTitle("Quantity of products sold of " + db.getFullname());
            showPieChartWithUser(db.getId());
        }
        String query="";
        if (radDay.isSelected()){
            if(radallUser.isSelected()){
                query="call showBillwithDate('"+dtpFrom.getEditor().getText()+"')";
            }else{
                query="call showBillwithUserDate('"+db.getId()+"','"+dtpFrom.getEditor().getText()+"')";
            }
            showbillWithExcute(query);
        }else if (radMonth.isSelected()){
            if(radallUser.isSelected()){
                query="call showBillwithMonth('"+dtpFrom.getEditor().getText()+"')";
            }else{
                query="call showBillwithUserMonth('"+db.getId()+"','"+dtpFrom.getEditor().getText()+"')";
            }
            showbillWithExcute(query);
        }else if (radYear.isSelected()){
            if(radallUser.isSelected()){
                query="call showBillwithYear('"+dtpFrom.getEditor().getText()+"')";
            }else{
                query="call showBillwithUserYear('"+db.getId()+"','"+dtpFrom.getEditor().getText()+"')";
            }
            showbillWithExcute(query);
        }
        clearBarChart();
        showBarChart();
    }

    //RADIO ALL USERS
    public void radAllUserClicked(MouseEvent mouseEvent) {
        String sql="select bill.id,accounts.fullname,bill.customer_name,bill.date,bill.total \n" +
                "from accounts\n" +
                " INNER JOIN bill ON accounts.id=bill.user";
        if (radallUser.isSelected()){
            String query="call showPieChart()";
            PieChart(query);
            tblAccounts.setDisable(true);
            showbillWithExcute(sql);
        }else {
            Accounts a=(Accounts)tblAccounts.getSelectionModel().getSelectedItem();
            showPieChartWithUser(a.getId());
            tblAccounts.setDisable(false);
            showTableBills();
        }
        clearBarChart();
        showBarChart();
        showDataDtpFromSearch();
    }

    public static class Accounts {
        private int id;
        private final SimpleStringProperty username;
        private final SimpleStringProperty fullname;

        public Accounts(int id, String username, String fullname) {
            this.id = id;
            this.username = new SimpleStringProperty(username);
            this.fullname = new SimpleStringProperty(fullname);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private Accounts(String uName, String fName) {
            this.username = new SimpleStringProperty(uName);
            this.fullname = new SimpleStringProperty(fName);
        }

        public String getUsername() {
            return username.get();
        }

        public void setUsername(String fName) {
            username.set(fName);
        }

        public String getFullname() {
            return fullname.get();
        }

        public void setFullname(String fName) {
            fullname.set(fName);
        }

    }

    public static class Bills {

        private final  SimpleIntegerProperty billID;
        private final SimpleStringProperty user;
        private final SimpleStringProperty buyer;
        private final SimpleStringProperty datetime;
        private final SimpleStringProperty totalPrice;

        public int getBillID() {
            return billID.get();
        }

        public SimpleIntegerProperty billIDProperty() {
            return billID;
        }

        public void setBillID(int billID) {
            this.billID.set(billID);
        }

        public String getUser() {
            return user.get();
        }

        public SimpleStringProperty userProperty() {
            return user;
        }

        public void setUser(String user) {
            this.user.set(user);
        }

        public String getBuyer() {
            return buyer.get();
        }

        public SimpleStringProperty buyerProperty() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer.set(buyer);
        }

        public String getDatetime() {
            return datetime.get();
        }

        public SimpleStringProperty datetimeProperty() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime.set(datetime);
        }

        public String getTotalPrice() {
            return totalPrice.get();
        }

        public SimpleStringProperty totalPriceProperty() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice.set(totalPrice);
        }

        public Bills(int billID, String user, String buyer, String datetime, String totalPrice) {
            this.billID = new SimpleIntegerProperty(billID);
            this.user = new SimpleStringProperty(user) ;
            this.buyer = new SimpleStringProperty(buyer);
            this.datetime = new SimpleStringProperty(datetime);
            this.totalPrice = new SimpleStringProperty(totalPrice);
        }
    }
}
