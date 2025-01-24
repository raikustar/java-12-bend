package com.rainer.veebipood.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3Product {
    public int id;
    public String title;
    public String description;
    public String category;
    public double price;
    public double discountPercentage;
    public double rating;
    public int stock;
    public ArrayList<String> tags;
    public String brand;
    public String sku;
    public int weight;
    public Supplier3Dimensions dimensions;
    public String warrantyInformation;
    public String shippingInformation;
    public String availabilityStatus;
    public ArrayList<Supplier3Review> reviews;
    public String returnPolicy;
    public int minimumOrderQuantity;
    //public Meta meta;
    public ArrayList<String> images;
    public String thumbnail;
}
