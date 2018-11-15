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

    public LineRead(int N) {
        this.tamany = N;
        this.insertMode = false;
        this.cursor = 0;
        this.liniaEscrita = "";

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
        final String foo = "\u001b[1;" + Integer.toString(this.cursor + 1) + "H";
        System.out.print(foo);

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
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
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
