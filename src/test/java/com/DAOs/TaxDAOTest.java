package com.DAOs;

import com.DAOs.Models.Tax;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TaxDAOTest {
    TaxDAO dao = new TaxDAOImpl();
    TaxDAOTest() throws FileNotFoundException {
        dao.loadFile();
    }

    @Test
    public void getAllTaxesTest() throws FileNotFoundException {
        HashMap<String, Tax> taxes = dao.getAllTaxes();
        File f = new File("SampleFileData/Data/Taxes.txt");
        Scanner scan = new Scanner(f);
        int i = 0;
        while (scan.hasNextLine()) {
            i++;
            scan.nextLine();
        }
        assert (taxes.size() == i);
    }

}