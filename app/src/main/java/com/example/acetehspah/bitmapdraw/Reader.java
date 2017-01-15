package com.example.acetehspah.bitmapdraw;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by acetehspah on 1/14/17.
 */

public class Reader {

    public int[][] optimalPath = new int[16][2];
    public Reader()
    {
        try {
            FileReader fileReader = new FileReader("optimalpath.txt");
            BufferedReader br = new BufferedReader(fileReader);
            int count = 0;
            String r = "";
            while ((r = br.readLine()) != null) {
                String c = br.readLine();
                optimalPath[count][0] = Integer.parseInt(r);
                optimalPath[count][1] = Integer.parseInt(c);
                count++;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public int[][] getOptimalPath()
    {
        return optimalPath;
    }

}
