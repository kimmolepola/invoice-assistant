/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author
 */
public class PanelIndent {
    
    public static JPanel indentRight(JTextArea textArea, Color color){
        JPanel panelA = createIndent(color, false);
        panelA.add(textArea);
        return panelA;
    }
    
    public static JPanel indentRight(JPanel panel, Color color) {
        JPanel panelA = createIndent(color, false);
        panelA.add(panel);
        return panelA;
    }

    public static JPanel indent(JPanel panel, Color color) {
        JPanel panelA = createIndent(color, true);
        panelA.add(panel);
        return panelA;
    }

    public static JPanel indent(JTextArea textArea, Color color) {
        JPanel panelA = createIndent(color, true);
        panelA.add(textArea);
        return panelA;
    }
    
    public static JPanel indent(JTextPane textPane, Color color) {
        JPanel panelA = createIndent(color, true);
        panelA.add(textPane);
        return panelA;
    }

    private static JPanel createIndent(Color color, boolean indentLeft) {
        JPanel panelA = new JPanel(new BorderLayout());
        JPanel panelB = new JPanel();
        panelB.setBackground(color);
        panelB.setPreferredSize(new Dimension(10, 0));
        if (indentLeft) {
            panelA.add(panelB, BorderLayout.WEST);
        } else {
            panelA.add(panelB, BorderLayout.EAST);
        }
        return panelA;
    }
}
