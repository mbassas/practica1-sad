/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package editablebufferedreader;

/**
 *
 * @author lsadusr12
 */
public class LineRead {
    
    int tamany = 0;
    String liniaEscrita;
    boolean insertMode = false;
    
    public LineRead (int N){
        this.tamany = N;
        this.liniaEscrita = "";
    }
    
    public int incPosicio(int actual){
        int aux = actual;
        if(actual < this.liniaEscrita.length()){
            return ++aux;
        }else{
            return aux;
        }
    }
    
    public int decPosicio(int actual){
        int aux = actual;
        if(actual > 0){
            return --aux;
        }else{
            return aux;
        }
    }
    
     public void mostraLinia (){
         System.out.print(this.liniaEscrita);
     }

     public String getContent () {
        return this.liniaEscrita;
     }
     
     public int mostraTamany(){
         return this.tamany;
     }
     
     public void esborra(int actual){

        if (actual == 0 || this.liniaEscrita.length() == 0) return;

         String sor1 = liniaEscrita.substring(0, actual - 1);
         String sor2 = liniaEscrita.substring(actual);
         liniaEscrita = sor1 + sor2;
     }

    public void clearScreen () {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

     public void suprimeix (int actual){

         if (this.liniaEscrita.length() == 0 || actual >= this.liniaEscrita.length()) return;

         String sor1 = liniaEscrita.substring(0, actual);
         String sor2 = liniaEscrita.substring(actual+1);
         liniaEscrita = sor1 + sor2;
     }

     public int goToEnd () {
        return this.liniaEscrita.length();
     }

     public boolean toggleInsertMode() {
        insertMode = !insertMode;
        return insertMode;
     }

     public int left (int actual){
         if (actual == 0) return 0;
         else return this.decPosicio(actual);
     }

     public int right (int actual){
         if (actual == this.tamany) return this.tamany;
         else {
             return this.incPosicio(actual);
         }
     }

     public void escriu (int button, int actual){
         String sortida = Character.toString((char)button);

         if (actual < liniaEscrita.length()) {
             String sor1 = liniaEscrita.substring(0, actual);
             String sor2 = liniaEscrita.substring(insertMode ? actual+1 : actual);
             liniaEscrita = sor1 + sortida + sor2;
         } else {
             liniaEscrita += sortida;
         }
         
     }
}
