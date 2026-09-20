package com.DAOs;

import com.DAOs.Models.Tax;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class TaxDAOImpl implements TaxDAO {

    //The string has the State Abbreviation
    HashMap<String, Tax> taxMap;

    public void loadFile() throws FileNotFoundException {
        taxMap = new HashMap<String, Tax>();
        File file = new File("SampleFileData/Data/Taxes.txt");

        Scanner scan = new Scanner(file);

        while (scan.hasNext()){
            String[] parsed = scan.nextLine().split("::");

            Tax temp = new Tax(parsed[1], parsed[0], new BigDecimal(parsed[2]));
            taxMap.put(temp.getStateAbr(), temp);
        }
    }
    public HashMap<String, Tax> getAllTaxes(){
        return taxMap;
    }
}
