/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class InvoiceItemsParser {

    public static Map<String, Integer> Parse(List<String> lines) {
        Map<String, Integer> items = new HashMap<String, Integer>();
        boolean indexNumberIsEven = true;
        String key = "empty";
        for (String line : lines) {
            if (indexNumberIsEven) {
                key = line;
                indexNumberIsEven = false;
            } else {
                try {
                    items.put(key, Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    items.put(key, -1);
                }
                indexNumberIsEven = true;
            }
        }
        return items;
    }
}
