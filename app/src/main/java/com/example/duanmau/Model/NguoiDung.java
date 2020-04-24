package com.example.duanmau.Model;

public class NguoiDung {
    String hoTen,email,tenDangNhap, matKhau, sdt;

    public NguoiDung() {
    }

    public NguoiDung(String hoTen, String email, String tenDangNhap, String matKhau, String sdt) {
        this.hoTen = hoTen;
        this.email = email;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
