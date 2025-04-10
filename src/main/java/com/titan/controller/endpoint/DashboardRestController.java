package com.titan.controller.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardRestController {
    @GetMapping("/bestSales")
    public ResponseEntity<Object> getBestSales(){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
