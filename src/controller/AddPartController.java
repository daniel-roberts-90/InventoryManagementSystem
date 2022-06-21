/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Inventory;
import model.inHouse;
import model.outSourced;

/**
 * FXML Controller class
 *
 * @author Daniel Roberts
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton outSourcedButton;
    @FXML
    private ToggleGroup tgsource;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private Label partLabel;
    @FXML
    private Button cancelPartButton;
    @FXML
    private Button savePartButton;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartId;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMin;
    @FXML
    private TextField addPartMachineCompany;
    /** 
     * validates min and max 
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
     * Validates the inventory/stock 
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /** 
     * sets partLabel field to Company Name is outSourced button selected 
     */
    @FXML
    private void onOutSourcedButton(ActionEvent event) {
        partLabel.setText("Company Name");
    }
    /** 
     * sets partLabel to Machine ID if inHouse button selected 
     */
    @FXML
    private void onInHouseButton(ActionEvent event) {
        partLabel.setText("Machine ID");
    }
    /** 
     * cancels out of add part menu and returns to main menu with a warning message prior to exiting 
     */
    @FXML
    private void onCancelPartButton(ActionEvent event) throws IOException {
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
    /** 
     * saves a part into the partTable, shows a successful message if success, shows error message if fails 
     */
    @FXML
    private void onSavePartButton(ActionEvent event) throws IOException {
        try{
        int id = 0;
        String name = addPartName.getText();
        Double price = Double.parseDouble(addPartPrice.getText());
        int stock = Integer.parseInt(addPartInv.getText());
        int min = Integer.parseInt(addPartMin.getText());
        int max = Integer.parseInt(addPartMax.getText());
        int machineId;
        String companyName;
        boolean addedPart = false;
        
        if (name.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Name is empty.");
            alert.setContentText("Name must contain a value.");
            alert.showAndWait();
        }
        
        else{
            if (validMin(min, max) && validInventory(min, max, stock)){
                if(inHouseButton.isSelected()){
                    try{
                        machineId = Integer.parseInt(addPartMachineCompany.getText());
                        inHouse newInPart = new inHouse(id, name, price, stock, min, max, machineId);
                        newInPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newInPart);
                        addedPart = true;
                    }
                    catch(NumberFormatException w){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Value entered for Machine ID is not valid.");
                        alert.setContentText("Value entered may only contain numbers.");
                        alert.showAndWait();       
                    }
                }
                
                if (outSourcedButton.isSelected()){
                    companyName = addPartMachineCompany.getText();
                    outSourced newOutPart = new outSourced(id, name, price, stock, min, max, companyName);
                    newOutPart.setId(Inventory.getNewPartId());
                    Inventory.addPart(newOutPart);
                    addedPart = true;
                }
                
                if (addedPart){
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
    }
        catch(NumberFormatException w){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Error: Part add unsuccessful");
            alert.setContentText("Check all fields are complete.");
            alert.showAndWait();
            
      }
   }  
}
