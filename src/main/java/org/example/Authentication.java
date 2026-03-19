package org.example;


import org.apache.commons.codec.digest.Crypt;

public class Authentication {


    static public boolean passedAuth(UserRepository user, String password){
        return Crypt.crypt(password).equals(password);
    }


}
