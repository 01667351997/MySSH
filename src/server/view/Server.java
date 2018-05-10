package server.view;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.trans.SetUpNetWorking;

/**
 *
 * @author binhminh
 */
public class Server {
    
    public void go() throws IOException{
        System.out.println("SSH Server");
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter server'name: ");
        String Sname = input.nextLine();
        
        
        System.out.println("Enter max client: ");
        int maxCL = input.nextInt();
        
        SetUpNetWorking connect = new SetUpNetWorking();
        connect.connectNetWorking(maxCL);
    }
    
    
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.go();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
