package server.conn;

import server.infomation.Clients;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.trans.WriteLog;

/**
 *
 * @author binhminh
 */
public class ClientHandler implements Runnable {

    ClientSession clientss = null;
    Clients clients = null;
    FileExe file;
    Logger log;
    long endTimeMillis = 0;
    public ClientHandler(Socket clientSocket, Clients clients,Logger log) {

        try {
            this.log=log;
            this.clients = clients;
            clientss = new ClientSession(clientSocket);
            file = new FileExe();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    @Override
    public void run() {
        Timer timer = new Timer();
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // task to run goes here
                log.info(String.format("%10s %10s %10s",new Date()," Timer check active ",clientss.getSock()));
                
                
                if ((System.currentTimeMillis() - endTimeMillis)>300000) {
                    try {
                        clientss.getReader().close();
                        clientss.getWriter().close();
                        clientss.getSock().close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    
                }
            }
        };

        long delay = 10000;
        long intevalPeriod = 60 * 1000; 
        // schedules the task to be run in an interval 
        timer.scheduleAtFixedRate(task, delay,intevalPeriod);
            // variable check is login success
        boolean login = false;
        
        // Time out or try over 3 turn will be kick off
        while (true) {
            endTimeMillis = System.currentTimeMillis();
            // This is place receive message
            String received = "";
            try {
                if ((received = clientss.getReader().readUTF()) != null) {
                    log.info(String.format("%10s %10s %10s", new Date()," Command: "+ received, clientss.getSock()));
                    
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                log.info("Have client disconnect "+clientss.getSock());
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
                    log.info(String.format("%10s %10s %10s",new Date()," connect success!",clientss.getSock()));
                    
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
            
            

        }// end while
        timer.cancel();
    }// end run
    
}
