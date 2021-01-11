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
public class UserInfoParserForOldFile {

    public static Map<String, User> parse(List<String> lines, Map<Integer, Customer> customers, int allItemsAmount) {
        int[] charPositions = setupCharPositions(allItemsAmount);

        Map<String, User> users = new HashMap<>();

        for (String line : lines) {
            int number;
            try {
                number = Integer.parseInt(StringTrimmer.trim(line, charPositions, 0));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                number = 0;
            }
            String name = StringTrimmer.trim(line, charPositions, 2);
            User user = new User(name, number, customers.get(number), allItemsAmount);
            int index = 3;
            while (!StringTrimmer.trim(line, charPositions, index).isEmpty()) {
                int price;
                try {
                    price = Integer.parseInt(StringTrimmer.trim(line, charPositions, index));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    price = 0;
                }
                index++;
                String item = StringTrimmer.trim(line, charPositions, index);
                index++;
                user.putItem(item, price);
            }
            users.put(number + " " + name, user);
        }
        return users;
    }
    
    private static int[] setupCharPositions(int allItemsAmount) {
        int charPositionsSize = 4 + allItemsAmount * 2;
        int[] charPositions = new int[charPositionsSize];
        charPositions[0] = Constants.OLDFILEFIRSTPOS.value();
        charPositions[1] = Constants.OLDFILESECONDPOS.value();
        charPositions[2] = Constants.OLDFILETHIRDPOS.value();
        charPositions[3] = charPositions[2] + Constants.ITEMNAMEMAXLENGTH.value();
        int index = 4;
        int charPosition = charPositions[3];
        for (int i = 0; i < charPositionsSize - 4; i+=2) {
            charPosition += Constants.ITEMPRICEMAXLENGTH.value();
            charPositions[index] = charPosition;
            index++;
            charPosition += Constants.ITEMNAMEMAXLENGTH.value();
            charPositions[index] = charPosition;
            index++;
        }
        return charPositions;
    }
}
