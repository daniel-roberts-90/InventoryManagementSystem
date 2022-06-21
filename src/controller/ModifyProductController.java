/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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
 * FXML Controller class
 *
 * @author Daniel Roberts
 */
public class ModifyProductController implements Initializable {
    /** validates min and max */  
private boolean validMin(int prodMin, int prodMax){
    boolean isValid = true;
    
    if(prodMin >= prodMax || prodMin <= 0){
        isValid = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Min must be greater than 0 and less than Max value");
        alert.showAndWait();
    }
    return isValid;
}
    /** validates stock */
private boolean validStock(int prodMin, int prodMax, int prodStock){
    boolean isValid = true;
    
    if(prodStock < prodMin || prodStock > prodMax){
        isValid = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Stock must be equal to or between Min and Max");
        alert.showAndWait();
    }
    return isValid;
}


    /** 
     * returns to main menu 
     */   
 public void returnToMenu(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } 
    /** 
     * alert for product successfully saved 
     */
 public void prodSaveSuccess(ActionEvent event) throws IOException{
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle("Success");
     alert.setHeaderText("Product modified successfully");
     alert.showAndWait();
 }
 /** selectedProduct from Product class
  * 
  * productId as integer
  */
 Product selectedProduct;
 private int productId;
 /** 
  * list of parts associated with the product 
  */
 private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
     
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductMin;
    @FXML
    private TableView<Part> PartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private Button addPartToProduct;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button savePartToProduct;
    @FXML
    private Button cancelAddProduct;
    @FXML
    private TextField searchOption;
    
    
    /**
     * Initializes the controller class. 
     * 
     * Populates the text field with selectedProduct values
     * 
     * places modified product back into products table where was previously at
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        selectedProduct = MainController.getSelectedProduct();
        associatedParts = selectedProduct.getAllAssociatedParts();
            
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTable.setItems(Inventory.getAllParts());  
        
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(associatedParts);
        
        productId = Inventory.getAllProducts().indexOf(selectedProduct);
        
        addProductId.setText(String.valueOf(selectedProduct.getId()));
        addProductName.setText(selectedProduct.getName());
        addProductInv.setText(String.valueOf(selectedProduct.getStock()));
        addProductPrice.setText(String.valueOf(selectedProduct.getPrice()));
        addProductMin.setText(String.valueOf(selectedProduct.getMin()));
        addProductMax.setText(String.valueOf(selectedProduct.getMax()));
       
    }
    /** 
     * adds part to associated parts table 
     */  
    @FXML
    private void onAddPartToProduct(ActionEvent event) {
            Part selectedPart = PartsTable.getSelectionModel().getSelectedItem();
            if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
            }
            else{
                associatedParts.add(selectedPart);
                associatedPartsTable.setItems(associatedParts);
            }           
        }
    /** 
     * removes part from associated parts 
     */
    @FXML
    private void onRemoveAssociatedPartButton(ActionEvent event) {
        
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        
        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("No part selected.");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Are you sure you would like to delete the selected associated part?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK){
                associatedParts.remove(selectedPart);
                associatedPartsTable.setItems(associatedParts);
        }
    }
 }
        
        
    /** saves modified product
     * 
     * alerts if fails and alerts if successful
     * 
     * checks to ensure all field values entered are valid
     */
    @FXML
    private void onSavePartToProduct(ActionEvent event) throws IOException {
        try{
            int prodId = selectedProduct.getId();
            String prodName = addProductName.getText();
            Double prodPrice = Double.parseDouble(addProductPrice.getText());
            int prodStock = Integer.parseInt(addProductInv.getText());
            int prodMin = Integer.parseInt(addProductMin.getText());
            int prodMax = Integer.parseInt(addProductMax.getText());
            
            if (prodName.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Name field must contain a value");
                alert.showAndWait();   
        }
            else{
                if(validMin(prodMin, prodMax) && validStock(prodMin, prodMax, prodStock)){
                                    
                    Product modProduct = new Product(prodId, prodName, prodPrice, prodStock, prodMin, prodMax);
                
                    for(Part part : associatedParts){
                    modProduct.addAssociatedPart(part);
                }
                    
                    Inventory.updateProduct(productId, modProduct);
                    prodSaveSuccess(event);
                    returnToMenu(event);
                
                }
             }  
           }        
            catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Modified product fail");
                    alert.setContentText("Please try again");
            }
        }
         /** 
          * cancels out of modify product screen and returns to main menu
          * 
          * confirmation alert prior to returning to main menu
          */                  
    @FXML
    private void onCancelAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel and return to main menu?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                 returnToMenu(event);
            }
    }
    /** 
     * searches for part based on value entered into search field
     * 
     * populates the table with matching items
     */
    @FXML
    private void onSearchOption(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsLocated = FXCollections.observableArrayList();
        String search = searchOption.getText();
        
        for(Part part : allParts){
           if(String.valueOf(part.getId()).contains(search) || part.getName().toLowerCase().contains(search) || part.getName().toUpperCase().contains(search) || part.getName().regionMatches(true, 0, search, 0, 1)){
            partsLocated.add(part);
        }
    }

        PartsTable.setItems(partsLocated);
        
        if(partsLocated.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error occured");
            alert.setContentText("Ensure all fields are complete and valid values are entered.");
            alert.showAndWait();
        }
    }
        
        
    /** 
     * populates the table with all parts when search field empty
     */
    @FXML
    private void onSearchOptionPressed(KeyEvent event) {
        if(searchOption.getText().isEmpty()){
            PartsTable.setItems(Inventory.getAllParts());
        }
    }
    
}
