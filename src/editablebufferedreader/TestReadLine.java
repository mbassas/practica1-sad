package editablebufferedreader;

import java.io.*;

class TestReadLine {

    public static void main(String[] args) throws IOException {
        
        EditableBufferedReader br = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = "";

        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nline is: " + str);

    }

}