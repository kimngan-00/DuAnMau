package com.example.duanmau.Model;

public class HoaDon {
String id, ngayXuat, nguoiXuat;
long date,tongTien;

    public HoaDon() {
    }

    public String getNguoiXuat() {
        return nguoiXuat;
    }

    public void setNguoiXuat(String nguoiXuat) {
        this.nguoiXuat = nguoiXuat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }
}
