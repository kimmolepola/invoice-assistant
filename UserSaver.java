/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class UserSaver {

    private String filename;
    private Map<String, User> users;

    public UserSaver(String filenameString) {
        filename = filenameString;
    }

    public void setUsers(Map<String, User> usersMap) {
        users = usersMap;
    }

    public boolean saveUsers() {
        return FileWriterClass.Write(filename, mapToList(users));
    }

    private List<String> mapToList(Map<String, User> users) {
        List<String> list = new ArrayList<String>();
        for (User user : users.values()) {
            if (user.getCorrectUserCustomerPair()) {
                list.add(user.toFileString());
            }
        }
        return list;
    }
}
