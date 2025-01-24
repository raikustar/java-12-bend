package com.rainer.veebipood.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier5Response {
    public String type;
    public Supplier5Metadata metadata;
    public ArrayList<Supplier5Feature> features;
    public ArrayList<Double> bbox;
}
