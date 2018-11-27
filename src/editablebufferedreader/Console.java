package editablebufferedreader;

import java.io.*;

public class Console {
    private int size;
    public Console() {
        this.size = this.getSize();
    }

    public void setRaw() throws IOException {
        run("stty -echo raw < /dev/tty");
    }

    public void unsetRaw() throws IOException {
        run("stty -raw echo < /dev/tty");
    }

    public int getSize() {
        byte[] buffer = new byte[512];
        int bytesRead = run("tput cols < /dev/tty", buffer);
        if(bytesRead > 0) {
            String output = new String(buffer).replace("\n", "").trim();
            return Integer.parseInt(output);
        }
        return 100; //default total columns value
    }

    public void print(String content) {
        System.out.print(content);
    }

    private String BLINKING_BAR = "\033[5 q";
    private String BLINKING_BLOCK = "\033[1 q";

    public void setCursor(int cursorPosition, boolean insertMode) throws IOException {
        final String SET_CURSOR_BEGINNNING = "\033[" + this.size +  "D";
        final String SET_CURSOR_POSITION = "\033[" + Integer.toString(cursorPosition) + "C";
        System.out.print(SET_CURSOR_BEGINNNING);
        
        if (cursorPosition > 0) {
            System.out.print(SET_CURSOR_POSITION);
        }

        System.out.print(insertMode ? BLINKING_BLOCK : BLINKING_BAR);
    }

    public void clearLine() {
        final String ANSI_CLEAR_LINE = "\033[K";
        final String SET_CURSOR_BEGINNNING = "\033[" + this.size +  "D";
        System.out.print(SET_CURSOR_BEGINNNING);
        System.out.print(ANSI_CLEAR_LINE);
    }
    
    private int run(String command) throws IOException {
        return run(command, new byte[512]);
    }

    private int run(String command, byte[] buffer) {
        String[] cmdRaw = {"/bin/sh", "-c", command};
        try {
            Process p = Runtime.getRuntime().exec(cmdRaw);
            p.waitFor();

            return p.getInputStream().read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}