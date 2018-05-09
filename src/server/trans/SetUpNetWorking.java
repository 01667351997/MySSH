package server.trans;

import java.io.*;
import java.net.*;
import server.conn.ClientHandler;
import server.infomation.Clients;

/**
 *
 * @author binhminh
 */
public class SetUpNetWorking {

    private static final int PORT = 5000;

    public void connectNetWorking(int maxCL) {
        
        System.out.println("Networking is setup done!");
        
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            
            // Lop thao tac voi danh sach cac client
            Clients clients = new Clients(maxCL);
            
            // true: Da thong bao max client connect
            boolean mess = false;
            while (true) {
                if(!clients.isOverhead()){
                    
                    Socket clientSock = serverSock.accept();
                    Thread t = new Thread(new ClientHandler(clientSock,clients));
                    t.start();
                    
                    System.out.println("Number of clients: "+(clients.getClientConnect()+1));
                    mess=false;
                }else{
                    if(!mess){
                        System.out.println("Max "+(clients.getClientConnect()+1) +" clients connect");
                        mess = true;
                    }
                    Thread.sleep(1000);
                }
                

            }
        } catch(InterruptedException e){
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
