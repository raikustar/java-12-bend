package com.rainer.veebipood.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthToken {
    private String token;
    private Date expiration;
    private boolean admin;
}
