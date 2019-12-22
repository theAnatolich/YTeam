package com.YTeam.cinema.controller;

import com.YTeam.cinema.CheckBuyTicket;
import com.YTeam.cinema.CheckTicket;
import com.YTeam.cinema.Payment;
import com.YTeam.cinema.models.Film;
import com.YTeam.cinema.models.Sit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import postgresql.PSQLConnection;
//import com.google.gson.*;

import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class appController {
    private PSQLConnection connection ;
    private Statement stat;
    public appController() throws SQLException {
        connection = new PSQLConnection();
        stat=connection.getConnection().createStatement();
    }
    public appController(boolean l) {    }
    public appController(PSQLConnection connection, Statement stat) {
        this.connection = connection;
        this.stat = stat;
    }
    public void setConnection(PSQLConnection p) {
        connection=p;
    }
    public void setStat(Statement s) {
        stat=s;
    }

    @GetMapping("/")
    public String getFilms(
            Map<String, Object> model
    ) throws SQLException, ParseException {
        String q="select name, rating, photo, genre, duration, to_char(day,'dd month') as day, age_limit, start_time, shedule_id, film_id from get_films_shedule order by day, start_time";
        ResultSet rs = stat.executeQuery(q);
        Map<String, TreeMap<String, ArrayList<Object>>> days = new TreeMap<>();
        Map<String, Map<String, TreeMap<Integer, String>>> schedule = new TreeMap<>();

        while (rs.next()){
            ArrayList<Object> list = new ArrayList<>();
            list.add(Integer.parseInt(rs.getString(2)));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
            list.add(rs.getString(7));
            list.add(rs.getInt(10));

            String name = rs.getString(1);
            String day = rs.getString(6);
            TreeMap<String, TreeMap<Integer, String>> nameFilm = new TreeMap<>();

            if (!days.containsKey(day) || days.isEmpty()) {
                TreeMap<String, ArrayList<Object>> nameMap = new TreeMap<>();
                nameMap.put(name, list);
                days.put(day, nameMap);
            } else {
                if (!days.get(day).containsKey(name)) {
                    days.get(day).put(name, list);
                }
            }

            if (!schedule.containsKey(day) || schedule.isEmpty()) {
                TreeMap<Integer, String> scheduleId = new TreeMap<>();
                scheduleId.put(rs.getInt(9), rs.getString(8).substring(0, 5));
                nameFilm.put(name, scheduleId);
                schedule.put(day, nameFilm);
            } else {
                try {
                    schedule.get(day).get(name).put(rs.getInt(9), rs.getString(8).substring(0, 5));
                } catch (Exception e) {
                    TreeMap<Integer, String> scheduleId = new TreeMap<>();
                    scheduleId.put(rs.getInt(9), rs.getString(8).substring(0, 5));
                    schedule.get(day).put(name, scheduleId);
                }
            }
        }

        model.put("schedule", schedule);
        model.put("days", days);
        return "WEB-INF/pages/afisha";
    }

    @GetMapping("/login")
    public String login() {
        return "WEB-INF/pages/login";
    }

//    @GetMapping("/addFilm")
//    public String addFilm() {
//        return "WEB-INF/pages/admin";
//    }
//
//    @PostMapping("/addFilm")
//    public ModelAndView postAfisha(@ModelAttribute Film film, Model model) throws SQLException {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("WEB-INF/pages/addFilm");
//        String query="";
//        if(stat.executeQuery(query).getInt(1)==0){
//            modelAndView.addObject("message","Фильм успешно добавлен.");
//        }
//        else {
//            modelAndView.addObject("message","Ошибка добавления");
//        }
//
//        return modelAndView;
//    }

    @GetMapping("/SeetSelection")
    public String getSeetSelection(
            @RequestParam(name="shedule_id", required=false, defaultValue="18") int shedule_id,
            Map<String, Object> model
    ) throws SQLException {
        String getFilmQuery = "select name, photo, day, start_time, age_limit, duration from get_films_shedule where shedule_id="+shedule_id;
        String q="select ID, plase_number,row_number,price,state from ticket  where shedule_id="+shedule_id+" order by row_number,plase_number";
        ArrayList<Sit> sitList = new ArrayList<>();
        ResultSet rs = stat.executeQuery(q);

        while (rs.next()){
            sitList.add(
               new Sit(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
                )
            );
        }

        Map<Integer, HashMap<Integer, ArrayList<Integer>>> sit_num = new HashMap<>();

        for (Sit sit: sitList) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(sit.ID);
            list.add(sit.price);
            list.add(sit.state);

            if (!sit_num.containsKey(sit.row_number) || sit_num.isEmpty()) {
                HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
                map.put(sit.place_number, list);
                sit_num.put(sit.row_number, map);
            } else {
                HashMap<Integer, ArrayList<Integer>> map = sit_num.get(sit.row_number);
                map.put(sit.place_number, list);

                sit_num.put(sit.row_number, map);
            }
        }

        ArrayList<Object> filmParamList = new ArrayList<>();
        ResultSet resultQuery = stat.executeQuery(getFilmQuery);

        while (resultQuery.next()){
            filmParamList.add(resultQuery.getString(1));
            filmParamList.add(resultQuery.getString(2));
            filmParamList.add(resultQuery.getString(3));
            filmParamList.add(resultQuery.getString(4));
            filmParamList.add(Integer.parseInt(String.valueOf(resultQuery.getInt(5))));
            filmParamList.add(resultQuery.getString(6));
        }

        model.put("film", filmParamList);
        model.put("sit_num", sit_num);
        return "WEB-INF/pages/SeetSelection";
    }

    @GetMapping("/description")
    public String getFilmDescription(
            @RequestParam(name="film_id", required=false) int film_id,
            Map<String, Object> model
    ) throws SQLException {
        String getFilmQuery = "select name,photo,age_limit,duration,director,genre,description from get_films_shedule where film_id="+film_id+" limit 1";
        ArrayList<Object> film = new ArrayList<>();
        ResultSet result = stat.executeQuery(getFilmQuery);

        while (result.next()){
            film.add(result.getString(1));
            film.add(result.getString(2));
            film.add(result.getInt(3));
            film.add(result.getInt(4));
            film.add(result.getString(5));
            film.add(result.getString(6));
            film.add(result.getString(7));
        }

        model.put("film", film);
        return "WEB-INF/pages/filmDescription";
    }

//    @GetMapping("/receipt")
//    public String receipt(
//            @RequestParam(name="tic", required=false, defaultValue="") String tickets,
//            Map<String, Object> model
//    ) {
//
//        String[] q=tickets.split("!");
//        for (String i :q
//             ) {
//            model.put("tic"+i, i);
//        }
//        return "WEB-INF/pages/receipt";
//    }

    @PostMapping("/SeetSelection")
    public ModelAndView getSeetSelection(
            @RequestParam("filter") String tickets_id, @RequestParam Map<String, Object> model
    ) throws SQLException, ParseException, InterruptedException {
        Map<String, ArrayList<Object>> map = new HashMap<>();
        String[] q=tickets_id.split("!");
        String selTickets="select t.id,f.name,h.name,t.plase_number,t.row_number,t.price,c.day,c.start_time " +
                            "from ticket t left join shedule s on(t.shedule_id=s.id) " +
                            "left join film f on(s.film_id=f.id) " +
                            "left join hall h on(s.hall_id=h.id) " +
                            "left join calendar c on(s.calendar_id=c.id) " +
                            "where t.id=";
        for (String i : q) {
            String createOrder = "select buy_ticket("+i+")";
            ResultSet order_id=stat.executeQuery(createOrder);
            order_id.next();

            CheckBuyTicket a=new CheckBuyTicket();
            a.op_id=Integer.valueOf(order_id.getInt(1));
            Thread myThready = new Thread(a);
            myThready.start();


            int operationId=order_id.getInt(1);
            ResultSet rs=stat.executeQuery(selTickets+i);
            rs.next();
            ArrayList<Object> ticketList=new ArrayList<>();
            ticketList.add(operationId);
            ticketList.add(rs.getString(2));
            ticketList.add(rs.getString(3));
            ticketList.add(rs.getInt(4));
            ticketList.add(rs.getInt(5));
            ticketList.add(rs.getInt(6));
            ticketList.add(new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(7)));
            ticketList.add(rs.getString(8));

            map.put(i, ticketList);
        }

        if(Payment.ticketPayment())
            for (String i : q) {
                Object a=map.get(i).get(0);
                PSQLConnection.purchaseConfirmation(Integer.valueOf(a.toString()));
            }
        else throw new RuntimeException();


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("WEB-INF/pages/receipt");
        modelAndView.addObject("ticket", map);


        return modelAndView;
    }

//    @PostMapping("/returnTicket")
//    public ModelAndView retutnTicket(
//            @RequestParam("filter") Integer operation_id, @RequestParam Map<String, Object> model
//    ) throws SQLException {
////        String checkReturnVal = "select return_ticket("+operation_id+")";
////        ResultSet order_id=stat.executeQuery(createOrder);
////        order_id.next();
//
//        String createOrder = "select return_ticket("+operation_id+")";
//        ResultSet order_id=stat.executeQuery(createOrder);
//        order_id.next();
//        ModelAndView modelAndView = new ModelAndView();
//       // modelAndView.setViewName("WEB-INF/pages/receipt");
//       // modelAndView.addObject("tic",ticketList);
//        return modelAndView;
//    }
}