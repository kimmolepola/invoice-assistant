/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

import java.util.Map;

/**
 *
 * @author
 */
public class StringTrimmer {

    public static String trim(String line, int[] charPositions, int charPositionIndex) {
        try {
            return line.substring(charPositions[charPositionIndex], charPositions[charPositionIndex + 1] - 1).trim();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "";
        }

    }
}
