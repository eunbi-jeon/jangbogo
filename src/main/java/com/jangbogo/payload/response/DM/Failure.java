package com.jangbogo.payload.response.DM;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Failure implements Result {
    private String msg;
}