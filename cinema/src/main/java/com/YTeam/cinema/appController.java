package com.YTeam.cinema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import postgresql.PSQLConnection;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    ) throws SQLException, ParseException {
        String q="select * from get_films_shedule";
        ArrayList<FilmSession> FilmSessionList=new ArrayList<FilmSession>();
        ResultSet rs= stat.executeQuery(q);
        while (rs.next()){
            FilmSessionList.add(
                    new FilmSession(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(10),
                            new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(10)),
                            rs.getString(11)
                    )
            );
        }
        model.put("FilmSessionList", FilmSessionList);
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestParam(name="shedule_id", required=false, defaultValue="18") int shedule_id,
            Map<String, Object> model
    ) throws SQLException {
        String q="select id,shedule_id,plase_number,row_number,price,state from ticket  where shedule_id="+shedule_id+"order by row_number,plase_number";
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
        model.put("name", ticketList);
        return "WEB-INF/pages/SeetSelection";
    }

    @GetMapping("/receipt")
    public String receipt(
            @RequestParam(name="tic", required=false, defaultValue="") String tickets,
            Map<String, Object> model
    ) {

        String[] q=tickets.split("!");
        for (String i :q
             ) {
            model.put("tic"+i, i);
        }
        return "WEB-INF/pages/receipt";
    }

    @PostMapping("SeetSelection")
    public String getSeetSelection(
            @RequestParam("filter") String tickets_id, @RequestParam Map<String, Object> model
    ) throws SQLException {

        String[] q=tickets_id.split("!");
        String upStr="update into ticket set state=1 where id=";
//        for (String i :q
//             ) {
//            stat.executeUpdate(upStr+i);
//        }?tic="+tickets_id
        model.put("tic",tickets_id);
        RedirectView redirectView= new RedirectView("/foo",true);

        return "redirect:/receipt?tic="+tickets_id;//на оплату
    }


}