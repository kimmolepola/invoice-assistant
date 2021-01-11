/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author
 */
public class FileWriterClass {
    public static boolean Write(String filename, List<String> lines){
        Path file = Paths.get(filename);
        try {
        Files.write(file, lines, Charset.forName("ISO-8859-1"));
        return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
