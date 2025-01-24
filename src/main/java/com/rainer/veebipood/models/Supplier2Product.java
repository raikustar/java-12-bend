package com.rainer.veebipood.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Supplier2Product {
    public int id;
    public String title;
    public int price;
    public String description;
    public ArrayList<String> images;
    public Date creationAt;
    public Date updatedAt;
    public Supplier2Category category;

}
