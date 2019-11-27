package com.YTeam.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Application {
    /*wifi 192.168.0.107
      cable
    * */
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/cinema";
    static final String USER = "postgres";
    static final String PASS = "00000000";
    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(DB_URL,USER,PASS)) {
            SpringApplication.run(Application.class, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }
}
