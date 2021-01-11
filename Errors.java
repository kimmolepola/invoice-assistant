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
public enum Errors {
    NOTFOUND("Käyttäjää tai yritystä ei löydy", "Käyttäjää tai yritystä ei löytynyt:"),
    WRONGCUSTOMER("Väärä laskutusasiakas", "Vääriä laskutusasiakkaita"),
    ATYPICALITEM("Poikkeava tuote", "Poikkeavia tuotteita");
    
    private String string;
    private String summary;
    
    private Errors(String string1, String string2){
        this.string = string1;
        this.summary = string2;
    }
    
    public String value(){
        return string;
    }
    
    public String summary(){
        return summary;
    }
}
