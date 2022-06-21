/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Daniel Roberts
 */
public class Product {
    /** 
     * id as integer 
     */
    private int id; 
    /** 
     * name as a String 
     */
    private String name;
    /** 
     * price as double 
     */
    private double price;
    /** 
     * stock as integer 
     */
    private int stock;
    /** 
     * min as integer 
     */
    private int min;
    /** 
     * max as integer 
     */
    private int max;
    /** 
     * the list for the associated parts of the products 
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    /** 
     * Constructor 
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /** Get method for ID
     * 
     * @return id  */
    public int getId(){
        return id;
    }
    /** 
     * set method for ID
     * 
     * @param id
     */
    public void setId(int id) {
    this.id = id; 
}
    /** 
     * Get method for Name
     * 
     * @return name 
     */
    public String getName(){
        return name;
    }   
    /** 
     * set method for Name
     * 
     * @param name
     */
    public void setName(String name){
    this.name = name;   
}
    /** 
     * Get method for Price
     * 
     * @return price  
     */
    public double getPrice(){
        return price;
    }
    /** 
     * set method for Price
     * 
     * @param price 
     */
    public void setPrice(double price) {
      this.price = price;
}
    /** 
     * Get method for Stock
     * 
     * @return stock
     */
    public int getStock(){
        return stock;
    }
    /** 
     * set method for Stock
     * 
     * @param stock
     */
    public void setStock(int stock){
      this.stock = stock;
}
    /** 
     * Get method for Min
     * 
     * @return min  
     */
    public int getMin(){
        return min;
    }
    /** 
     * set method for Min
     * 
     * @param min
     */
    public void setMin(int min){
       this.min = min;
}
    /** 
     * Get method for Max
     * 
     * @return max 
     */
    public int getMax(){
        return max;
    } 
    /** 
     * set method for Max
     * 
     * @param max
     */
    public void setMax(int max) {
       this.max = max;
}
    /** 
     * adding associated part
     * 
     * @param part that's added
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /** 
     * gets the list of associated parts for the product
     * 
     * @return list of associated parts 
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    
    /** 
     * Delete the selected associated part from the product
     * 
     * @param selectedAssociatedPart part to be deleted
     * 
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        
    } 
   } 
  }
   
        