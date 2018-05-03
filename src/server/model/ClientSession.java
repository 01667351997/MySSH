
package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author binhminh
 */
public class ClientSession {
    private ObjectInputStream reader=null;
    private Socket sock=null;
    private ObjectOutputStream writer=null;
    private String userName;
    
    public ClientSession(Socket sock) throws IOException{
        this.sock = sock;
        this.writer = new ObjectOutputStream(sock.getOutputStream());
        this.reader = new ObjectInputStream(sock.getInputStream());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }
}
