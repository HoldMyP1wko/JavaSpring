package org.example;

public enum Role {
    ADMIN,
    USER;

    static public Role toEnum(String s){
        if (s.equals("ADMIN")){
            return ADMIN;
        } else {
            return USER;
        }
    }
}
