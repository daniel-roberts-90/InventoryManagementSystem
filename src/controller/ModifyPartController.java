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
import model.Part;
import model.inHouse;
import model.outSourced;

/**
 * FXML Controller class
 *
 * @author Daniel Roberts
 */
public class ModifyPartController implements Initializable {

   public Part selectedPart;
   private int partId; 

    @FXML
    private Label partsLabel;
    @FXML
    private Button saveModifyPart;
    @FXML
    private Button cancelModifyPart;
    @FXML
    private TextField addPartId;
    @FXML
    private TextField addPartMax;
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addPartInv;
    @FXML
    private TextField addPartPrice;
    @FXML
    private TextField addPartMachineCompany;
    @FXML
    private TextField addPartMin;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private ToggleGroup tgsource;
    @FXML
    private RadioButton outSourcedButton;
    
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
    /** saves the modified part
     * 
     * checks to ensure accuracy of fields being entered
     * 
     * gives error message if fails, success message if successful 
     */
    @FXML
    private void onSaveModifyPart(ActionEvent event) throws IOException {
         int partStock = Integer.parseInt(addPartInv.getText());
         int partMin = Integer.parseInt(addPartMin.getText());
         int partMax = Integer.parseInt(addPartMax.getText());
         
         if(partMin > partMax){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Warning");
             alert.setHeaderText("Min cannot be greater than Max");
             alert.showAndWait();                   
         }
         
         else if(partStock < partMin || partStock > partMax){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Warning");
             alert.setHeaderText("Inventory cannot be less than Min or greater than Max");
             alert.showAndWait(); 
         }
         
         else {
             int id = Integer.parseInt(addPartId.getText());
             String name = addPartName.getText();
             double price = Double.parseDouble(addPartPrice.getText());
             int stock = Integer.parseInt(addPartInv.getText());
             int min = Integer.parseInt(addPartMin.getText());
             int max = Integer.parseInt(addPartMax.getText());
             
             if(inHouseButton.isSelected()) {
                 try {
                     int machineId = Integer.parseInt(addPartMachineCompany.getText());
                     inHouse temp = new inHouse(id, name, price, stock, min, max, machineId);
                     Inventory.updatePart(partId, temp);
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Success");
                     alert.setHeaderText("Successfully modified part");
                     alert.setContentText("Returning to Main Menu");
                     alert.showAndWait();
                     returnToMenu(event);
                 }
                 catch(NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Invalid Machine ID entry");
                    alert.showAndWait();  
                 }     
             }
         
             else{
                 String companyName = addPartMachineCompany.getText();
                 outSourced temp = new outSourced(id, name, price, stock, min, max, companyName);
                 Inventory.updatePart(partId, temp);
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Success");
                 alert.setHeaderText("Successfully modified part");
                 alert.setContentText("Returning to Main Menu");
                 alert.showAndWait();
                 returnToMenu(event);
          }
   
        }
    }  
    /** 
     * cancels out of modify part menu and confirms to return to main menu 
     */
    @FXML
    private void onCancelModifyPart(ActionEvent event) throws IOException {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to cancel and return to main menu?");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                 returnToMenu(event);
            }
    }


    /** 
     * sets partsLabel to Machine ID if inHouse button selected 
     */
    @FXML
    private void onInHouseButton(ActionEvent event) {
        partsLabel.setText("Machine ID");
    }
    /** 
     * sets partsLabel to Company Name if outSourced button selected 
     */
    @FXML
    private void onOutSourcedButton(ActionEvent event) {
        partsLabel.setText("Company Name");
    }
    
    /** populates the text fields with the selectedPart values
     * 
     * ID field is disabled and auto-entered with selectedPart ID
     * 
     * places modified part back into table view where was previously entered
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedPart = MainController.getSelectedPart();

        if (selectedPart instanceof inHouse) {
            inHouseButton.setSelected(true);
            partsLabel.setText("Machine ID");
            addPartMachineCompany.setText(String.valueOf(((inHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof outSourced){
            outSourcedButton.setSelected(true);
            partsLabel.setText("Company Name");
            addPartMachineCompany.setText(((outSourced) selectedPart).getCompanyName());
        }
        
        
        partId = Inventory.getAllParts().indexOf(selectedPart);

        addPartId.setText(String.valueOf(selectedPart.getId()));
        addPartName.setText(selectedPart.getName());
        addPartInv.setText(String.valueOf(selectedPart.getStock()));
        addPartPrice.setText(String.valueOf(selectedPart.getPrice()));
        addPartMax.setText(String.valueOf(selectedPart.getMax()));
        addPartMin.setText(String.valueOf(selectedPart.getMin()));
    }
}

