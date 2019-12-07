package com.YTeam.cinema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class appController {
    private PSQLConnection connection = new PSQLConnection();
    private Statement stat=connection.getConnection().createStatement();



    public appController() throws SQLException {
    }

    @GetMapping("/afisha")
    public String getAfisha(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "WEB-INF/pages/afisha";
    }

    @PostMapping("/afisha")
    public String postAfisha(
            @RequestBody Member member
    ) {
        /*overload*/
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/")
    public String getAuth(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestParam(name="shedule_id", required=false, defaultValue="18") int shedule_id,
            Map<String, Object> model
    ) throws SQLException {
        String q="select id,shedule_id,plase_number,row_number,price,state from ticket  where shedule_id="+shedule_id;
        ArrayList<ticket> ticketList=new ArrayList<ticket>();
        ResultSet rs= stat.executeQuery(q);
        while (rs.next()){
            ticketList.add(
                           new ticket(
                                        rs.getInt(1),
                                        rs.getInt(2),
                                        rs.getInt(3),
                                        rs.getInt(4),
                                        rs.getInt(5),
                                        rs.getInt(6)
                                      )
                            );
        }
//
//        ArrayList<Integer> selectedTicket= new ArrayList<Integer>();
//        selectedTicket.add(ticket_id);
//        String ticketPurchase="update into ticket set state=1 where id="+ ticket_id;
//        stat.executeUpdate(ticketPurchase);
//        String ticketReturn="";
//        stat.executeUpdate(ticketReturn);
        model.put("name", ticketList);
        return "WEB-INF/pages/SeetSelection";
    }


    @PostMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestBody Integer[] ticket_id
    ) throws SQLException {
        String q="";
        for (Integer id:ticket_id
             ) {
            if(q.length()==0) q="update into ticket set state=1 where id="+id;
            else q+=" or id="+id;
        }

        stat.executeUpdate(q);
        return "WEB_INF/pages/receipt";
    }
}