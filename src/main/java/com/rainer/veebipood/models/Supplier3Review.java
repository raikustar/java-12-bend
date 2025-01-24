package com.rainer.veebipood.models;

import lombok.Data;

import java.util.Date;

@Data
public class Supplier3Review {
    public int rating;
    public String comment;
    public Date date;
    public String reviewerName;
    public String reviewerEmail;
}
