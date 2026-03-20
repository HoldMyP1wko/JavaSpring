package org.example;


import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class Authentication {

    static UserRepository u = new UserRepository();

    static public boolean passedAuth(String login, String password){
        List<User> uUsersList = u.getUsers();

        for (User us : uUsersList){
            if (us.getLogin().equals(login)){
                String newHash = DigestUtils.sha256Hex(password);
                if (us.getPassword().equals(newHash)){
                    return true;
                }
            }
        }
        return false;
    }

}
