package com.rainer.veebipood.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogValues {
    private String request_name;
    private int response_duration;
    private int request_count;


    public int getResponseAverageDuration() {
        return response_duration / request_count;
    }
}
