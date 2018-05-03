package clients.model;

import clients.security.C_RSAUtil;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Base64;

/**
 *
 * @author binhminh
 */
public class C_ProcessCommand implements C_IProcessCommand{

    C_ClientSession clientss;

    public C_ProcessCommand(C_ClientSession clientss) {
        this.clientss = clientss;
    }

    public boolean login(String user, String pass) {
        
        try {
            // create account follow type literal string
            String account = user + " " + pass;

            clientss.getWriter().writeUTF("login");
            clientss.getWriter().flush();

            PublicKey publicKey;
            C_RSAUtil rsaUtil = new C_RSAUtil();
            while (true) {
                try {
                    if ((publicKey = (PublicKey) clientss.getReader().readObject()) != null) {
                        break;
                    }
                } catch (ClassNotFoundException ex) {
                    System.err.println(ex.getMessage());
                }
            }

            byte[] byteEncrypted = rsaUtil.encryptData(account, publicKey);
            String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);

            clientss.getWriter().writeUTF(encrypted);
            clientss.getWriter().flush();
            
            
            // Read value to check is login success 
            
            Boolean response = (Boolean) clientss.getReader().readObject();
            
            return response.booleanValue();
            
                
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean pwd() {
        try {
            clientss.getWriter().writeUTF("pwd");
            clientss.getWriter().flush();
            
            // keep read untill get message
            String path;
            path = clientss.getReader().readObject().toString();
            
            System.out.println(path);
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean ls() {
        try {
            clientss.getWriter().writeUTF("ls");
            clientss.getWriter().flush();
            
            // keep read untill get message
            String path;
            path = clientss.getReader().readObject().toString();
            
            System.out.println(path);
        } catch ( IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean date() {
        try {
            clientss.getWriter().writeUTF("date");
            clientss.getWriter().flush();
            
            // keep read untill get message
            String date;
            date = clientss.getReader().readObject().toString();
            
            System.out.println(date);
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean rm(String dest) {
        Boolean flag;
        try {
            clientss.getWriter().writeUTF("rm");
            clientss.getWriter().flush();
            
            clientss.getWriter().writeObject(dest);
            clientss.getWriter().flush();
            
            flag = (Boolean) clientss.getReader().readObject();
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return flag.booleanValue();
    }

    @Override
    public boolean mv(String src,String dest) {
        Boolean flag;
        try {
            clientss.getWriter().writeUTF("mv");
            clientss.getWriter().flush();
            
            clientss.getWriter().writeObject(src);
            clientss.getWriter().flush();
            
            clientss.getWriter().writeObject(dest);
            clientss.getWriter().flush();
            
            flag = (Boolean) clientss.getReader().readObject();
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return flag.booleanValue();
    }

    @Override
    public boolean cd(String dest) {
        Boolean flag;
        try {
            // thuc hien lenh cd
            clientss.getWriter().writeUTF("cd");
            clientss.getWriter().flush();
            
            // Send destination 
            clientss.getWriter().writeObject(dest);
            clientss.getWriter().flush();
            
            // keep read untill get message
            
            flag = (Boolean) clientss.getReader().readObject();
            
            
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return flag.booleanValue();
    }
    
    

}
