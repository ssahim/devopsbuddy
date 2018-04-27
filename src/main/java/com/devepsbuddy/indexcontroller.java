package com.devepsbuddy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PublicKey;

@Controller
public class indexcontroller {

    @RequestMapping("/")
    public String sayHello(){
        return "index";
    }
}
