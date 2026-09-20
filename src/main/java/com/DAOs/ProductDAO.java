package com.DAOs;

import com.DAOs.Models.Product;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;

public interface ProductDAO {
    public void loadFile()throws FileNotFoundException;
    public HashMap<String, Product> getAllProducts();
    public BigDecimal getCostPerSquareFoot(String productType);
    public BigDecimal getLabourCostPerSquareFoot(String productType);
}
