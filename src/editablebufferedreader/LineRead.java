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
    int actual;
    
    public LineRead(int N) {
        this.tamany = N;
        this.insertMode = false;
        this.actual = 0;
        this.liniaEscrita = "";

    }

    public void incPosicio() {
        if (this.actual >= this.liniaEscrita.length()) return;
        this.actual++;
    }

    public void decPosicio() {
        if (this.actual <= 0) return;
        this.actual--;
    }

    private String BLINKING_BAR = "\033[5 q";
    private String BLINKING_BLOCK = "\033[1 q";

    public void setCursor() throws IOException {
        final String foo = "\u001b[1;" + Integer.toString(this.actual + 1) + "H";
        System.out.print(foo);

        System.out.print(this.insertMode ? BLINKING_BLOCK : BLINKING_BAR);

    }

    public void mostraLinia() {
        System.out.print(this.liniaEscrita);
    }

    public String getContent() {
        return this.liniaEscrita;
    }

    public void esborra() {

        if (this.actual == 0 || this.liniaEscrita.length() == 0) return;

        String sor1 = this.liniaEscrita.substring(0, this.actual - 1);
        String sor2 = this.liniaEscrita.substring(this.actual);
        this.liniaEscrita = sor1 + sor2;
    }

    public void clearScreen() {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

    public void suprimeix() {

        if (this.liniaEscrita.length() == 0 || this.actual >= this.liniaEscrita.length()) return;

        String sor1 = this.liniaEscrita.substring(0, this.actual);
        String sor2 = this.liniaEscrita.substring(this.actual + 1);
        this.liniaEscrita = sor1 + sor2;
    }

    public void goHome() {
        this.actual = 0;
    }

    public void goToEnd() {
        this.actual = this.liniaEscrita.length();
    }

    public void toggleInsertMode() {
        this.insertMode = !this.insertMode;
    }

    public void left() {
        if (this.actual == 0) return;

        this.decPosicio();
    }

    public void right() {
        if (this.actual == this.tamany) return;

        this.incPosicio();
    }

    public void escriu(int button) {
        String sortida = Character.toString((char) button);

        if (this.actual < this.liniaEscrita.length()) {
            String sor1 = this.liniaEscrita.substring(0, this.actual);
            String sor2 = this.liniaEscrita.substring(this.insertMode ? this.actual + 1 : this.actual);
            this.liniaEscrita = sor1 + sortida + sor2;
        } else {
            this.liniaEscrita += sortida;
        }

    }
}
