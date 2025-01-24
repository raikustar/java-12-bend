package com.rainer.veebipood.models;

import lombok.Data;

import java.util.Date;

@Data
public class EveryPayCheck {
    public String account_name;
    public String order_reference;
    public Object email;
    public String customer_ip;
    public String customer_url;
    public Date payment_created_at;
    public double initial_amount;
    public double standing_amount;
    public String payment_reference;
    public String payment_link;
    public String api_username;
    public Object processing_error;
    public Object warnings;
    public int stan;
    public int fraud_score;
    public String payment_state;
    public String payment_method;
    public Object ob_details;
    public Date transaction_time;
    public Object detailed_fraud_check_results;
}
