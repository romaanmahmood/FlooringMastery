package com.DAOs;

import com.DAOs.Models.Product;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOTest {
    ProductDAO dao = new ProductDaoImpl();
    ProductDAOTest() throws FileNotFoundException {
        dao.loadFile();
    }

    @Test
    public void getAllProductsTest() throws FileNotFoundException {
        HashMap<String, Product> products = dao.getAllProducts();
        File f = new File("SampleFileData/Data/Products.txt");
        Scanner scan = new Scanner(f);
        int i = 0;
        while (scan.hasNextLine()) {
            i++;
            scan.nextLine();
        }
        assert (products.size() == i);
    }

}