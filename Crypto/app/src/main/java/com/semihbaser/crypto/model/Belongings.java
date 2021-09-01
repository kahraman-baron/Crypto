package com.semihbaser.crypto.model;

import java.io.Serializable;

public class Belongings implements Serializable {

    public String email;
    public String name;
    public String price;
    public String downloadUrl;
    public String info;

    public Belongings(String email, String name, String price, String downloadUrl, String info) {
        this.email = email;
        this.name = name;
        this.price = price;
        this.downloadUrl = downloadUrl;
        this.info = info;
    }
}
