package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {

        //ApplicationContext appCon = new ClassPathXmlApplicationContext("classpath:aplicationContext.xml");
        ApplicationContext appCon
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        // Actually returns Object of type Object
        // Controller cannot use Object of type Object
        // 2nd parameter type casts
        Controller control = appCon.getBean("controller", Controller.class);
        control.run();
    }
}
