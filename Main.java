/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

/**
 *
 * @author
 */
public class Main {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FileReaderClass fileReader = new FileReaderClass();
                Map<String, Integer> invoiceItems = InvoiceItemsParser.Parse(fileReader.ReadFile("Laskutustuotteet.txt"));

                Map<Integer, Customer> customers = new HashMap<>();

                List<String> linesFromOldFile = fileReader.ReadFile("Asiakkaat.txt");
                CustomerInformationParser.Parse(linesFromOldFile, customers);
                List<String> linesFromCurrentFile = fileReader.ReadFile("TETO_0_20170215_0745_59.txt");
                CustomerInformationParser.Parse(linesFromCurrentFile, customers);

                Map<String, User> oldUsers = UserInfoParserForOldFile.parse(linesFromOldFile, customers, invoiceItems.size());
                Map<String, User> currentUsers = UserInfoParserForCurrentFile.parse(linesFromCurrentFile, customers, invoiceItems.size());

                for (String string: currentUsers.keySet()){
                    if(oldUsers.containsKey(string)){
                        currentUsers.get(string).setItems(oldUsers.get(string).getItems());
                    }
                }
                Map<String, User> allUsers = new HashMap<>(currentUsers);
                allUsers.putAll(oldUsers);
                //
                Map<String, User> newUsers = new HashMap<>(currentUsers);
                newUsers.keySet().removeAll(oldUsers.keySet());

                UserSaver userSaver = new UserSaver("Asiakkaat.txt");
                userSaver.setUsers(allUsers);
                
                InvoiceAnalyser invoiceAnalyser = new InvoiceAnalyser(linesFromCurrentFile, currentUsers);
                invoiceAnalyser.analyse();
                WindowHandler wh = new WindowHandler(invoiceAnalyser, invoiceItems, newUsers, currentUsers, userSaver);
            }
        });
    }
}
