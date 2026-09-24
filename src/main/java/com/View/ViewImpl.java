package com.View;

import com.DAOs.Models.Order;
import com.DAOs.Models.Product;
import com.DAOs.Models.Tax;


import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class ViewImpl implements View{
    UserIO io;

    public ViewImpl(UserIO io){
        this.io = io;
    }


    @Override
    public String getStateNotEmpty(ArrayList<Tax> states){
        while(true) {
            try {
                String state = getState(states);
                if (!state.isEmpty()) {
                    return state;
                }

                throw new InvalidInputException("Invalid Input!");
            }catch (InvalidInputException e){
                io.print(e.getMessage());
            }

        }
    }

    @Override
    public String getState(ArrayList<Tax> states){
        while (true) {
            displayStates(states);
            io.print("Enter State:");
            String user = io.readString();
            if (user.isEmpty()){
                return user;
            }

            try {
                for (Tax state : states) {
                    if (state.getState().equalsIgnoreCase(user) || state.getStateAbr().equalsIgnoreCase(user)) {
                        return state.getStateAbr();
                        //return user;
                    }
                }
                throw new InvalidInputException("Invalid input!");
            } catch (InvalidInputException e){
                io.print(e.getMessage());
            }
        }
    }

    @Override
    public void displayStates(ArrayList<Tax> states) {
        io.print("Available states:");
        for (Tax state : states){
            io.print("-----------------------------------");
            io.print("State Name: " + state.getState());
            io.print("State Abbreviation: " + state.getStateAbr());
            io.print("State tax rate: " + state.getTaxRate());
            io.print("");
        }
    }

    @Override
    public String getProductTypeNotEmpty(ArrayList<Product> products){
        while (true) {
            String user = getProductType(products);
            try {
                if (!user.isEmpty()) {
                    return user;
                }
                throw new InvalidInputException("Input must not be empty!");
            }catch (InvalidInputException e){
                io.print(e.getMessage());
            }
        }
    }
    @Override
    public String getProductType(ArrayList<Product> products) {
        while (true) {
            displayAllProducts(products);

            try{
                io.print("Enter Product: ");
                String user = io.readString();

                if (user.isEmpty()){
                    return user;
                }

                for(Product p : products) {
                    if (user.equalsIgnoreCase(p.getType())) {
                        return p.getType();
                    }
               }
               throw new InvalidInputException("Invalid Input!");
            } catch (InvalidInputException e) {
                io.print(e.getMessage());
            }
        }

    }
    private void displayAllProducts(ArrayList<Product> products){
        io.print("Available Products: ");

        //Lambda reduces code size
        //Powerful with streams
        //Good for filtering, finding max number, look into built-in functions
        products.forEach((p)->io.print(p.toString()));
    }

    @Override
    public void exitMessage(){
        io.print("Thank you for using this program!");
        io.print("Exiting...");
    }

    @Override
    public void print(String message) {
        io.print(message);
    }

    @Override
    public int mainMenu(){
        while (true){
            displayMainMenu();
            String user = io.readString();
            if (validateUserInt(user)){
                try{
                    int num = Integer.parseInt(user);
                    if (num >= 1 && num <= 6) {
                        return num;
                    }
                    else{
                        throw new InvalidInputException("Invalid Input!");
                    }
                }catch (InvalidInputException e){
                    io.print(e.getMessage());
                }
            }
        }
    }

    @Override
    public String getCustomerNameNotEmpty(){
        while (true) {
            String user = getCustomerName();
            try{
                if (user.isEmpty()){
                    throw new InvalidInputException("Invalid input! Must not be empty.");
                }
                return user;

            }catch (InvalidInputException e){
                io.print(e.getMessage());
            }
        }

    }

    @Override
    public void showHowToNotChange(){
        io.print("Press Enter without typing anything to not change this value.");
    }

    @Override
    public String getCustomerName() {
        boolean loop = true;
        String name = "";
        while (true) {
            io.print("Enter Customer Name");

            name = io.readString();

            if (name.isEmpty()){
                return name;
            }

            try {
                for (char c : name.toCharArray()) {
                    if (!(Character.isLetter(c) || Character.isDigit(c) || c == '.' || c == ',')) {

                        throw new InvalidInputException("Invalid Input! Only letters, numbers, commas and full-stops are valid.");
                    }
                }
                return name;
            }
            catch (InvalidInputException e){
                io.print(e.getMessage());
            }
        }

    }

    private void displayMainMenu(){
        io.print("------------------------");
        io.print("Main Menu:");
        io.print("1: Display Orders");
        io.print("2: Add Order");
        io.print("3: Edit an Order");
        io.print("4: Remove an Order");
        io.print("5: Export All Data");
        io.print("6: Quit");
        io.print("Enter Choice:");
    }

    @Override
    public int getOrderNum() {
        while (true) {
            io.print("Enter Order Number:");
            String user = io.readString();
            if (validateUserInt(user)){
                return Integer.parseInt(user);
            }
        }
    }

    @Override
    public BigDecimal getAreaNotEmpty(){
        BigDecimal number = null;
        while (true) {
            io.print("Enter Area: ");

            try {
                number = new BigDecimal(io.readString());

                if (number.compareTo(new BigDecimal(100)) >= 0) {
                    return number;
                }
                throw new InvalidInputException("Number must be equal to or greater than 100!");
            }catch (Exception _) {
                io.print("Invalid Input!");
            }
        }
    }

    @Override
    //Will return null when user input is empty
    public BigDecimal getArea() {
        BigDecimal number = null;
        while (true) {
            io.print("Enter Area: ");

            try {
                String user = io.readString();
                if (user.isEmpty()){
                    return null;
                }
                number = new BigDecimal(user);



                if (number.compareTo(new BigDecimal(100)) >= 0) {
                    return number;
                }
                throw new InvalidInputException("Number must be equal to or greater than 100!");
            }catch (Exception _) {
                io.print("Invalid Input!");
            }
        }
    }

    @Override
    public void saveMessage(){
        io.print("Data has been saved!");
    }

    @Override
    public boolean askForConfirm(){
        while (true) {
            try {
                io.print("Would you like to proceed? (y/n)");

                String user = io.readString();
                if (user.equalsIgnoreCase("y")) {
                    return true;
                } else if (user.equalsIgnoreCase("n")) {
                    return false;
                }
                throw new InvalidInputException("Invalid Input!");
            }catch (InvalidInputException e){
                io.print(e.getMessage());
            }
        }
    }

    private boolean validateUserInt(String parse){
        try{
            int num = Integer.parseInt(parse);
            return true;
        }
        catch(Exception e){
            io.print("Invalid input!");
            return false;}
    }

    @Override
    public LocalDate getDateInput(){

        while (true) {
            try{
                io.print("Enter the date you would like to load (dd/mm/yyyy)");
                LocalDate date = LocalDate.parse(io.readString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return date;
            } catch (Exception e){
                io.print("Date formatted incorrectly!");
            }
        }
    }
    @Override
    public LocalDate getFutureDateInput() throws InvalidInputException{
        while (true) {
            try {
                LocalDate date = getDateInput();
                if (date.isAfter(LocalDate.now())) {
                    return date;
                }
                else{

                    throw new InvalidInputException("Date must be in the future!");
                }

            }catch (InvalidInputException e){
                io.print(e.getMessage());
            }

        }
    }



    @Override
    public void displayAllOrders(ArrayList<Order> orders) {
        if (orders.isEmpty()){
            io.print("No orders have been placed on that date!");
        }
        for (Order order : orders){
            displayOrder(order);
        }
    }

    @Override
    public void displayOrder(Order order) {
        io.print("----------------------------------------------");
        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate());
        io.print("Product Type: " + order.getProductType());
        io.print("Cost Per Square Foot: " + order.getCostPerSquareFoot());
        io.print("Labour Cost Per Square Foot: " + order.getLabourCostPerSquareFoot());
        io.print("Material Cost: " + order.getMaterialCost());
        io.print("Area: " + order.getArea());
        io.print("Labour Cost: " + order.getLabourCost());
        io.print("Tax: " + order.getTax());
        io.print("Total Cost: " + order.getTotal());
        io.print("");
    }


}
