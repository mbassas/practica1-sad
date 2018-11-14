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
    
    public LineRead (int N){
        this.tamany = N;
        this.liniaEscrita = "";
    }
    
    public int incPosicio(int actual){
        int aux = actual;
        if(actual < this.tamany){
           aux++;
            return aux;
        }else{
            return aux;
        }
    }
    
    public int decPosicio(int actual){
        int aux = actual;
        if(actual == 0){
            return aux;
        }else{
            aux --;
            return aux;
        }
    }
    
     public String mostraLinia (){
         System.out.print(this.liniaEscrita);
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

          /*if (this.mostraTamany()>0 && actual == this.mostraTamany()){
                this.liniaEscrita = this.liniaEscrita.substring(0, this.liniaEscrita.length()-1);

          } else if (this.mostraTamany()>0 && actual!=0){
                String sub1 = this.liniaEscrita.substring(0, actual-1);
                String sub2 = this.liniaEscrita.substring(actual+1, this.tamany);
                this.liniaEscrita = sub1 + sub2;
           }*/
     }

    public void clearScreen () {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

     public void suprimeix (int actual){
         if(actual<this.mostraTamany()){
                String subs1 = liniaEscrita.substring(0,actual);
                if (actual+2 < this.tamany){
                    String subs2 = liniaEscrita.substring(actual+2, this.tamany);
                    liniaEscrita = subs1+subs2;
                } else liniaEscrita = subs1;
          } else if(actual==liniaEscrita.length()-1)
                liniaEscrita = liniaEscrita.substring(0,actual);
     }

     public int left (int actual){
         if (actual == 0) return 0;
         else return this.decPosicio(actual);
     }

     public int right (int actual){
        System.out.print(actual+ "++++");
        System.out.print(this.tamany+ " jiji");
         if (actual == this.tamany) return this.tamany;
         else {
             int happy = this.incPosicio(actual);
             System.out.print(happy+ " uuuh");

             return this.incPosicio(actual);
         }
     }

     public boolean insert (boolean insert){
         return !insert;
     }

     public int inicio(){
         return  0;
     }

     public int fin(){
         return this.tamany;
     }

     public void escriu (int button, int actual, boolean insert){
         String sortida = Character.toString((char)button);

         if (!insert) {

             if (liniaEscrita.length() > 0 && actual != this.tamany) {
                 if (actual != liniaEscrita.length()) {
                     String sor1 = liniaEscrita.substring(0, actual);
                     String sor2 = liniaEscrita.substring(actual);
                     liniaEscrita = sor1 + sortida + sor2;
                 } else {
                     liniaEscrita += sortida;
                 }

             }
             else if(liniaEscrita.length() == 0) liniaEscrita = sortida;

         } else {
             if (liniaEscrita.length() > 0) {
                 System.out.println(liniaEscrita.length());
                 String sorIns1 = liniaEscrita.substring(0, actual - 1);
                 String sorIns2 = liniaEscrita.substring(actual + 1, liniaEscrita.length() - 1);
                 liniaEscrita = sorIns1 + sortida + sorIns2;
             }
             else liniaEscrita = sortida;
         }
         
     }
}
