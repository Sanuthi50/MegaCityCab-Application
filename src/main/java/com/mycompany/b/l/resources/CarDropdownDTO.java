/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.resources;

/**
 *
 * @author User
 */
public class CarDropdownDTO {
    private int carID;
    private String displayText;

    // Constructor
    public CarDropdownDTO(int carID, String displayText) {
        this.carID = carID;
        this.displayText = displayText;
    }

    // Getters and setters
    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}  

