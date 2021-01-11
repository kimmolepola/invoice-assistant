/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class InvoiceAnalyser {

    List<String> linesFromFile;
    List<String> lines;
    Map<Integer, Errors> foundErrors;
    Map<Errors, Integer> errorAmounts;
    Map<String, User> users;
    Deque<Integer> errorLocations;
    int[] errorsByType;

    public List<String> getLines() {
        return lines;
    }

    public Map<Integer, Errors> getFoundErrors() {
        return foundErrors;
    }

    public Map<Errors, Integer> getErrorAmounts() {
        return errorAmounts;
    }

    public Deque<Integer> getErrorLocations() {
        return errorLocations;
    }

    public InvoiceAnalyser(List<String> linesList, Map<String, User> usersMap) {
        linesFromFile = linesList;
        users = usersMap;
    }

    public void analyse() {
        errorLocations = new ArrayDeque<>();
        int position = 0;
        lines = new ArrayList();
        foundErrors = new HashMap<>();
        errorAmounts = new HashMap<>();
        for (Errors error : Errors.values()) {
            errorAmounts.put(error, 0);
        }
        User user = null;
        int[] charPositionsL = new int[]{1, 9, 11, 55};
        int[] charPositionsH = new int[]{1, 31, 39, 63, 83};
        int[] charPositionsT = new int[]{1, 55, 83};
        for (String line : linesFromFile) {
            Errors error = null;
            if (line.charAt(0) != 'A') {
                lines.add(line);
                if (line.charAt(0) == 'L') {
                    user = findUser(users, charPositionsL, line);
                    if (user == null) {
                        error = Errors.NOTFOUND;
                        position = addErrorLocation(errorLocations, position, charPositionsL);
                    } else if (!user.getCorrectUserCustomerPair()) {
                        error = Errors.WRONGCUSTOMER;
                        position = addErrorLocation(errorLocations, position, charPositionsL);
                    } else {
                        position += charPositionsL[charPositionsL.length - 1] + 1;
                    }
                } else if (line.charAt(0) == 'H') {
                    if (!findItem(user, charPositionsH, line)) {
                        error = Errors.ATYPICALITEM;
                        position = addErrorLocation(errorLocations, position, charPositionsH);
                    } else {
                        position += charPositionsH[charPositionsH.length - 1] + 1;
                    }
                } else if (line.charAt(0) == 'R') {
                    //jos korvaava, pitää olla "korvaa tod. ..." -teksti
                    position += 56;
                } else if (line.charAt(0) == 'T') {
                    //tarkistus
                    position += charPositionsT[charPositionsT.length - 1] + 1;
                }
                if (error != null) {
                    foundErrors.put(lines.size() - 1, error);
                    errorAmounts.put(error, errorAmounts.get(error) + 1);
                }
            }
        }
    }

    private static int addErrorLocation(Deque<Integer> errorLocations, int position, int[] charPositions) {
        errorLocations.add(position);
        position += charPositions[charPositions.length - 1] + 1;
        errorLocations.add(position - 1);
        return position;
    }

    private static User findUser(Map<String, User> currentUsers, int[] charPositionsL, String line) {
        String number = StringTrimmer.trim(line, charPositionsL, 0);
        String name = StringTrimmer.trim(line, charPositionsL, 2);
        String key = number + " " + name;
        if (currentUsers.containsKey(key)) {
            return currentUsers.get(key);
        }
        return null;
    }

    private static boolean findItem(User user, int[] charPositionsH, String line) {
        String item = StringTrimmer.trim(line, charPositionsH, 0);
        return user.getItems().containsKey(item);
    }
}
