package com.billing.software.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sales {
    @GetMapping(path = "/api/sales")
    public String getSalesData(){
        return "sales";
    }
}
