/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author
 */
public class WindowHandler {

    public WindowHandler(InvoiceAnalyser invoiceAnalyser, Map<String, Integer> invoiceItems, Map<String, User> newUsers, Map<String, User> currentUsers, UserSaver userSaver) {
        JFrame frameForItemSelect = new JFrame("invoiceassistant");
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameForItemSelect.dispose();
                new WindowForInvoice(invoiceAnalyser, new JFrame());
            }
        };
        new WindowForItemSelect(frameForItemSelect, al, invoiceItems, newUsers, userSaver);
    }
}
