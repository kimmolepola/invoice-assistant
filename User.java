/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author
 */
public class User {

    private final String name;
    private final int number;
    private final Customer customer;
    private final int hashNumber;
    private Map<String, Integer> items;
    private final int allItemsAmount;
    private boolean correctUserCustomerPair;
    
    public boolean getCorrectUserCustomerPair(){
        return correctUserCustomerPair;
    }
    
    public void setCorrectUserCustomerPair(boolean b){
        correctUserCustomerPair = b;
    }
    
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(customer.GetFileString());
        sb = appendStringAndSpaces(sb, name, Constants.ITEMNAMEMAXLENGTH.value());
        for (String string : items.keySet()) {
            sb = appendStringAndSpaces(sb, String.valueOf(items.get(string)), Constants.ITEMPRICEMAXLENGTH.value());
            sb = appendStringAndSpaces(sb, string, Constants.ITEMNAMEMAXLENGTH.value());
        }
        while (sb.length() < customer.GetFileString().length() + allItemsAmount * (Constants.ITEMPRICEMAXLENGTH.value() + Constants.ITEMNAMEMAXLENGTH.value())) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public User(String userName, int userCustomerNumber, Customer userCustomer, int allItemsAmountInt) {
        name = userName;
        number = userCustomerNumber;
        hashNumber = createHashNumber(name, number);
        customer = userCustomer;
        items = new HashMap<>();
        allItemsAmount = allItemsAmountInt;
        correctUserCustomerPair = true;
    }
    
    public Map<String, Integer> getItems(){
        return items;
    }
    
    public void setItems(Map<String, Integer> itemsMap){
        items = itemsMap;
    }

    public void putItem(String itemName, int itemPrice) {
        items.put(itemName, itemPrice);
    }

    public void removeItem(String itemName, int itemPrice) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
        }
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        //return "Tilaajan nimi: " + name + System.lineSeparator() + customer + itemsToString();
        return "Tilaajan nimi: " + name + System.lineSeparator() + customer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User u = (User) o;
        return number == u.getNumber() && name.equals(u.getName());
    }

    @Override
    public int hashCode() {
        return hashNumber;
    }

    private String itemsToString() {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (String string : items.keySet()) {
            sb.append("Oletustuote ");
            sb.append(counter);
            sb.append(": ");
            sb.append(string);
            sb.append(" ");
            sb.append(items.get(string));
            sb.append("e");
            sb.append(System.lineSeparator());
            counter++;
        }
        return sb.toString();
    }

    private StringBuilder appendStringAndSpaces(StringBuilder sb, String string, int fullLength) {
        if (string.length() > fullLength) {
            sb.append(string.substring(0, fullLength));
            return sb;
        }
        sb.append(string);
        int length = string.length();
        while (length < fullLength) {
            sb.append(" ");
            length++;
        }
        return sb;
    }

    private int createHashNumber(String userName, int userCustomerNumber) {
        return Integer.valueOf(String.valueOf(userCustomerNumber) + String.valueOf(stringToInt(userName)));
    }

    private int stringToInt(String string) {
        char[] chars = string.toCharArray();
        int integer = 0;
        for (int i = 0; i < chars.length; i++) {
            integer += chars[i];
        }
        return integer;
    }
}
