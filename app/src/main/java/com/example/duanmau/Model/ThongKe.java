package com.example.duanmau.Model;

public class ThongKe {
    private String idSach, ngayBan, tenSach, uriImgSach, tacGia;
    private long longDate;
    private int soLuongBanRa = 0;

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getUriImgSach() {
        return uriImgSach;
    }

    public void setUriImgSach(String uriImgSach) {
        this.uriImgSach = uriImgSach;
    }

    public ThongKe() {
    }

    public String getIdSach() {
        return idSach;
    }

    public void setIdSach(String idSach) {
        this.idSach = idSach;
    }

    public String getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(String ngayBan) {
        this.ngayBan = ngayBan;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public int getSoLuongBanRa() {
        return soLuongBanRa;
    }

    public void setSoLuongBanRa(int soLuongBanRa) {
        this.soLuongBanRa = soLuongBanRa;
    }
}
