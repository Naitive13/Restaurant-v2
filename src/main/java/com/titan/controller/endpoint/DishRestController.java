package com.titan.controller.endpoint;

import com.titan.controller.mapper.DishRestMapper;
import com.titan.model.entities.Dish;
import com.titan.model.rest.DishRest;
import com.titan.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;
    private final DishRestMapper dishRestMapper;

    @GetMapping("/dishes")
    public ResponseEntity<Object> getAllDishes(@RequestParam int page, @RequestParam int pageSize){
        List<Dish> dishes = dishService.getAllDishes(page,pageSize);
        List<DishRest> dishRests = dishes.stream().map(dishRestMapper::toRest).toList();
        return ResponseEntity.ok().body(dishRests);
    }
}
