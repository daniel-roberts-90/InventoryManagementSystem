/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.Product;
import model.inHouse;
import model.outSourced;



/**
 * After opening Main.zip file, the javadoc comments can be found at /javadoc 
 * 
 * FUTURE ENHANCEMENT: attempt was made to search parts and products regardless of case, for the most part tests were accurate, however a few test examples fail
 * 
 * future enhancement would be to ensure all searches populated the table with matching values regardless of character case or the section of characters entered
 * 
 * example being *Computer*, entered text value being *uTEr* would populate the table with any matching items that had characters of *uter*
 * 
 * @author Daniel Roberts
 */
public class Main extends Application {
    /** loads main scene*/
    @Override 
    public void start(Stage mainStage) throws Exception {
        Parent root  = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.setTitle("Inventory Management System");
        mainStage.show();
    }

    /** creates the sample inventory of parts
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int partId = Inventory.getNewPartId();
        inHouse computerMonitor = new inHouse(partId, "24in Monitor", 299.99, 10, 1, 15, 1001);
        
        partId = Inventory.getNewPartId();
        inHouse computerKeyboard = new inHouse(partId, "Keyboard", 99.99, 5, 1, 15, 1002);
        
        partId = Inventory.getNewPartId();
        inHouse computerMouse = new inHouse (partId, "Mouse", 29.99, 5, 1, 15, 1003);
        
        partId = Inventory.getNewPartId();
        outSourced computerConnectionCables = new outSourced(partId, "Computer Connection Cables", 19.99, 20, 1, 100, "Logicom");
        /** adds the parts to the inventory */
        Inventory.addPart(computerMonitor);
        Inventory.addPart(computerKeyboard);
        Inventory.addPart(computerMouse);
        Inventory.addPart(computerConnectionCables);
        /** adds the product to inventory */
        int productId = Inventory.getNewProductId();
        Product computerHardwarePackage = new Product(productId, "Computer Hardware Package", 419.99, 5, 1, 20);
        Inventory.addProduct(computerHardwarePackage);
           computerHardwarePackage.addAssociatedPart(computerKeyboard);
           computerHardwarePackage.addAssociatedPart(computerMouse);
           computerHardwarePackage.addAssociatedPart(computerConnectionCables);
        productId = Inventory.getNewProductId();
        Product computerDeluxePackage = new Product (productId, "Computer Deluxe Package", 699.99, 5, 1, 20);
        Inventory.addProduct(computerDeluxePackage);
          computerDeluxePackage.addAssociatedPart(computerMonitor);
          computerDeluxePackage.addAssociatedPart(computerKeyboard);
          computerDeluxePackage.addAssociatedPart(computerMouse);
          computerDeluxePackage.addAssociatedPart(computerConnectionCables);
        launch(args);
    }

   
}
    

