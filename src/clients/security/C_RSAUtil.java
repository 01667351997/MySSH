package clients.security;

import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author binhminh
 */
public class C_RSAUtil {
    public byte[] encryptData(String data, PublicKey pubkey) throws Exception{
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            encryptedData = cipher.doFinal(dataToEncrypt);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return encryptedData;
    }
}
