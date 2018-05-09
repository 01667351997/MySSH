package server.authentication;

import java.util.ArrayList;

/**
 *
 * @author binhminh
 */
public class Authentication {
    
    public String[] splitAccount(String account){
        String[] list = account.split(" ");
        return list;
    }
    public boolean isValid(ArrayList accounts,String userName,String password) {
        for (int i = 0; i < accounts.size(); i++) {
            String[] s = accounts.get(i).toString().split(" ");
            if (s[0].trim().equals(userName)) {
                if (s[1].trim().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
