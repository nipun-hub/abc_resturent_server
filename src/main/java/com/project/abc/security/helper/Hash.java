package com.project.abc.security.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hash {
    public static String make(String text){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(text);
    }

    public static boolean match(String text,String hash){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(text,hash);
    }
}
