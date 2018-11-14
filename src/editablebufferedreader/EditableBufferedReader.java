/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editablebufferedreader;

import editablebufferedreader.LineRead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.StringBuilder;


/**
 * @author lsadusr12
 */
public class EditableBufferedReader extends BufferedReader {

    /**
     * @param args the command line arguments
     */

    public static final int INSERT = 50;
    public static final int INICIO = 72;
    public static final int FIN = 70;
    public static final int LEFT = 68;
    public static final int RIGHT = 67;
    public static final int ENTER = 13;
    public static final int BACKSPACE = 127;
    public static final int SUPR = 126;
    public static final int ESC = 27;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here


        EditableBufferedReader br = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = "";

        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);

    }

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() throws IOException {
        String[] cmdRaw = {"/bin/sh", "-c", "stty -echo raw < /dev/tty"};
        try {
            Runtime.getRuntime().exec(cmdRaw).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String BLINKING_BAR = "\033[5 q";
    private String BLINKING_BLOCK = "\033[1 q";
    public void setCursor(int position, boolean insertMode) throws IOException {
        final String foo = "\u001b[1;" + Integer.toString(position + 1) + "H";
        System.out.print(foo);

        System.out.print(insertMode ? BLINKING_BLOCK : BLINKING_BAR);

    }

    public void unsetRaw() throws IOException {
        String[] cooked = {"/bin/sh", "-c", "stty -raw echo < /dev/tty"};
        try {
            Runtime.getRuntime().exec(cooked).waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int read() throws IOException {
        int llegit = System.in.read();
        return llegit;
    }


    public String readLine() throws IOException {

        String[] tamanyConsola = {"/bin/sh", "-c", "tput cols < /dev/tty"};
        int tamany = 300; //aconseguir màxim
        LineRead linia = new LineRead(tamany);
        try {
            int button = 0, actual = 0;
            boolean insertMode = false;
            this.setRaw();
            linia.clearScreen();
            setCursor(actual, insertMode);

            while (button != ENTER) {
                button = this.read();
                switch (button) {

                    case (ESC):
                        button = this.read();
                        button = this.read();

                        switch (button) {
                            case (FIN):
                                actual = linia.goToEnd();
                                break;

                            case (INICIO):
                                actual = 0;
                                break;

                            case (RIGHT):
                                actual = linia.right(actual);
                                break;

                            case (LEFT):
                                actual = linia.left(actual);
                                break;

                            case (INSERT):
                                button = this.read();
                                insertMode = linia.toggleInsertMode();
                                break;

                            default:
                                break;
                        }
                        break;

                    case (BACKSPACE):
                        linia.esborra(actual);
                        actual = linia.decPosicio(actual);

                        break;

                    case (SUPR):
                        linia.suprimeix(actual);
                        break;

                    default:
                        linia.escriu(button, actual);
                        actual = linia.incPosicio(actual);
                        break;

                }


                linia.clearScreen();
                linia.mostraLinia();
                setCursor(actual, insertMode);
            }

            linia.clearScreen();
            return linia.getContent();
        } finally {
            this.unsetRaw();
        }
    }
}
