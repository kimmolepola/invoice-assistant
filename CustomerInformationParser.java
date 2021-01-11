/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class CustomerInformationParser {

    public static void Parse(List<String> lines, Map<Integer, Customer> customers) {
        int[] charPositions = new int[]{1, 9, 44, 79, 114, 120, 149, 162, 201, 239};
        int lineIndex = 0;
        while (lineIndex < lines.size()) {
            if (lines.get(lineIndex).charAt(0) == 'A') {
                int charPositionIndex = 0;
                int number;
                try {
                    number = Integer.parseInt(StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    number = 0;
                }
                charPositionIndex++;
                String name = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String nameExtension = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String address = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String postNumber = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String postOffice = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String businessID = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String oVTID = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String eInvoiceID = StringTrimmer.trim(lines.get(lineIndex), charPositions, charPositionIndex);
                charPositionIndex++;
                String intermediatorID = lines.get(lineIndex).substring(charPositions[charPositionIndex]).trim();
                Customer customer = new Customer(lines.get(lineIndex), number, name, nameExtension, address, postNumber, postOffice, businessID, oVTID, eInvoiceID, intermediatorID);
                customers.put(number, customer);
            }
            lineIndex++;
        }
    }
}
