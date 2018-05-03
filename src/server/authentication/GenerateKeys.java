
package server.authentication;


import java.io.*;
import java.security.*;
/**
 *
 * @author binhminh
 */
public class GenerateKeys {
    public static final String PUBLIC_KEY_FILE ="server_repository/rsa_keypair/publicKey";
    public static final String PRIVATE_KEY_FILE = "server_repository/rsa_keypair/privateKey";
    
    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    
    public GenerateKeys (int keylength) throws NoSuchAlgorithmException, NoSuchProviderException{
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    } 
    public void createKeys(){
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public void writeToFile(String path,byte[] key)throws IOException{
        File f = new File(path);
        f.getParentFile().mkdirs();
        
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }
    
    public void generateKeysToFile(){
        try{
            
            this.createKeys();
            this.writeToFile(PUBLIC_KEY_FILE, this.getPublicKey().getEncoded());
            this.writeToFile(PRIVATE_KEY_FILE, this.getPrivateKey().getEncoded());
            System.out.println("key Generated! ");
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}
