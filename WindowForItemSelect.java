/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 *
 * @author
 */
public class WindowForItemSelect {

    public WindowForItemSelect(JFrame frame, ActionListener al, Map<String, Integer> invoiceItems, Map<String, User> users, UserSaver userSaver) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (users.size() == 0) {
            frame.add(createNoUsersPanel(al));
        } else {
            JPanel panel = createContentPanel(al, invoiceItems, users, userSaver);
            JScrollPane pane = new JScrollPane(panel);
            pane.setPreferredSize(new Dimension(300, 300));
            pane.getVerticalScrollBar().setUnitIncrement(16);
            frame.add(pane);
        }
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private JPanel createNoUsersPanel(ActionListener al) {
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.setBackground(Color.white);
        JPanel panel0 = new JPanel(new BorderLayout());
        panel0.setBackground(Color.white);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(System.lineSeparator() + "Uusia käyttäjätietoja 0 kpl" + System.lineSeparator());
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        panel.add(PanelIndent.indent(textArea, Color.WHITE), c);
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        JPanel buttonPanel = createNextButton(al);
        buttonPanel.setBackground(Color.white);
        panel.add(PanelIndent.indent(buttonPanel, Color.WHITE), c);
        panel0.add(panel, BorderLayout.LINE_START);
        panel1.add(panel0, BorderLayout.PAGE_START);
        return panel1;
    }

    private JPanel createNextButton(ActionListener al) {
        JPanel panel = new JPanel();
        JButton button = new JButton("SEURAAVA");
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(al);
        panel.add(button);
        return panel;
    }

    private JPanel createContentPanel(ActionListener al, Map<String, Integer> invoiceItems, Map<String, User> users, UserSaver userSaver) {
        JPanel panel0 = new JPanel(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(" ");
        panel.add(textArea);
        textArea = new JTextArea();
        textArea.setText("Uusia käyttäjätietoja " + users.size() + " kpl" + System.lineSeparator() + "Valitse käyttäjien oletustuotteet" + System.lineSeparator());
        panel.add(PanelIndent.indent(textArea, Color.WHITE));

        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(0, 1));
        panel.add(separator);

        for (User user : users.values()) {
            panel.add(createCustomerAndButtonsPanel(user, createItemButtons(invoiceItems, user)));
        }
        panel0.add(panel);
        panel0.add(createSaveButton(al, userSaver), BorderLayout.SOUTH);

        if (panel.getComponent(0).getClass() == JTextArea.class) {
            ((JTextArea) panel.getComponent(0)).setCaretPosition(0);
        }
        return panel0;
    }

    private JPanel createSuccessMessage(boolean success) {
        JPanel panel = new JPanel();
        String imageFilename;
        String text;
        if (success) {
            imageFilename = "Success.png";
            text = "Tallennettu onnistuneesti";
        } else {
            imageFilename = "Unsuccess.png";
            text = "Tallennus epäonnistui";
        }
        try {
            BufferedImage image = ImageIO.read(new File(imageFilename));
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            panel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(text);
        textArea.setBackground(null);
        panel.add(textArea);
        return panel;
    }

    private JPanel createSaveButton(ActionListener al, UserSaver userSaver) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panel5 = new JPanel();
        JButton button = new JButton("TALLENNA");
        button.setPreferredSize(new Dimension(100, 30));
        panel5.add(button);
        panel.add(panel5, BorderLayout.WEST);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                if (true) {
                if (userSaver.saveUsers()) {
                    panel.add(createSuccessMessage(true));
                    panel.add(PanelIndent.indentRight(createNextButton(al), null), BorderLayout.EAST);
                    panel.revalidate();
                } else {
                    panel.add(createSuccessMessage(false));
                    panel.revalidate();
                }
            }
        });
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        JPanel panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(0, 30));
        JPanel panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(0, 30));
        panel2.setPreferredSize(new Dimension(0, 90));
        panel2.add(panel3);
        panel2.add(panel);
        panel2.add(panel4);
        return PanelIndent.indent(PanelIndent.indent(panel2, null), null);
    }

    private JPanel createCustomerAndButtonsPanel(User user, JPanel buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(System.lineSeparator() + user);

        JTextArea emptyLine = new JTextArea();
        emptyLine.setText(System.lineSeparator());

        panel.add(createCustomerTextAreaAndFalseButton(user, buttons, emptyLine));
        panel.add(PanelIndent.indent(buttons, Color.WHITE));
        panel.add(emptyLine);

        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(0, 1));
        panel.add(separator);

        return panel;
    }

    private JPanel createCustomerTextAreaAndFalseButton(User user, JPanel buttons, JTextArea emptyLine) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(System.lineSeparator() + user);
        Color textAreaColor = textArea.getForeground();
        panel.add(PanelIndent.indent(textArea, Color.WHITE));

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.white);
        JPanel panel2 = new JPanel(new GridBagLayout());
        JToggleButton button = new JToggleButton("VIRHEELLINEN");
        Color color = button.getForeground();
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    user.setCorrectUserCustomerPair(false);
                    button.setForeground(Color.red);
                    textArea.setForeground(Color.LIGHT_GRAY);
                    buttons.setVisible(false);
                    emptyLine.setVisible(false);
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    user.setCorrectUserCustomerPair(true);
                    button.setForeground(color);
                    textArea.setForeground(textAreaColor);
                    buttons.setVisible(true);
                    emptyLine.setVisible(true);
                }
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(button, c);
        panel3.add(panel2);
        panel.add(panel3, BorderLayout.EAST);
        return panel;
    }

    private JPanel createItemButtons(Map<String, Integer> invoiceItems, User user) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);
        int row = 0;
        for (String item : invoiceItems.keySet()) {
            int price = invoiceItems.get(item);
            JToggleButton button = new JToggleButton(item + " " + price + "e");
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent ev) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        setUserItemState(user, true, item, price);
                    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                        setUserItemState(user, false, item, price);
                    }
                }
            });
            GridBagConstraints c = new GridBagConstraints();
            c.gridy = row;
            c.fill = GridBagConstraints.HORIZONTAL;
            panel.add(button, c);
            row++;
            if (item.equals("Terveystodistus korvaava")) {
                button.setSelected(true);
                setUserItemState(user, true, item, price);
            }
        }
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(panel, BorderLayout.WEST);
        panel2.setBackground(Color.WHITE);
        return panel2;
    }

    private void setUserItemState(User user, boolean put, String item, int price) {
        if (put) {
            user.putItem(item, price);
        } else {
            user.removeItem(item, price);
        }
    }
}
