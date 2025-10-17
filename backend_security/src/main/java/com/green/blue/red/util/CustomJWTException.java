package com.green.blue.red.util;

import lombok.RequiredArgsConstructor;


public class CustomJWTException extends RuntimeException{
    public CustomJWTException(String msg){
        super(msg);
    }
}
