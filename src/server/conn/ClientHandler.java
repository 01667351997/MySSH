package server.conn;

import server.infomation.Clients;
import java.io.*;
import java.net.*;

/**
 *
 * @author binhminh
 */
public class ClientHandler implements Runnable {

    ClientSession clientss = null;
    Clients clients = null;
    FileExe file;
    public ClientHandler(Socket clientSocket, Clients clients) {

        try {
            this.clients = clients;
            clientss = new ClientSession(clientSocket);
            file = new FileExe();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void run() {
        
        // variable check is login success
        boolean login = false;

        // Time out or try over 3 turn will be kick off
        while (true) {

            // This is place receive message
            String received = "";
            try {
                if ((received = clientss.getReader().readUTF()) != null) {
                    System.out.println(clientss.getSock() + " command: " + received);
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                clients.disconnect();
                break;
            }
            
            
            ProcessCommand cmd = new ProcessCommand(clientss,file);
            if (received.equals("login")) {
                try {
                    login = cmd.login();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                // check number time client try to connect server 
                if (!login) {
                    try {
                        clientss.getReader().close();
                        clientss.getWriter().close();
                        this.clientss.getSock().close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                }else{
                    System.out.println(clientss.getSock()+" connect success!");
                    
                    // Dem da tang client connect
                    clients.connect();
                }
                
                
                
            } else if (login && received.equals("ls")) {
                cmd.ls();
            } else if (login && received.equals("cd")) {
                cmd.cd();
            } else if (login && received.equals("pwd")) {
                cmd.pwd();
            } else if (login && received.equals("date")) {
                cmd.date();
            } else if (login && received.equals("rm")) {
                cmd.rm();
            } else if (login && received.equals("mv")) {
                cmd.mv();
            }

            

        }

    }// end while

}
