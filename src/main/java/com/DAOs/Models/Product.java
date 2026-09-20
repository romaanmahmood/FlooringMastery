package com.DAOs.Models;

import java.math.BigDecimal;

public class Product {
    private String type;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;


    public Product(String type, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot){
        this.type = type;
        this.costPerSquareFoot = costPerSquareFoot;
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    @Override
    public String toString(){
        return "------------------------------------------\n" +
                "Type: " + type + "\n" +
                "Cost Per Square Foot: " + costPerSquareFoot + "\n" +
                "Labour Cost Per Square Foot: " + labourCostPerSquareFoot + "\n";
    }
}
