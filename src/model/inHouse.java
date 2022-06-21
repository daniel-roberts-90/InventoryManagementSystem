/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Daniel Roberts
 */
public class inHouse extends Part{
/**
 * machineId for the inHouse parts
 */
    private int machineId;
    
    /** 
     * constructor 
     */
    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /** 
     * get method for machineId 
     */
    public int getMachineId(){
        return machineId;
    }
    /** 
     * set method for machineId 
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

}
