package server.conn;

/**
 *
 * @author binhminh
 */
public class FileExe {
    private String root_directory;
    private String current_directory=null;

    public FileExe() {
        root_directory = "/home/";
        
    }

    public String getRoot_directory() {
        return root_directory;
    }

    public void setRoot_directory(String root_directory) {
        this.root_directory = root_directory;
    }

    public String getCurrent_directory() {
        return current_directory;
    }

    public void setCurrent_directory(String current_directory) {
        this.current_directory = current_directory;
    }
    
}
