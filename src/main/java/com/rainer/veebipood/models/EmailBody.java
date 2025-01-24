package com.rainer.veebipood.models;

import lombok.Data;

@Data
public class EmailBody {
    private String to;
    private String subject;
    private String body;

}