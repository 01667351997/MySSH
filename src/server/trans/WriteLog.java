
package server.trans;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author binhminh
 */
public class WriteLog {
    
    public Logger writeLog() throws IOException{
        Logger logger = Logger.getLogger("ssh");
        
        FileHandler fh = null;  

        // This block configure the logger with handler and formatter
        fh = new FileHandler("test/LogFile.log");
            
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter(); 
        
        fh.setFormatter(formatter);  
        
        // the following statement is used to log any messages  
        return logger;           
  
    }
}
