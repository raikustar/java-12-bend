package com.rainer.veebipood.models;

import lombok.Data;

@Data
public class Supplier1Product {
    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String image;
    public Supplier1Rating rating;
}
