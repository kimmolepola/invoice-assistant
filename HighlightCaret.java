/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.awt.Color;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author
 */
public class HighlightCaret extends DefaultCaret{
    private static final Highlighter.HighlightPainter unfocusedPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
    private static final Highlighter.HighlightPainter focusedPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private static final long serialVersionUID = 1L;
    private boolean isFocused;
    
    @Override
    protected Highlighter.HighlightPainter getSelectionPainter(){
        setBlinkRate(500);
        return isFocused ? focusedPainter : unfocusedPainter;
    }
    
    @Override
    public void setSelectionVisible(boolean hasFocus){
        if (hasFocus != isFocused){
            isFocused = hasFocus;
            super.setSelectionVisible(false);
            super.setSelectionVisible(true);
        }
    }    
}
