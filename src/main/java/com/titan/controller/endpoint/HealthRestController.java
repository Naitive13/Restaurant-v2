package com.titan.controller.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthRestController {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
