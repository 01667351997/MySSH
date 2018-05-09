package clients.conn;

import java.io.*;
import java.net.*;

/**
 *
 * @author binhminh
 */
public class C_ClientSession {

    private ObjectInputStream reader=null;
    private Socket sock=null;
    private ObjectOutputStream writer=null;
    private String userName;
    
    public C_ClientSession(Socket socketOfClient) {
        try {
            this.sock = socketOfClient;
            
            this.reader = new ObjectInputStream(sock.getInputStream());
            this.writer = new ObjectOutputStream(sock.getOutputStream());
            this.userName = userName;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public ObjectInputStream getReader() {
        return reader;
    }

    public Socket getSock() {
        return sock;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }
    
    
}
