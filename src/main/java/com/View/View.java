package com.View;

import com.DAOs.Models.Order;
import com.DAOs.Models.Product;
import com.DAOs.Models.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public interface View {
    public int mainMenu() throws InvalidInputException;

    public String getCustomerName();
    public String getState(ArrayList<Tax> states);
    public String getStateNotEmpty(ArrayList<Tax> states);
    public String getProductType(ArrayList<Product> products);
    public int getOrderNum();
    public BigDecimal getArea();
    public LocalDate getDateInput();
    public LocalDate getFutureDateInput();
    public void displayAllOrders(ArrayList<Order> orders);

    public void displayOrder(Order order);

    public void displayStates(ArrayList<Tax> states);

    public boolean askForConfirm();

    public void showHowToNotChange();



    public String getCustomerNameNotEmpty();

    public String getProductTypeNotEmpty(ArrayList<Product> values);

    public BigDecimal getAreaNotEmpty();

    public void saveMessage();

    public void exitMessage();

    public void print(String message);
}
