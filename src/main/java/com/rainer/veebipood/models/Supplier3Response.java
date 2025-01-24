package com.rainer.veebipood.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3Response {
    public ArrayList<Supplier3Product> products;
    public int total;
    public int skip;
    public int limit;
}
