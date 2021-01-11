/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class UserInfoParserForCurrentFile {

    public static Map<String, User> parse(List<String> lines, Map<Integer, Customer> customers, int allItemsAmount) {
        Map<String, User> users = new HashMap<>();
        int[] charPositions = new int[]{1, 9, 11, 55};
        for (String line : lines) {
            if (line.charAt(0) == 'L') {
                int number;
                try {
                    number = Integer.parseInt(StringTrimmer.trim(line, charPositions, 0));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    number = 0;
                }
                String name = StringTrimmer.trim(line, charPositions, 2);
                User user = new User(name, number, customers.get(number), allItemsAmount);
                users.put(number + " " + name, user);
            }
        }
        return users;
    }
}
