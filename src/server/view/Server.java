package server.view;

import java.util.Scanner;
import server.trans.SetUpNetWorking;

/**
 *
 * @author binhminh
 */
public class Server {
    
    public void go(){
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
        server.go();
    }
}
