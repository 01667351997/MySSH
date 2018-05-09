
package server.authentication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author binhminh
 */
public class RSAUtil {
    private ArrayList Accounts;
    
    // Read file account.txt to check
    public ArrayList readAccounts() {
        File f = new File("server_repository/accounts.txt");
        Accounts = new ArrayList();
        try {
            FileReader fr = new FileReader(f);

            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                this.Accounts.add(line);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return Accounts;
    }
    public String decryptData(byte[] data, PrivateKey prakey) throws IOException{
        byte[] decryptData = null;
        data = Base64.getDecoder().decode(data);
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, prakey);
            decryptData = cipher.doFinal(data);
            
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return new String(decryptData);
    }
}
