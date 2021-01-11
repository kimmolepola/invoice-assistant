/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laskutustiedostoapuri;

/**
 *
 * @author
 */
public enum Constants {
    ITEMNAMEMAXLENGTH(44),
    ITEMPRICEMAXLENGTH(4),
    OLDFILEFIRSTPOS(1),
    OLDFILESECONDPOS(9),
    OLDFILETHIRDPOS(269);
    
    private int i;
    
    private Constants(int i){
        this.i = i;
    }
    
    public int value(){
        return i;
    }
}
