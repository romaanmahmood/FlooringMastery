package com;

import com.DAOs.Models.Order;
import com.DAOs.Models.Product;
import com.DAOs.Models.Tax;
import com.DAOs.OrderDAO;
import com.DAOs.ProductDAO;
import com.DAOs.TaxDAO;
import com.Service.PersistenceException;
import com.Service.Service;
import com.View.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Controller {

    Service service;
    View view;

    public Controller(Service service, View view){
        this.service = service;
        this.view = view;

    }

    //START HERE
    public void run() throws IOException {

        mainMenu();

        // I don't know why I didn't just put the main menu here, but oh well
    }



    private void mainMenu() throws IOException {
        boolean run = true;
        while (run){
            int user = view.mainMenu();

            if (user == 1){
                displayOrders();
            }
            else if (user == 2){
                addOrders();
            }
            else if (user == 3){
                editOrders();
            }
            else if (user == 4){
                removeOrder();
            }
            else if (user == 5){
                exportAllData();
            }
            //The view object checks for invalid inputs
            else{
                //Quit
                run = false;
                view.exitMessage();
            }


        }
    }

    private void exportAllData() throws IOException, PersistenceException {
        try {
            service.export();
        }catch (PersistenceException e){
            view.print(e.getMessage());
        }
    }


    private void removeOrder() throws IOException {
        Order order = getUserOrder();

        view.displayOrder(order);

        if (view.askForConfirm()){
            service.removeOrder(order.getOrderNumber());
            save();
        }

    }

    //This is run anytime a change is made (after confirming with the user)
    private void save() throws IOException {
        service.save();
        view.saveMessage();
    }

    private Order getUserOrder() throws FileNotFoundException {

        while (true) {
            loadDate();

            int orderNum = view.getOrderNum();
            Order order = service.validateOrder(orderNum);
            if (order != null){
                return order;
            }
        }

    }

    private void editOrders() throws IOException {
        boolean loop = true;

        boolean changed = false;

        Order newOrder = null;
        Order oldOrder = null;
        int orderNum = -1;

        oldOrder = getUserOrder();
        newOrder = new Order(oldOrder);

        newOrder = new Order(oldOrder);

        view.displayOrder(oldOrder);

        view.showHowToNotChange();
        String name = view.getCustomerName();
        if (!name.isEmpty()){
            newOrder.setCustomerName(name);
            changed = true;
        }

        view.showHowToNotChange();
        String state = view.getState(new ArrayList<>(service.getTaxes().values()));
        if (!state.isEmpty()){
            newOrder.setState(state);
            changed = true;
        }

        view.showHowToNotChange();
        String productType = view.getProductType(new ArrayList<>(service.getProducts().values()));
        if (!productType.isEmpty()){
            newOrder.setProductType(productType);
            changed = true;
        }

        view.showHowToNotChange();
        BigDecimal area = view.getArea();
        if (area != null){
            newOrder.setArea(area);
            changed = true;
        }

        if (changed) {
            newOrder.calculate();


            view.displayOrder(newOrder);

            if (view.askForConfirm()) {
                service.editOrder(orderNum, newOrder);
                save();
            }
        }

    }

    private void displayOrders() throws FileNotFoundException {
        loadDate();
        ArrayList<Order> orderList = new ArrayList<>(service.getOrders().values());
        view.displayAllOrders(orderList);
    }

    private void addOrders() throws IOException {
        loadFutureDate();

        String customerName = view.getCustomerNameNotEmpty();

        ArrayList<Tax> taxList = new ArrayList<>(service.getTaxes().values());
        view.displayStates(taxList);
        String state = view.getStateNotEmpty(taxList);


        String productType = view.getProductTypeNotEmpty(new ArrayList<>(service.getProducts().values()));

        BigDecimal area = view.getAreaNotEmpty();

        Order order = service.constructOrder(customerName, state, productType, area);

        order.setOrderNumber(service.getNextOrderNumber());

        view.displayOrder(order);

        boolean conf = view.askForConfirm();

        if (conf){
            service.addOrder(order);
            save();
        }
    }



    //Forces the user to input a date in the future
    private void loadFutureDate() throws FileNotFoundException {
        service.loadOrders(view.getFutureDateInput());
    }

    private void loadDate() throws FileNotFoundException {
        service.loadOrders(view.getDateInput());
    }


}
