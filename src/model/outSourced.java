/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel Roberts
 */
public class outSourced extends Part {
    /** 
     * companyName for outSourced inventory 
     */
    private String companyName;
    /**
     * Constructor 
     */
    public outSourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** 
     * get method for companyName 
     */
    public String getCompanyName(){
        return companyName;
    }
    /** 
     * set method for companyName 
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    } 
}
