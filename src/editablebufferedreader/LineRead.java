/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editablebufferedreader;


import java.io.IOException;
/**
 * @author lsadusr12
 */
public class LineRead {

    int tamany;
    String liniaEscrita;
    boolean insertMode;
    int cursor;

    public LineRead() {
        this.insertMode = false;
        this.cursor = 0;
        this.liniaEscrita = "";

        updateSize();
    }

    private void updateSize() {
        this.tamany = getTerminalSize();
    }

    private int getTerminalSize(){
        String[] consoleSize = {"/bin/sh", "-c", "tput cols < /dev/tty"};
        try {
            Process p = Runtime.getRuntime().exec(consoleSize);
            p.waitFor();

            byte[] b = new byte[10];
            int bytesRead = p.getInputStream().read(b);
            if(bytesRead > 0) {
                String output = new String(b).replace("\n", "").trim();
                return Integer.parseInt(output);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 100; //default total columns value
    }
    

    public void incPosition() {
        if (this.cursor >= this.liniaEscrita.length()) return;
        this.cursor++;
    }

    public void decPosition() {
        if (this.cursor <= 0) return;
        this.cursor--;
    }

    private String BLINKING_BAR = "\033[5 q";
    private String BLINKING_BLOCK = "\033[1 q";

    public void setCursor() throws IOException {
        final String SET_CURSOR_BEGINNNING = "\033[" + this.tamany +  "D";
        final String SET_CURSOR_POSITION = "\033[" + Integer.toString(this.cursor) + "C";
        System.out.print(SET_CURSOR_BEGINNNING);
        
        if (this.cursor > 0) {
            System.out.print(SET_CURSOR_POSITION);
        }

        System.out.print(this.insertMode ? BLINKING_BLOCK : BLINKING_BAR);
    }

    public void showLine() {
        System.out.print(this.liniaEscrita);
    }

    public String getContent() {
        return this.liniaEscrita;
    }

    public void delete() {

        if (this.cursor == 0 || this.liniaEscrita.length() == 0) return;

        String sor1 = this.liniaEscrita.substring(0, this.cursor - 1);
        String sor2 = this.liniaEscrita.substring(this.cursor);
        this.liniaEscrita = sor1 + sor2;
    }

    public void clearScreen() {
        final String ANSI_CLEAR_LINE = "\033[K";
        final String SET_CURSOR_BEGINNNING = "\033[" + this.tamany +  "D"; //SHOUL BE COLS
        System.out.print(SET_CURSOR_BEGINNNING);
        System.out.print(ANSI_CLEAR_LINE);
    }

    public void suppress() {

        if (this.liniaEscrita.length() == 0 || this.cursor >= this.liniaEscrita.length()) return;

        String sor1 = this.liniaEscrita.substring(0, this.cursor);
        String sor2 = this.liniaEscrita.substring(this.cursor + 1);
        this.liniaEscrita = sor1 + sor2;
    }

    public void goHome() {
        this.cursor = 0;
    }

    public void goToEnd() {
        this.cursor = this.liniaEscrita.length();
    }

    public void toggleInsertMode() {
        this.insertMode = !this.insertMode;
    }

    public void left() {
        if (this.cursor == 0) return;

        this.decPosition();
    }

    public void right() {
        if (this.cursor == this.tamany) return;

        this.incPosition();
    }

    public void write(int button) {
        if(this.liniaEscrita.length() == this.tamany) return;

        String sortida = Character.toString((char) button);

        if (this.cursor < this.liniaEscrita.length()) {
            String sor1 = this.liniaEscrita.substring(0, this.cursor);
            String sor2 = this.liniaEscrita.substring(this.insertMode ? this.cursor + 1 : this.cursor);
            this.liniaEscrita = sor1 + sortida + sor2;
        } else {
            this.liniaEscrita += sortida;
        }

    }
}
