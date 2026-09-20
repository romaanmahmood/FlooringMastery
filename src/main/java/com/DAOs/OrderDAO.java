package com.DAOs;

import com.DAOs.Models.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public interface OrderDAO {
    public HashMap<Integer, Order> getAllOrders();
    public void loadOrders(LocalDate date)throws FileNotFoundException;
    public void saveOrders()throws IOException;
    public void addOrder(Order order);
    public Order removeOrder(int orderNum);

    public int getHighestOrderNumber()throws FileNotFoundException;
    public void editOrder(int orderNum, Order order);
    public LocalDate getLoadedDate();

    public void export() throws IOException;
}
