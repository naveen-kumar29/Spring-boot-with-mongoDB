package com.example.springbootwithjwttoken.util;


import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public  boolean stringValidation(String value){
        if(value!=null && !value.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
    public  boolean intValidation(int value){
        if(value!=0){
            return true;
        }else {
            return false;
        }
    }
}
