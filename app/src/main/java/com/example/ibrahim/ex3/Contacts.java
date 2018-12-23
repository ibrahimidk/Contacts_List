package com.example.ibrahim.ex3;

public class Contacts {
    String name;
    String phone;
    int img;

    public Contacts(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }
}
