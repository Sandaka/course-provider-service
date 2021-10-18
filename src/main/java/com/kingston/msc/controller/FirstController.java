package com.kingston.msc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 10/18/21
 */

@RestController
@RequestMapping("/cps/employee")
public class FirstController {

    @GetMapping("/message")
    public String test(){
        return "CP service works...";
    }

    @GetMapping("/testvariable/{id}")
    public String testVar(@PathVariable String id){
        return id;
    }
}
