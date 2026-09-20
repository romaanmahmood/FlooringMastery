package com.View;

import java.util.Scanner;

public class ConsoleIO implements UserIO{
    Scanner scan = new Scanner(System.in);
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString() {

        return scan.nextLine();

    }

    //@Override
    //public int readInt() {
       // return 0;
   // }
}
