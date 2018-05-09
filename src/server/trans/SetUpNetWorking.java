package server.trans;

import java.io.*;
import static java.lang.Math.log;
import java.net.*;
import java.util.logging.Logger;
import server.conn.ClientHandler;
import server.infomation.Clients;

/**
 *
 * @author binhminh
 */
public class SetUpNetWorking {

    private static final int PORT = 5000;

    public void connectNetWorking(int maxCL) throws IOException {
        
        WriteLog filelog = new WriteLog();
        Logger log = filelog.writeLog();
        log.info("Networking is setup done!");
        
        try {
            ServerSocket serverSock = new ServerSocket(PORT);
            
            // Lop thao tac voi danh sach cac client
            Clients clients = new Clients(maxCL);
            
            // true: Da thong bao max client connect
            boolean mess = false;
            while (true) {
                if(!clients.isOverhead()){
                    
                    Socket clientSock = serverSock.accept();
                    Thread t = new Thread(new ClientHandler(clientSock,clients,log));
                    t.start();
                    
                    
                    log.info("Number of clients: "+(clients.getClientConnect()+1));
                    mess=false;
                }else{
                    if(!mess){
                        
                        log.info("Max "+(clients.getClientConnect()+1) +" clients connect");
                        mess = true;
                    }
                    Thread.sleep(10000);
                }
                

            }
        } catch(InterruptedException e){
            log.info(e.getMessage());
        } 

    }
}
