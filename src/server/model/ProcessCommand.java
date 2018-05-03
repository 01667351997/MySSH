package server.model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import server.authentication.GenerateKeys;
import server.authentication.RSAPairKeys;
import server.infomation.FileExe;
import server.security.RSAUtil;

/**
 *
 * @author binhminh
 */
public class ProcessCommand implements IProcessCommand{
    private final FileExe nDirec;
    private final ClientSession client;
    public ProcessCommand(ClientSession client,FileExe nDirec){
        this.client = client;
        this.nDirec = nDirec;
    }

    /**
     *
     * @return
     * @throws IOException
     */
    @Override
    public boolean login() throws IOException {
        Authentication auth = new Authentication();
        RSAUtil rsaUtil = new RSAUtil();
        try {
            // Tao khoa de gui cho client
            new GenerateKeys(1024).generateKeysToFile();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.err.println(e.getMessage());
        }
        String data = "";
        try {

            // Create pair keys and send public keys
            RSAPairKeys key = new RSAPairKeys();
            client.getWriter().writeObject(key.getPublicKey());
            client.getWriter().flush();

            // read encrypt account of user
            String encrypt = client.getReader().readUTF();

            // After receive we have 172 byte encrypt but RSA just process 128 bytes
            byte[] byteEncrypted = encrypt.getBytes();

            // decypt data
            data = rsaUtil.decryptData(byteEncrypted, key.getPrivateKey());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String[] acc = auth.splitAccount(data);
        if (auth.isValid(rsaUtil.readAccounts(), acc[0], acc[1])) {
            client.getWriter().writeObject(new Boolean(true));
            client.getWriter().flush();
            
            File file = new File("/home/binhminh/NetBeansProjects/MySSH/server_repository/home/"+acc[0]);
            file.mkdirs();
            // session of user determine by userName
            client.setUserName(acc[0]);
            
            nDirec.setCurrent_directory(acc[0]);
            
            return true;
        } else {
            client.getWriter().writeObject(new Boolean(false));
            client.getWriter().flush();
            return false;

        }
    }
    

    @Override
    public boolean pwd() {
        try {     
            client.getWriter().writeObject(nDirec.getRoot_directory()+nDirec.getCurrent_directory());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean ls() {
        File file = new File("/home/binhminh/NetBeansProjects/MySSH/server_repository"+nDirec.getRoot_directory()+nDirec.getCurrent_directory());
        File[] children = file.listFiles();
        
        // non-synchronized nhung hieu qua hon
        StringBuilder result = new StringBuilder();
        for (File chil :children) {
            if(chil.isDirectory()){
                result.append("d");
            }else{
                result.append("-");
            }
            if(chil.canRead()){
                result.append("r");
            }else{
                result.append("-");
            }
            if(chil.canWrite()){
                result.append("w");
            }else{
                result.append("-");
            }
            if(chil.canExecute()){
                result.append("x");
            }else{
                result.append("-");
            }
            
            long lastMofifyInMillis = chil.lastModified();
            Date lastModifyDate = new Date(lastMofifyInMillis);
            
            result.append(String.format("%10s %10s %10s\n",chil.length(), lastModifyDate,chil.getName()));
        }
        try {     
            client.getWriter().writeObject(result);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean date() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
        try {     
            client.getWriter().writeObject(dateFormat.format(date));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean rm() {
        try{
            String dest = client.getReader().readObject().toString();
            
            File currentDir = new File("/home/binhminh/NetBeansProjects/MySSH/server_repository"+nDirec.getRoot_directory()+nDirec.getCurrent_directory());
            File[] files = currentDir.listFiles();
            for(File file : files){
                if(!file.isDirectory()){
                    if(dest.trim().equals(file.getName())){
                        client.getWriter().writeObject(true);
                        return file.delete();
                        
                    }
                }
            }
            client.getWriter().writeObject(false);
        }catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean mv() {
        try{
            String src = client.getReader().readObject().toString();
            String dest = client.getReader().readObject().toString();
            String path = "/home/binhminh/NetBeansProjects/MySSH/server_repository"
                    +nDirec.getRoot_directory()+nDirec.getCurrent_directory();
            File oldFile = new File(path+"/"+ src);
            
            oldFile.renameTo(new File(path+"/"+dest+"/"+src));
            
            
            client.getWriter().writeObject(false);
        }catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        return false;
    }

    @Override
    public boolean cd() {
        try {
            String dest = client.getReader().readObject().toString();
            if(dest.equals("..")){
                if(nDirec.getRoot_directory().equals("/home/")){
                    client.getWriter().writeObject(false);
                    return false;
                }else{
                    String[] dirs = nDirec.getRoot_directory().split("/");
                    nDirec.setCurrent_directory(dirs[dirs.length-1]);
                    
                    // non-synchronized nhung hieu qua hon
                    StringBuilder rootPath = new StringBuilder();
                    for(int i=0; i<dirs.length-1; i++){
                        rootPath.append(dirs[i]+"/");
                    }
                    nDirec.setRoot_directory(rootPath.toString());
                    client.getWriter().writeObject(true);
                    return true;
                }
            }
            File currentDir = new File("/home/binhminh/NetBeansProjects/MySSH/server_repository"+nDirec.getRoot_directory()+nDirec.getCurrent_directory());
            File[] files = currentDir.listFiles();
            for(File file : files){
                if(file.isDirectory()){
                    if(dest.trim().equals(file.getName())){
                        nDirec.setRoot_directory(nDirec.getRoot_directory()+nDirec.getCurrent_directory()+"/");
                        nDirec.setCurrent_directory(dest);
                        client.getWriter().writeObject(true);
                        return true;
                    }
                }
            }
            client.getWriter().writeObject(false);
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
}
