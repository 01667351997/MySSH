package clients.model;

/**
 *
 * @author binhminh
 */
public interface C_IProcessCommand {

    public boolean login(String user, String pass);
    
    public boolean cd(String dest);

    public boolean pwd();

    public boolean ls();

    public boolean date();

    public boolean rm(String dest);
    
    public boolean mv(String src, String dest);
}
