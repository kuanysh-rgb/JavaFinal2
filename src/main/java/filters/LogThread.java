package filters;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogThread implements Runnable{
    private HttpServletRequest req;

    public LogThread(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void run() {
        StringBuffer url = req.getRequestURL();
        String logMessage = "#INFO URL: " + url;
        File file = new File("C:\\users\\kuany\\Desktop\\log.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileWriter writer = new FileWriter(file, true)){
            try {
                writer.write(LocalDateTime.now().toString() + ": " + logMessage);
                writer.append('\n');
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
