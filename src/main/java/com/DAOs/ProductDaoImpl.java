package com.DAOs;

import com.DAOs.Models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDAO {
    HashMap<String, Product> products;

    public void loadFile() throws FileNotFoundException {
        products = new HashMap<String, Product>();
        File file = new File("SampleFileData/Data/Products.txt");
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()){
            String[] parsed = scan.nextLine().split("::");

            Product temp = new Product(parsed[0], new BigDecimal(parsed[1]), new BigDecimal(parsed[2]));
            products.put(temp.getType(), temp);
        }
    }
    public HashMap<String, Product> getAllProducts(){
        return products;
    }

    public BigDecimal getCostPerSquareFoot(String productType){
        return products.get(productType).getCostPerSquareFoot();
    }
    public BigDecimal getLabourCostPerSquareFoot(String productType){
        return products.get(productType).getLabourCostPerSquareFoot();
    }
}
