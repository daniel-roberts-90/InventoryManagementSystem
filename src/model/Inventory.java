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
public class Inventory {
    /** 
     * all parts in inventory 
     */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** 
     * all products in inventory 
     */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /** 
     * unique partId for each part 
     */
    private static int partId = 0;
    /** 
     * unique product id 
     */
    private static int productId = 0;
    /** 
     * get method to retrieve all parts 
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** 
     * get method to retrieve all products  
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    /** 
     * adds part to inventory
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /** 
     * adds product to inventory 
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /** 
     * gets a new partId 
     */
    public static int getNewPartId(){
        return ++partId;
    }
    /** 
     * gets new productId 
     */
    public static int getNewProductId(){
        return ++productId;
    }
    /** 
     * looks for parts by id 
     */
    public static Part lookupPart(int partId){
        Part partLocated = null;
        
        for (Part part : allParts){
            if (part.getId() == partId){
            partLocated = part;
        }
      }
        return partLocated;
    }
    /**
     * looks for parts by name 
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsLocated = FXCollections.observableArrayList();
        
        for (Part part : allParts){
            if (part.getName().equals(partName)){
                partsLocated.add(part);
            }
        }
        return partsLocated;
    }
    /** 
     * looks for product by id 
     */
    public static Product lookupProduct(int productID){
        Product productLocated = null;
        
        for(Product product : allProducts){
            if (product.getId() == productId){
                productLocated = product;
            }
        }
        return productLocated;
    }
    /** 
     * looks for product by name 
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsLocated = FXCollections.observableArrayList();
        
        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                productsLocated.add(product);
            }
        }
        return productsLocated;
    }
    /** 
     * updates part in the parts list 
     */
    public static void updatePart (int index, Part selectedPart){
        
        allParts.set(index, selectedPart);
    }
    /** 
     * updates product in the product list
     */
    public static void updateProduct (int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    /** 
     * deletes parts for parts list 
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return false;
        }
    }
    /** 
     * deletes product from products list 
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return false;
        }
    }
}
