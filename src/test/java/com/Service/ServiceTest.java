package com.Service;

import com.DAOs.Models.Order;
import com.DAOs.Models.Tax;
import com.DAOs.OrderDAOImpl;
import com.DAOs.ProductDaoImpl;
import com.DAOs.TaxDAOImpl;
import jdk.jshell.Snippet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    Service service = new ServiceImpl(new OrderDAOImpl(), new ProductDaoImpl(), new TaxDAOImpl());

    ServiceTest() throws FileNotFoundException {
        service.loadOrders(LocalDate.parse("10/10/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Test
    public void getOrderTest() throws FileNotFoundException {
        Order sampleOrder = getSampleOrder();
        service.addOrder(sampleOrder);

        assert(service.getOrder(sampleOrder.getOrderNumber()).equalTo(sampleOrder));
    }

    @Test
    public void loadOrderTest() throws FileNotFoundException {
        service.loadOrders(LocalDate.parse("06/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        assert(!service.getOrders().isEmpty());
    }

    @Test
    public void loadEmptyTest() throws FileNotFoundException{
        service.loadOrders(LocalDate.parse("10/10/2999", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assert(service.getOrders().isEmpty());
    }





    public Order getSampleOrder() throws FileNotFoundException {
        Order order = new Order();
        order.setCustomerName("customerName");
        order.setDate(LocalDate.parse("10/10/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        order.setProductType("carpet");
        order.setArea(new BigDecimal("100"));
        order.setState("CA");
        Tax tax = new Tax("Calfornia", "CA", new BigDecimal("12"));
        order.setTaxRate(tax.getTaxRate());

        order.setCostPerSquareFoot(new BigDecimal("12"));
        order.setLabourCostPerSquareFoot(new BigDecimal("12"));

        order.calculate();
        order.setOrderNumber(1);
        //currentOrderNum++;
        return order;
    }

}