/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editablebufferedreader;

import editablebufferedreader.LineRead;
import editablebufferedreader.Console;
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

    private Console console;

    public EditableBufferedReader(Reader in) {
        super(in);
        this.console = new Console();
    }

    @Override
    public int read() throws IOException {
        int read = System.in.read();
        return read;
    }

    public String readLine() throws IOException {

        LineRead line = new LineRead();
        try {
            int button = 0;
            this.console.setRaw();

            while (button != ENTER) {
                button = this.read();

                switch (button) {

                    case (ESC):
                        button = this.read();
                        button = this.read();

                        switch (button) {
                            case (FIN):
                                line.goToEnd();
                                break;

                            case (INICIO):
                                line.goHome();
                                break;

                            case (RIGHT):
                                line.right();
                                break;

                            case (LEFT):
                                line.left();
                                break;

                            case (INSERT):
                                button = this.read();
                                line.toggleInsertMode();
                                break;

                            default:
                                break;
                        }
                        break;

                    case (BACKSPACE):
                        line.delete();
                        line.decPosition();

                        break;

                    case (SUPR):
                        line.suppress();
                        break;

                    default:
                        line.write(button);
                        line.incPosition();
                        break;
                }

                line.clearScreen();
                line.showLine();
                line.setCursor();
            }

            line.clearScreen();
            return line.getContent();

        } finally {

            this.console.unsetRaw();
        }
    }
}
