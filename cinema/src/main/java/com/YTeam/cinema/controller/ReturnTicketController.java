package com.YTeam.cinema.controller;

import com.YTeam.cinema.FilmSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class ReturnTicketController {
    private PSQLConnection connection ;
    private Statement stat;

    public ReturnTicketController() throws SQLException {
        this.connection = new PSQLConnection();
        this.stat = connection.getConnection().createStatement();
    }

    @GetMapping("/returnTicket")
    public String getAfisha(
            Map<String, Object> model
    ) {
        return "WEB-INF/pages/returnTicket";
    }

    @PostMapping("/returnTicket")
    public ModelAndView postAfisha(
            @ModelAttribute("id") Integer id
    ) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/returnTicket");
        String q="select return_ticket("+id+")";

        ResultSet rs= stat.executeQuery(q);
        rs.next();
        if (rs.getInt(1) == 0){

            modelAndView.addObject("state","Билет был возвращен!");
            return modelAndView;
        }

        if (rs.getInt(1) == 5){

            modelAndView.addObject("state","Билет не может быть возвращен!");
            return modelAndView;
        }
//        modelAndView.addObject("state","Данный билет не может быть возвращен! \n Обратитесь к администратору");
        return modelAndView;
    }


}
