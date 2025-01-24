package com.rainer.veebipood.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier4Response {
    public int total_count;
    public ArrayList<Supplier4Product> results;
}
