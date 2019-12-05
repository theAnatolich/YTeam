package com.YTeam.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.logging.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Application {
    /*wifi 192.168.0.107
      cable 192.168.0.103
    * */
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/cinema";
    static final String USER = "postgres";
    static final String PASS = "00000000";
    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(DB_URL,USER,PASS)) {
            SpringApplication.run(Application.class, args);
            Statement stat=connection.createStatement();
//            stat.executeUpdate("INSERT INTO HALL (ID,NAME,HALL_TYPE_ID) values (1,'HALL1',1)");
//            ResultSet rs= stat.executeQuery("select * from hall");
//            while (rs.next()) {
//
//                System.out.println("HALL_ID : " + rs.getString("ID"));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }
}
