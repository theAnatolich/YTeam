package com.YTeam.cinema;

import java.util.Random;

public class Payment {

    public static boolean ticketPayment() throws InterruptedException {
        Random random = new Random();
        int i = random.nextInt(10 );
        Thread.sleep(i*1000);
        if (i<1)
            return false;
        return true;
    }
    /*6451*/
}
