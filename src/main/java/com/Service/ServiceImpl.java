package com.Service;

import com.DAOs.Models.Order;
import com.DAOs.Models.Product;
import com.DAOs.Models.Tax;
import com.DAOs.OrderDAO;
import com.DAOs.ProductDAO;
import com.DAOs.TaxDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class ServiceImpl implements Service{

    OrderDAO orderDAO;
    ProductDAO productDAO;
    TaxDAO taxDAO;

    ServiceImpl(OrderDAO orderDAO, ProductDAO productDAO, TaxDAO taxDao) throws FileNotFoundException {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.taxDAO = taxDao;

        productDAO.loadFile();
        taxDao.loadFile();

    }

    public Order constructOrder(String customerName, String state, String productType, BigDecimal area){
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setDate(getLoadedDate());
        order.setProductType(productType);
        order.setArea(area);
        order.setState(state);
        Tax tax = getTax(state);
        order.setTaxRate(tax.getTaxRate());

        order.setCostPerSquareFoot(productDAO.getCostPerSquareFoot(productType));
        order.setLabourCostPerSquareFoot(productDAO.getLabourCostPerSquareFoot(productType));

        order.calculate();
        return order;
    }

    @Override
    public void save() throws IOException {
        orderDAO.saveOrders();
    }

    @Override
    public void export() throws IOException, PersistenceException {
        orderDAO.export();
    }



    @Override
    public Tax getTax(String state){
        HashMap<String, Tax> taxes = taxDAO.getAllTaxes();
        return taxes.get(state.toUpperCase());
    }

    @Override
    public LocalDate getLoadedDate(){
        return orderDAO.getLoadedDate();
    }

    @Override
    public void loadOrders(LocalDate date) throws FileNotFoundException {
        orderDAO.loadOrders(date);
    }

    @Override
    public int getNextOrderNumber() throws FileNotFoundException {
        return orderDAO.getHighestOrderNumber() + 1;
    }

    @Override
    public void addOrder(Order order) {
        orderDAO.addOrder(order);
    }

    @Override
    public void editOrder(int orderNum, Order order) {
        if (validateOrder(orderNum) != null) {
            orderDAO.editOrder(orderNum, order);
        }
    }

    @Override
    public Order getOrder(int orderNum) throws NoSuchOrderException{
        return validateOrder(orderNum);
    }

    @Override
    public HashMap<Integer, Order> getOrders() {
        return orderDAO.getAllOrders();
    }

    @Override
    public Order removeOrder(int orderNum) {
        if (validateOrder(orderNum) != null) {
            return orderDAO.removeOrder(orderNum);
        }
        return null;
    }

    @Override
    public Order validateOrder(int orderNum){
        try{
            Order order = orderDAO.getAllOrders().get(orderNum);
            if (order != null) {
                return order;
            }
            else{
                throw new NoSuchOrderException("Order does not exist!");
            }
        }
        catch(NoSuchOrderException e){
            return null;
        }
    }



    @Override
    public HashMap<String, Tax> getTaxes() {
        return taxDAO.getAllTaxes();

    }

    @Override
    public HashMap<String, Product> getProducts() {
        return productDAO.getAllProducts();
    }
}
