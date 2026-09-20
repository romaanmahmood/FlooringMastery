package com.Service;

import com.DAOs.Models.Order;
import com.DAOs.Models.Product;
import com.DAOs.Models.Tax;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public interface Service {

    public Order getOrder(int orderNum) throws NoSuchOrderException;

    public int getNextOrderNumber() throws FileNotFoundException;

    public void addOrder(Order order);

    public void loadOrders(LocalDate date) throws FileNotFoundException;

    public void editOrder(int orderNum, Order order);

    public HashMap<Integer, Order> getOrders();

    public Order removeOrder(int orderNum);

    public HashMap<String, Tax> getTaxes();

    public HashMap<String, Product> getProducts();

    public LocalDate getLoadedDate();

    public Tax getTax(String state);

    public Order validateOrder(int orderNum);


    public Order constructOrder(String customerName, String state, String productType, BigDecimal area);

    public void save() throws IOException;

    public void export() throws IOException;
}
