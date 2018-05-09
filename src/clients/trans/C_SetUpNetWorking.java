package clients.trans;

import java.io.*;
import java.net.*;

/**
 *
 * @author binhminh
 */
public class C_SetUpNetWorking {

    private Socket socketOfClient;
    
    
    public Socket getSocketOfClient() {
        return this.socketOfClient;
    }


    public boolean connectNetworking(String address) {
        try {
            this.socketOfClient = new Socket(address, 5000);
            socketOfClient.setSoTimeout(300*1000);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return true;
    }
    

}
