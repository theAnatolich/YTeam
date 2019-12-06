package com.YTeam.cinema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Member;
import java.util.Map;

@Controller
public class appController {

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
}