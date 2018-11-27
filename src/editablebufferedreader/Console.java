package editablebufferedreader;

import java.io.*;

public class Console {
    public void setRaw() throws IOException {
        run("stty -echo raw < /dev/tty");
    }

    public void unsetRaw() throws IOException {
        run("stty -raw echo < /dev/tty");
    }
    
    private byte[] run(String command) throws IOException {
        String[] cmdRaw = {"/bin/sh", "-c", command};
        try {
            Process p = Runtime.getRuntime().exec(cmdRaw);
            p.waitFor();

            byte[] b = new byte[512];
            p.getInputStream().read(b);
            return b;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}