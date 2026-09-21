package com.DAOs;

import com.DAOs.Models.Order;
import com.Service.PersistenceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class OrderDAOImpl implements OrderDAO{
    HashMap<Integer, Order> orders;
    LocalDate currentDateLoaded;

    public HashMap<Integer, Order> getAllOrders(){
        return orders;
    }
    public OrderDAOImpl(LocalDate date) throws FileNotFoundException {
        loadOrders(date);
    }
    public OrderDAOImpl(){}

    public String getStringFromInt(int i){
        if (i < 10){
            return "0" + i;
        }
        else{
            return Integer.toString(i);
        }
    }

    public void loadOrders(LocalDate date) throws FileNotFoundException {
        orders = new HashMap<Integer, Order>();
        currentDateLoaded = date;

        //File name format will be "Orders_dd-mm-yyyy.txt"

        File file = new File("samplefiledata/Orders/Orders_" + dateToString(currentDateLoaded) + ".txt");

        if (file.canRead()) {

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String[] parsed = scan.nextLine().split("::");

                Order temp = new Order();
                temp.setOrderNumber(Integer.parseInt(parsed[0]));
                temp.setCustomerName(parsed[1]);
                temp.setState(parsed[2]);
                temp.setTaxRate(new BigDecimal(parsed[3]));
                temp.setProductType(parsed[4]);
                temp.setArea(new BigDecimal(parsed[5]));
                temp.setCostPerSquareFoot(new BigDecimal(parsed[6]));
                temp.setLabourCostPerSquareFoot(new BigDecimal(parsed[7]));
                temp.setMaterialCost(new BigDecimal(parsed[8]));
                temp.setLabourCost(new BigDecimal(parsed[9]));
                temp.setTax(new BigDecimal(parsed[10]));
                temp.setTotal(new BigDecimal(parsed[11]));
                temp.setDate(currentDateLoaded);

                orders.put(temp.getOrderNumber(), temp);


            }
            scan.close();
        }

        file = null;
    }

    @Override
    public LocalDate getLoadedDate() {
        return currentDateLoaded;
    }

    @Override
    public void export() throws IOException {
        try {

            File dir = new File("SampleFileData/Orders");
            FileWriter toWrite = new FileWriter("SampleFileData/Backup/DataExport.txt");
            for (File file : dir.listFiles()) {

                //May include the ".txt"
                String dateStr = file.getName().split("_")[1];
                dateStr = dateStr.split("\\.")[0];

                Scanner scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    toWrite.write(line + "::" + dateStr + "\n");
                }
            }
            toWrite.close();

        }
        catch (IOException _){
            throw new PersistenceException("Unable to save!");
        }
    }

    private String dateToString(LocalDate date){
        int day = date.getDayOfMonth();
        String dayStr = getStringFromInt(day);

        int month = date.getMonthValue();
        String monthStr = getStringFromInt(month);

        int year = date.getYear();
        String yearStr = Integer.toString(year);

        return dayStr + "-" +  monthStr + "-" + yearStr;
    }


    public void saveOrders() throws IOException {

        String fileName = "SampleFileData/Orders/Orders_" + dateToString(currentDateLoaded) + ".txt";

        File f = new File(fileName);
        f.delete();


        if (!orders.isEmpty()){

            FileWriter file = new FileWriter(fileName);
            for (int key : orders.keySet()) {
                file.write(orders.get(key).toString() + "\n");
            }
            file.close();
        }

    }

    public int getHighestOrderNumber() throws FileNotFoundException {

        try {
            File directory = new File("SampleFileData/Orders");

            int max = 0;


            for (File file : directory.listFiles()) {

                Scanner scan = new Scanner(file);
                while (scan.hasNextLine()) {
                    String[] parsed = scan.nextLine().split("::");
                    if (Integer.parseInt(parsed[0]) > max) {
                        max = Integer.parseInt(parsed[0]);
                    }
                }

            }

            return max;
        }catch (FileNotFoundException e){
            return 0;
        }

    }

    public void editOrder(int orderNum, Order order){
        orders.put(orderNum, order);
    }

    public void addOrder(Order order){
        orders.put(order.getOrderNumber(), order);
    }
    public Order removeOrder(int orderNum){
        Order temp = orders.get(orderNum);
        orders.remove(orderNum);
        return temp;
    }
}
