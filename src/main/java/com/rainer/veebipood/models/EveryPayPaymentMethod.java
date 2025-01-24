package com.rainer.veebipood.models;

import lombok.Data;

@Data
public class EveryPayPaymentMethod {
    public String source;
    public String display_name;
    public String country_code;
    public String payment_link;
    public String logo_url;
    public Object applepay_available;
    public boolean googlepay_available;
    public Object wallet_display_name;
    public boolean available;
}
