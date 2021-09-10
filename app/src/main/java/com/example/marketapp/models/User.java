package com.example.marketapp.models;

public class User {
    private String taiKhoan;
    private String password;
    private String tenNguoiDung;

    public User() {
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    @Override
    public String toString() {
        return "User{" +
                "taiKhoan='" + taiKhoan + '\'' +
                ", password='" + password + '\'' +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                '}';
    }
}
