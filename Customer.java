/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoiceassistant;

/**
 *
 * @author
 */
public class Customer {

    private int number;
    private String name;
    private String nameExtension;
    private String address;
    private String postNumber;
    private String postOffice;
    private String businessID;
    private String oVTID;
    private String eInvoiceID;
    private String intermediatorID;
    private int hashNumber;
    private String fileString;

    public Customer(String customerFileString, int customerNumber, String customerName, String customerNameExtension, String customerAddress, String customerPostNumber, String customerPostOffice, String customerBusinessID, String customerOVTID, String customerEInvoiceID, String customerIntermediatorID) {
        fileString = customerFileString;
        number = customerNumber;
        name = customerName;
        nameExtension = customerNameExtension;
        address = customerAddress;
        postNumber = customerPostNumber;
        postOffice = customerPostOffice;
        businessID = customerBusinessID;
        oVTID = customerOVTID;
        eInvoiceID = customerEInvoiceID;
        intermediatorID = customerIntermediatorID;
    }
    
    public String GetFileString(){
        return fileString;
    }
    
    public int GetNumber(){
        return number;
    }

    @Override
    public String toString() {
        return BuildString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer c = (Customer) o;
        return this.number == c.GetNumber();
    }

    @Override
    public int hashCode() {
        return number;
    }
    
    private String BuildString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Asiakasnumero: ");
        sb.append(number);
        sb.append(System.lineSeparator());
        sb.append("Asiakkaan nimi: ");
        sb.append(name);
        sb.append(System.lineSeparator());
        if (!nameExtension.equals("")) {
            sb.append("Nimen jatko: ");
            sb.append(nameExtension);
            sb.append(System.lineSeparator());
        }
        sb.append("Lähiosoite: ");
        sb.append(address);
        sb.append(System.lineSeparator());
        sb.append("Postinumero: ");
        sb.append(postNumber);
        sb.append(System.lineSeparator());
        sb.append("Postitoimipaikka: ");
        sb.append(postOffice);
        sb.append(System.lineSeparator());
        sb.append("Y-tunnus: ");
        sb.append(businessID);
        sb.append(System.lineSeparator());
        sb.append("OVT-tunnus: ");
        sb.append(oVTID);
        sb.append(System.lineSeparator());
        sb.append("Verkkolaskuosoite: ");
        sb.append(eInvoiceID);
        sb.append(System.lineSeparator());
        if (!intermediatorID.equals("")) {
            sb.append("Välittäjätunnus: ");
            sb.append(intermediatorID);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
