package server.model;

/**
 *
 * @author binhminh
 */
public class ServerConfig {
    private int maxClient;
    private String host;
    private String password;
    
    public ServerConfig(){
        
    }
    
    public ServerConfig(String host,String password,int max){
        this.host = host;
        this.password = password;
        this.maxClient = max;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxClient() {
        return maxClient;
    }

    public void setMaxClient(int maxClient) {
        this.maxClient = maxClient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
