/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author
 */
public class WindowForInvoice implements KeyListener{

    JScrollPane pane;
    Component previouslyFocused;
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        previouslyFocused = e.getComponent();
        pane.requestFocusInWindow();
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        System.out.println(previouslyFocused.getWidth());
        previouslyFocused.requestFocusInWindow();
        
    }

    public WindowForInvoice(InvoiceAnalyser invoiceAnalyser, JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new JScrollPane(createContentPanel(invoiceAnalyser));
        pane.setPreferredSize(new Dimension(300, 300));
        pane.getVerticalScrollBar().setUnitIncrement(16);
        pane.addKeyListener(this);
        frame.add(pane);
        frame.pack();
        pane.requestFocusInWindow();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private JPanel createContentPanel(InvoiceAnalyser invoiceAnalyser) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createResultPanel(invoiceAnalyser), BorderLayout.WEST);
        JTextArea textArea = new JTextArea();
        panel.add(createSummaryPanel(textArea, invoiceAnalyser), BorderLayout.NORTH);
        textArea.setCaretPosition(0);
        panel.setBackground(Color.white);
        return panel;
    }

    private JPanel createResultPanel(InvoiceAnalyser invoiceAnalyser) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.add(createInvoicePanel(invoiceAnalyser));
        panel.add(createEmptyPanel());
        panel.add(createMessagePanel(invoiceAnalyser));
        panel.setBackground(Color.white);
        return panel;
    }

    private JPanel createInvoicePanel(InvoiceAnalyser invoiceAnalyser) {
        StringBuilder sb = new StringBuilder();
        for (String line : invoiceAnalyser.getLines()) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        JTextPane textPane = new JTextPane();
        textPane.setText(sb.toString());
        textPane.setEditable(false);
//        textPane.setCaret(new HighlightCaret());
        DefaultHighlighter.DefaultHighlightPainter highlightPainter
                = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        while (!invoiceAnalyser.getErrorLocations().isEmpty()) {
            int start = invoiceAnalyser.getErrorLocations().poll();
            int end = invoiceAnalyser.getErrorLocations().poll();
            try {
                textPane.getHighlighter().addHighlight(start, end, highlightPainter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        JPanel panel = new JPanel();
        panel.add(textPane);
        panel.setBackground(Color.white);
        textPane.addKeyListener(this);
        return panel;
    }

    private JPanel createEmptyPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setPreferredSize(new Dimension(100, 100));
        return panel;
    }

    private JPanel createMessagePanel(InvoiceAnalyser invoiceAnalyser) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < invoiceAnalyser.getLines().size(); i++) {
            if (invoiceAnalyser.getFoundErrors().containsKey(i)) {
                sb.append(invoiceAnalyser.getFoundErrors().get(i).value());
                sb.append(System.lineSeparator());
            } else {
                sb.append(System.lineSeparator());
            }
        }

        JTextPane textPane = new JTextPane();
        textPane.setText(sb.toString());
        textPane.setEditable(false);
        Style style = textPane.addStyle("Italic", null);
        StyleConstants.setItalic(style, true);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setCharacterAttributes(0, sb.length(), style, true);

        JPanel panel = new JPanel();
        panel.add(textPane);
        panel.setBackground(Color.white);
        textPane.addKeyListener(this);
        return panel;
    }

    private JPanel createSummaryPanel(JTextArea textArea, InvoiceAnalyser invoiceAnalyser) {
        textArea.setEditable(false);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        StringBuilder sb = new StringBuilder();
        sb.append("YHTEENVETO");
        sb.append(System.lineSeparator());
        if (!invoiceAnalyser.getErrorAmounts().isEmpty()) {
            for (Errors error : invoiceAnalyser.getErrorAmounts().keySet()) {
                if (invoiceAnalyser.getErrorAmounts().get(error) != 0) {
                    sb.append(error.summary());
                    sb.append(" ");
                    sb.append(invoiceAnalyser.getErrorAmounts().get(error));
                    sb.append(" kpl");
                    sb.append(System.lineSeparator());
                }
            }
        } else {
            sb.append("Virheitä löydetty 0 kpl");
        }
        textArea.setText(sb.toString());
        panel.add(PanelIndent.indent(textArea, Color.white));
        panel.setBackground(Color.white);
        textArea.addKeyListener(this);
        return panel;
    }
}
