/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.resources;

/**
 *
 * @author User
 */
public class DriverDropdownDTO {

    private Integer driverID;
    private String displayText;

    // Default constructor
    public DriverDropdownDTO(Integer driverID, String displayText) {
        this.driverID = driverID;
        this.displayText = displayText;
    }

    // Getters and Setters
    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}

