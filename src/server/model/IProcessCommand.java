package server.model;

import java.io.IOException;

/**
 *
 * @author binhminh
 */
public interface IProcessCommand {
    public boolean login() throws IOException;
    
    public boolean cd();
    
    public boolean pwd();

    public boolean ls();

    public boolean date();

    public boolean rm();
    
    public boolean mv();
}
