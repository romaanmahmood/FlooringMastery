package com.DAOs;

import com.DAOs.Models.Tax;

import java.io.FileNotFoundException;
import java.util.HashMap;

public interface TaxDAO {

    public void loadFile()throws FileNotFoundException;
    public HashMap<String, Tax> getAllTaxes();
}
