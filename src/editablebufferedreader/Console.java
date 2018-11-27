package editablebufferedreader;

import java.io.*;

public class Console {
    public void setRaw() throws IOException {
        run("stty -echo raw < /dev/tty");
    }

    public void unsetRaw() throws IOException {
        run("stty -raw echo < /dev/tty");
    }

    public int getSize() throws IOException {
        byte[] buffer = new byte[512];
        int bytesRead = run("tput cols < /dev/tty", buffer);
        if(bytesRead > 0) {
            String output = new String(buffer).replace("\n", "").trim();
            return Integer.parseInt(output);
        }
        return 100; //default total columns value
    }
    
    private int run(String command) throws IOException {
        return run(command, new byte[512]);
    }

    private int run(String command, byte[] buffer) throws IOException {
        String[] cmdRaw = {"/bin/sh", "-c", command};
        try {
            Process p = Runtime.getRuntime().exec(cmdRaw);
            p.waitFor();

            return p.getInputStream().read(buffer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}