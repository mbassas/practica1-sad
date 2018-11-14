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
 *
 * @author lsadusr12
 */
public class EditableBufferedReader extends BufferedReader {

    /**
     * @param args the command line arguments
     */
    
    public static final int INSERT = 26;
    public static final int INICIO = 2;
    public static final int FIN = 3;
    public static final int LEFT = 37;
    public static final int RIGHT = 39;
    public static final int ENTER = 13;
    public static final int BACKSPACE = 127;
    public static final int SUPR = 8;
    public static final int ESC = 27;
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        
        EditableBufferedReader br = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = "";
        
        try {
            str = br.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);
        
    }

    public EditableBufferedReader(Reader in) {
        super(in);
    }
    
    public void setRaw() throws IOException{
        String [] cmdRaw = {"/bin/sh", "-c", "stty -echo raw < /dev/tty"}; 
        try{
            Runtime.getRuntime().exec(cmdRaw).waitFor();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setCursor(int position) throws IOException {
        final String foo = "\u001b[1;" + Integer.toString(position + 1) + "H";
        System.out.print(foo);

    }
    
    public void unsetRaw() throws IOException{
          String [] cooked = {"/bin/sh", "-c", "stty -raw echo < /dev/tty"}; 
        try{
            Runtime.getRuntime().exec(cooked).waitFor();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
     
    
    @Override
     public int read() throws IOException{
         int llegit = System.in.read();
         return llegit;
     }
    
     
     public String readLine() throws IOException{

        String [] tamanyConsola = {"/bin/sh", "-c", "tput cols < /dev/tty"};
        int tamany = 300; //aconseguir màxim
        LineRead linia = new LineRead(tamany);
        try{
            int button = 0, actual = 0;
            this.setRaw();
            //String sortida = Character.toString((char)button);
            boolean insert = false;

            //while (!sortida.equals("\r")){
            while (button != ENTER){
                    button = this.read();
                    switch (button) {

                        case(ESC):
                            button = this.read();
                            button = this.read();

                            switch (button){
                                case(65):
                                    break;

                                case(66):
                                    break;

                                case(67):
                                    //System.out.print(button);
                                    actual = linia.right(actual);
                                    break;

                                case(68):
                                    //System.out.print(button);
                                    actual = linia.left(actual);
                                    break;

                                default:
                                    break;
                            }
                            break;

                        case (BACKSPACE): // Esborrar
                            if (actual >0 || linia.mostraTamany() > 0){
                                //System.out.print("HERE");
                                linia.esborra(actual);
                                actual = linia.decPosicio(actual);
                            }
                            
                        break; 
                            
                        case (SUPR): //suprimir
                              if (actual > 0 || linia.mostraTamany() > 0)
                                    linia.suprimeix(actual);
                        break;

                        
                          case(INSERT): // insert
                              insert = linia.insert(insert);
                          break;
                          case(INICIO): //inicio
                              actual = linia.inicio();
                          break;
                       
                          case (FIN): // fin
                              actual = linia.fin();
                          break;
                              
                         // case (ENTER):
                         //     System.out.println("caracola");
                         //break;
                        
                          default:
                              linia.escriu(button, actual, insert );
                              actual = linia.incPosicio(actual);
                              break;

                    }


                linia.clearScreen();
                linia.mostraLinia();
                setCursor(actual);
            }


            linia.mostraLinia();

            return "kaka";
        }finally{
            this.unsetRaw();
        }
    }
}
