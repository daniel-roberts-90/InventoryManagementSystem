/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * RUNTIME ERROR was found when clicking the modify button for part and product
 * 
 * no item was selected to modify, thus nothing happened
 * 
 * ERROR alert was set in the modifyProduct to give a warning if selectedPart is Null
 * 
 * FXML Controller class
 *
 * @author Daniel Roberts
 */
public class MainController implements Initializable {
    private static Product productSelected;
    private static Part partSelected;
    public static Part getSelectedPart(){
        return partSelected;
    }
    public static Product getSelectedProduct(){
        return productSelected;
    }

    @FXML
    private Button deletePart;
    @FXML
    private TextField partSearch;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private Button modifyPart;
    @FXML
    private Button addPart;
    @FXML
    private TextField productSearch;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button exit;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * Initializes the controller class.
     */
    
    /** deletes part from partTable
     * 
     * alerts for confirmation prior to deletion
     * 
     * alerts if no part was selected to be deleted
     */
    @FXML
    private void onDeletePart(ActionEvent event) {
        partSelected = partTable.getSelectionModel().getSelectedItem();
        if(partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected. Please select a part from the list."); 
            alert.showAndWait();
        }
        
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to delete selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(partSelected);
            }
        }
    }
    /** deletes product from productTable
     * 
     * alerts if no product was selected to be deleted
     * 
     * alerts for confirmation prior to deletion
     */
     @FXML
    private void onDeleteProduct(ActionEvent event) {
        
    Product productSelected = productTable.getSelectionModel().getSelectedItem();
                
        if (productSelected == null){
                               
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Warning");
            alert1.setHeaderText("No part selected");
            alert1.setContentText("Please select a part from the list");
            alert1.showAndWait();
        }
        else{
            ObservableList<Part> associatedParts = productSelected.getAllAssociatedParts();
            
            if (associatedParts.size() >= 1){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Warning");
                alert2.setHeaderText("Part associated with selected item");
                alert2.setContentText("All associated parts must be removed from the associated list prior to deletion");
                alert2.showAndWait();
            }
            else{
                Alert alert3 = new Alert(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Confirm");
                alert3.setHeaderText("Are you sure you want to delete the selected product?");
                Optional<ButtonType> result = alert3.showAndWait();
                
                if(result.isPresent() && result.get() == ButtonType.OK){
                    Inventory.deleteProduct(productSelected);
                }
            }
        }
    }

 
 /** RUNTIME ERROR was found when clicking the modify button for part and product
 * 
 * no item was selected to modify, thus nothing happened
 * 
 * ERROR alert was set in the modifyProduct to give a warning if selectedPart is Null
 * 
 * opens modify part screen
 */

    
    @FXML
    private void onModifyPart(ActionEvent event) throws IOException {
        
        partSelected = partTable.getSelectionModel().getSelectedItem();
                
        if (partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected. Please select a part from the list."); 
            alert.showAndWait();
        }
        else {   
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
   }
    /** RUNTIME ERROR was found when clicking the modify button for part and product
 * 
 * no item was selected to modify, thus nothing happened
 * 
 * ERROR alert was set in the modifyProduct to give a warning if selectedPart is Null
 * 
 * opens modify product screen
 */

      @FXML
    private void onModifyProduct(ActionEvent event) throws IOException {
        
          productSelected = productTable.getSelectionModel().getSelectedItem();
                
        if (productSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product selected. Please select a product from the list."); 
            alert.showAndWait();
        }
        else{    
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ModifyProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }
    }
    
    /** 
     * opens add part screen 
     */
    @FXML
    private void onAddPart(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

 
    /** 
     * opens add product screen 
     */
    @FXML
    private void onAddProduct(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } 
    /** FUTURE ENHANCEMENT: attempt was made to search parts and products regardless of case, for the most part tests were accurate, however a few test examples fail; 
     * 
     * future enhancement would be to ensure all searches populated the table with matching values regardless of character case or the section of characters entered
     * 
     * example being *Computer*, entered text value being *uTEr* would populate the table with any matching items that had characters of *uter* regardless of those character case
     * 
     * Searches for parts based on value entered in search field
     */
    @FXML
    private void onPartSeach(ActionEvent event) {
    ObservableList<Part> allParts = Inventory.getAllParts();
    ObservableList<Part> partsLocated = FXCollections.observableArrayList();
    String searchPart = partSearch.getText();
    
    
    for (Part part : allParts){
        if(String.valueOf(part.getId()).contains(searchPart) || part.getName().toLowerCase().contains(searchPart) || part.getName().toUpperCase().contains(searchPart) || part.getName().regionMatches(true, 0, searchPart, 0, 1)){
            partsLocated.add(part);      
        }
    }
    
    partTable.setItems(partsLocated);
    
    if(partsLocated.isEmpty()){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText(searchPart + " not located in system.");
        alert.showAndWait();
    }
    }
    /** populates part table with all parts when search field empty */
          @FXML
    private void onPartSearchKeyPressed(KeyEvent event) {
        if (partSearch.getText().isEmpty()){
            partTable.setItems(Inventory.getAllParts());
        }
    }
    /** FUTURE ENHANCEMENT: attempt was made to search parts and products regardless of case, for the most part tests were accurate, however a few test examples fail; 
     * 
     * future enhancement would be to ensure all searches populated the table with matching values regardless of character case or the section of characters entered
     * 
     * example being *Computer*, entered text value being *uTEr* would populate the table with any matching items that had characters of *uter* regardless of those character case
     * 
     * searches for product based on value entered in search field
     */
    @FXML
    private void onProductSearch(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productsLocated = FXCollections.observableArrayList();
        String searchProduct = productSearch.getText();
        
        for (Product product : allProducts){
            if(String.valueOf(product.getId()).contains(searchProduct) || product.getName().toLowerCase().contains(searchProduct) || product.getName().toUpperCase().contains(searchProduct)){  
            productsLocated.add(product);
        }
        }
     productTable.setItems(productsLocated);
    if (productsLocated.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText(searchProduct + " not located in system.");
        alert.showAndWait();
    }
        
    }
    /** 
     * populates product tables with all products when search field empty 
     */
     @FXML
    private void onProductSearchKeyPressed(KeyEvent event) {
        if (productSearch.getText().isEmpty()){
            productTable.setItems(Inventory.getAllProducts());
    }
    }

    /** 
     * exits Inventory Management System and gives a confirmation alert prior to exiting 
     */
    @FXML
    private void onExit(ActionEvent event) {
        
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to exit the Inventory Management System?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {   
            System.exit(0);
    }
    }
        /** 
         * populates the product and part tables with items set in inventory 
         */    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
    }
}
    
        
        
    
    

