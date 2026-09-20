package com.DAOs.Models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private LocalDate date;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal area;
    private BigDecimal labourCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(){}

    public Order(Order other){
        orderNumber = other.getOrderNumber();
        customerName = other.getCustomerName();
        state = other.getState();
        date = other.getDate();
        taxRate = other.getTaxRate();
        productType = other.getProductType();
        costPerSquareFoot = other.getCostPerSquareFoot();
        labourCostPerSquareFoot = other.getLabourCostPerSquareFoot();
        materialCost = other.getMaterialCost();
        area = other.getArea();
        labourCost = other.getLabourCost();
        tax = other.getTax();
        total = other.getTotal();
    }



    public boolean equalTo(Order other){
        if (orderNumber == other.getOrderNumber() &&
                Objects.equals(customerName, other.getCustomerName()) &&
                Objects.equals(state, other.getState()) &&
                date.equals(other.getDate())&&
                Objects.equals(taxRate, other.getTaxRate()) &&
                Objects.equals(productType, other.getProductType()) &&
                Objects.equals(costPerSquareFoot, other.getCostPerSquareFoot()) &&
                Objects.equals(labourCostPerSquareFoot, other.getLabourCostPerSquareFoot()) &&
                Objects.equals(materialCost, other.getMaterialCost()) &&
                Objects.equals(area, other.getArea()) &&
                Objects.equals(labourCost, other.getLabourCost()) &&
                Objects.equals(tax, other.getTax()) &&
                Objects.equals(total, other.getTotal())){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return orderNumber + "::" + customerName + "::" + state + "::" + taxRate + "::" + productType + "::" + area + "::" + costPerSquareFoot + "::" + labourCostPerSquareFoot + "::" + materialCost + "::" + labourCost + "::" + tax + "::" + total;
    }

    public void calculate(){
        labourCost = labourCostPerSquareFoot.multiply(area);
        materialCost = costPerSquareFoot.multiply(area);
        tax = (materialCost.add(labourCost)).multiply((taxRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)));
        total = labourCost.add(materialCost.add(tax));
    }

    public BigDecimal getLabourCostPerSquareFoot() {
        return labourCostPerSquareFoot;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductType() {
        return productType;
    }

    public String getState() {
        return state;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLabourCost(BigDecimal labourCost) {
        this.labourCost = labourCost;
    }

    public void setLabourCostPerSquareFoot(BigDecimal labourCostPerSquareFoot) {
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
