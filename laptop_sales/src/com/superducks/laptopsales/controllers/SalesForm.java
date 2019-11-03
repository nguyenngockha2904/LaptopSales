package com.superducks.laptopsales.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SalesForm {
    public TextField txtMsHD;
    public ComboBox<String> cbKhachHang;
    public GridPane gridPane;
    public ScrollPane scrollPane;
    ComboBox<String> cboMatHang;
    public TextField txtTenKhachHang;
    public TextField txtDiaChi;
    public TextField txtDienThoai;
    public TextField txtTongGiaTri;
    int nRowsCT = 0;
    List<TextField> txtSTTList = new ArrayList(),
            txtMSMatHangList = new ArrayList(),
            txtTenMatHangList = new ArrayList(),
            txtDVTlist = new ArrayList(),
            txtSoLuongList = new ArrayList(),
            txtDonGiaList = new ArrayList(),
            txtTriGiaList = new ArrayList();
    String MSKH_Luu = "";
    String MSHH_Luu="";
    public Button btnNew;
    public Button btnAddCT;
    public Button btnSave;
    public Button btnClose;
    public void  cboKhacHang_Click(){
        MSKH_Luu=cbKhachHang.getSelectionModel().getSelectedItem().toString();

    }

}