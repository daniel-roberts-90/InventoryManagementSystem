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
public class AddProductController implements Initializable {

    @FXML
    private TextField addProductMin;
    @FXML
    private TextField searchOption;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductInv;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductId;
    @FXML
    private TableView<Part> PartsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private Button addPartToProduct;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button savePartToProduct;
    @FXML
    private Button cancelAddProduct;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartsIdCol;
    @FXML
    private TableColumn<Part, String> associatedPartsNameCol;
    @FXML
    private TableColumn<Part, Integer> associatedPartsInvCol;
    @FXML
    private TableColumn<Part, Double> associatedPartsPriceCol;

    /**
     * Initializes the controller class. Places items into the Table.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTable.setItems(Inventory.getAllParts());
        
        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /** 
     * ensures min and max valid 
     */
     private boolean validMin (int min, int max){
        boolean validTrue = true;
        
        if (min <= 0 || min >= max){
            validTrue = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Value entered for min is invalid.");
            alert.setContentText("Min value must be a value greater than 0 and less than the max value.");
            alert.showAndWait();
        }
        return validTrue;
    }
    /** 
     * ensures stock/inventory valid 
     */
    private boolean validInventory (int min, int max, int stock){
        boolean validTrue = true;
        
        if(stock < min || stock > max){
            validTrue = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Value for inventory is invalid.");
            alert.setContentText("Inventory value must be equal to or between the min and max values.");
            alert.showAndWait();
        }
        return validTrue;
    }

    /** 
     * Searches for value entered into search field regardless of case and populates the Table with the results 
     */
    @FXML
    private void onSearchOption(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsLocated = FXCollections.observableArrayList();
        String search = searchOption.getText();
        
        for (Part part : allParts){
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
     * sets the partTable to all parts when search field empty 
     */
       @FXML
    private void onSearchOptionKeyPressed(KeyEvent event) {
        if (searchOption.getText().isEmpty()){
            PartsTable.setItems(Inventory.getAllParts());
        }
    }
    /** 
     * adds part to associated parts for the product 
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
     * removes an associated part from a product 
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
    /** 
     * saves product to inventory and loads back to main screen and shows a success message if successful 
     * 
     * error message if fails 
     */
    @FXML
    private void onSavePartToProduct(ActionEvent event) throws IOException {
        try{
        int id = 0;
        String name = addProductName.getText();
        Double price = Double.parseDouble(addProductPrice.getText());
        int stock = Integer.parseInt(addProductInv.getText());
        int min = Integer.parseInt(addProductMin.getText());
        int max = Integer.parseInt(addProductMax.getText());
        
        
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Name is empty.");
            alert.setContentText("Name must contain a value.");
            alert.showAndWait();
        }
        
        else{
            if (validMin(min, max) && validInventory(min, max, stock)){
                Product newProduct = new Product(id, name, price, stock, min, max);
                
                for(Part part : associatedParts){
                    newProduct.addAssociatedPart(part);
                    }
                newProduct.setId(Inventory.getNewProductId());
                Inventory.addProduct(newProduct);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("Part added successfully.");
                    alert.setContentText("Returning to Main Menu.");
                    alert.showAndWait();
                    
                 Parent parent = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
                 Scene scene = new Scene(parent);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 stage.show();
            }
          }
       }
            catch(Exception w){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error occured");
            alert.setContentText("Ensure all fields are complete and valid values are entered.");
            alert.showAndWait();
        }
      }
     
    /** 
     * cancels from addProduct screen and loads back to main screen, shows confirmation message 
     */
    @FXML
    private void onCancelAddProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel and return to main menu?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                 Parent parent = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
                 Scene scene = new Scene(parent);
                 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 stage.setScene(scene);
                 stage.show();
     }
    }
  }
    
            
       
