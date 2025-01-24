package com.rainer.veebipood.exception;

import lombok.Data;

import java.util.Date;

@Data // getter, setter, noargs constructor
public class ErrorMessage {
    private String message;
    private int statusCode;
    private Date date;
}
