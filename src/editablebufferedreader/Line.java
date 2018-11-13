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
public class Line {
    
    int tamany = 0;
    String liniaEscrita;
    
    public Line (int N){
        this.tamany = N;
        this.liniaEscrita = "";
    }
    
    public int incPosicio(int actual){
        if(actual < this.tamany){
            return actual++;
        }else{
            return actual;
        }
    }
    
    public int decPosicio(int actual){
        if(actual == 0){
            return actual--;
        }else{
            return actual;
        }
    }
     public int getPosicio(String liniaEscrita){
      
        return liniaEscrita.length();   
    }
     
     public String mostraLinia (){
         return this.liniaEscrita;
     }
     
     public int mostraTamany(){
         return this.tamany;
     }
     
     public void esborra(int actual){
          if(this.mostraTamany()>0 && actual == this.mostraTamany()){
                this.liniaEscrita = this.liniaEscrita.substring(0, this.liniaEscrita.length()-1);
                               
          } else if (this.mostraTamany()>0 && actual!=0){
                String sub1 = this.liniaEscrita.substring(0, actual-1);
                String sub2 = this.liniaEscrita.substring(0, actual+1);
                this.liniaEscrita = sub1 + sub2;
           }  
     }
     
     public void suprimeix (int actual){
         if(actual<this.mostraTamany()){
                String subs1 = liniaEscrita.substring(0,actual);
                String subs2 = liniaEscrita.substring(actual+2, this.mostraTamany());
                liniaEscrita = subs1+subs2;
          } else if(actual==liniaEscrita.length()-1)
                liniaEscrita = liniaEscrita.substring(0,actual);
     }
     
     public int left (int actual){
         return this.decPosicio(actual);
     }
     
     public int right (int actual){
         return this.incPosicio(actual);
     }
     
     public boolean insert (boolean insert){
         return !insert;
     }
     
     public int inicio(){
         return  0;
     }
     
     public int fin(){
         return this.mostraTamany();
     }
     
     public void escriu (int button, int actual, boolean insert){
         String sortida = Character.toString((char)button);
         if (!insert) {
             //System.out.println(liniaEscrita.length());
             if (liniaEscrita.length() > 0 && actual != this.tamany) {
                 if (actual != liniaEscrita.length()) {
                     String sor1 = liniaEscrita.substring(0, actual);
                     String sor2 = liniaEscrita.substring(actual + 1, liniaEscrita.length() - 1);
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
