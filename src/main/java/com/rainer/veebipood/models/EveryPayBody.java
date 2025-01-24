package com.rainer.veebipood.models;

import lombok.Data;


@Data
public class EveryPayBody {
    public String account_name;
    public String nonce;
    public String timestamp;
    public double amount;
    public String order_reference;
    public String customer_url;
    public String api_username;
}
