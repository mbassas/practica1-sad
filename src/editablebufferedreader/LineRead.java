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
    private Console console;

    public LineRead() {
        this.insertMode = false;
        this.cursor = 0;
        this.liniaEscrita = "";
        this.console = new Console();

        updateSize();
    }

    private void updateSize() {
        this.tamany = this.console.getSize();
    }
   

    public void incPosition() {
        if (this.cursor >= this.liniaEscrita.length()) return;
        this.cursor++;
    }

    public void decPosition() {
        if (this.cursor <= 0) return;
        this.cursor--;
    }

    public void showLine() {
        this.console.print(this.liniaEscrita);
    }

    public String getContent() {
        return this.liniaEscrita;
    }

    public void setCursor() throws IOException {
        this.console.setCursor(this.cursor, this.insertMode);
    }

    public void delete() {

        if (this.cursor == 0 || this.liniaEscrita.length() == 0) return;

        String sor1 = this.liniaEscrita.substring(0, this.cursor - 1);
        String sor2 = this.liniaEscrita.substring(this.cursor);
        this.liniaEscrita = sor1 + sor2;
    }

    public void clearScreen() {
        this.console.clearLine();
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

    public void setRawMode() throws IOException {
        this.console.setRaw();
    }

    public void setCookedMode() throws IOException {
        this.console.unsetRaw();
    }
}
