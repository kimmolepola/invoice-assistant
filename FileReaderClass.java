/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class FileReaderClass {
    
    public List ReadFile(String fileName){
        List<String> lines = new ArrayList<String>();
        
        try {
            File file = new File(fileName);
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine())!=null){
                lines.add(line);
            }
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return lines;
    }
}
