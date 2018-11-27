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

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    private int nextButton() throws IOException {
        int button = this.read();
        if (button == ESC) {
            button = this.read();
            button = this.read();
            
            // For SUPR button
            if(button == 51) button = this.read();
        }

        if (button == INSERT) {
            this.read();
        }

        return button;
    }

    public String readLine() throws IOException {

        LineRead line = new LineRead();
        try {
            int button = 0;
            line.setRawMode();

            while (button != ENTER) {
                button = this.nextButton();

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
                        line.toggleInsertMode();
                        break;

                    case (BACKSPACE):
                        line.delete();
                        line.decPosition();
                        break;

                    case (SUPR):
                        line.suppress();
                        break;
                    
                    case (ENTER):
                        line.goToEnd();
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

            return line.getContent();

        } finally {

            line.setCookedMode();
        }
    }
}
