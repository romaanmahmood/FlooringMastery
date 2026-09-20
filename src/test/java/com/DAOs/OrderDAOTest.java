package com.DAOs;

import com.DAOs.Models.Order;
import com.DAOs.Models.Tax;
import com.Service.Service;
import com.Service.ServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {



    OrderDAO dao = new OrderDAOImpl();
    int currentOrderNum = 1;
    OrderDAOTest() throws FileNotFoundException {
        dao = new OrderDAOImpl(LocalDate.parse("06/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        currentOrderNum = dao.getHighestOrderNumber();
    }

    @BeforeEach void startEach() throws FileNotFoundException {
        dao.loadOrders(LocalDate.parse("06/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Test
    public void loadTest() throws FileNotFoundException {

        LocalDate date = LocalDate.parse("06/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);

        assert(!dao.getAllOrders().isEmpty());
    }

    @Test
    public void loadFailedTest() throws FileNotFoundException {
        //This is a valid date, but there's no file
        LocalDate date = LocalDate.parse("10/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);

        assert(dao.getAllOrders().isEmpty());
    }



    //Tests if a file is created when there's no data for a date when the file does not exist
    @Test
    public void emptySaveTest() throws IOException{
        LocalDate date = LocalDate.parse("10/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);
        dao.saveOrders();

        File file = new File("SampleFileData/Orders");
        for (File f: file.listFiles()){
            if (f.getName().equals("Orders_10-02-2013.txt")){
                //Needs to delete the file for repeat tests
                f.delete();
                assert (false);
            }
        }
        assert (true);
    }

    @Test
    public void removeOrderTest() throws IOException {
        LocalDate date = LocalDate.parse("06/01/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);

        Order sample = getSampleObject();
        dao.addOrder(sample);

        dao.removeOrder(sample.getOrderNumber());

        boolean toAssert = false;
        for (Order order: dao.getAllOrders().values()){
            if (order.equals(sample)){
                toAssert=true;
            }
        }
        assert(!toAssert);
    }

    @Test
    public void newOrderNumTest() throws FileNotFoundException {
        boolean passed = (dao.getHighestOrderNumber() == 3);
        assert (passed);
    }

    @Test
    public void ExportTest() throws IOException {
        dao.export();

        File f = new File("SampleFileData/Backup/DataExport.txt");
        Scanner scan = new Scanner(f);
        int x = 0;
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            x++;

        }

        File directory = new File("SampleFileData/Orders");

        int y = 0;


        for (File file : directory.listFiles()) {

            scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String parsed = scan.nextLine();
                y++;
            }
        }

        assert(x==y);
    }

    @Test
    public void addOrderTest() throws IOException {
        LocalDate date = LocalDate.parse("06/01/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);

        Order sample = getSampleObject();
        dao.addOrder(sample);




        boolean toAssert = false;
        for (Order order: dao.getAllOrders().values()){
            if (order.equals(sample)){
                toAssert=true;
            }
        }

        //dao.removeOrder(sample.getOrderNumber());

        assert(toAssert);
    }

    @Test
    public void addOrderTestNewDate() throws IOException {
        LocalDate date = LocalDate.parse("10/02/2014", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);
        Order sample = getSampleObject();

        dao.addOrder(getSampleObject());

       // File del = new File ("SampleFileData/Orders/Orders_10-02-2014.txt");

        boolean toAssert = false;
        for (Order order: dao.getAllOrders().values()){
            if (order.equalTo(sample)){
                toAssert = true;
            }
        }
        assert(toAssert);
    }

    @Test
    public void EditTest() throws FileNotFoundException {
        Order sample = getSampleObject();
        int num = sample.getOrderNumber();
        dao.addOrder(sample);
        Order edit = getSampleObject();
        edit.setCustomerName("Leon");
        dao.editOrder(num, edit);

        assert (dao.getAllOrders().get(num).getCustomerName().equals("Leon"));
    }

    public Order getSampleObject() throws FileNotFoundException {
        Order order = new Order();
        order.setCustomerName("customerName");
        order.setDate(dao.getLoadedDate());
        order.setProductType("carpet");
        order.setArea(new BigDecimal("100"));
        order.setState("CA");
        Tax tax = new Tax("Calfornia", "CA", new BigDecimal("12"));
        order.setTaxRate(tax.getTaxRate());

        order.setCostPerSquareFoot(new BigDecimal("12"));
        order.setLabourCostPerSquareFoot(new BigDecimal("12"));

        order.calculate();
        order.setOrderNumber(dao.getHighestOrderNumber());
        //currentOrderNum++;
        return order;
    }

    //@Test
    public void saveTest() throws IOException {
        LocalDate date = LocalDate.parse("06/02/2013", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dao.loadOrders(date);

        HashMap<Integer, Order> orders = dao.getAllOrders();

        dao.saveOrders();

        dao.loadOrders(date);

        boolean same = true;

        for (Order order : orders.values()){
            try {
                if (!order.equals(dao.getAllOrders().get(order.getOrderNumber()))) {
                    assert (true);
                }
            }
            catch (Exception _){
                assert(false);
            }
        }
        assert(true);
    }

}