package com.titan.service;

import com.titan.model.entities.Dish;
import com.titan.repository.dao.DishDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishDAO dishDAO;

    public List<Dish> getAllDishes(int page, int pageSize) {
        return dishDAO.getAll(page,pageSize);
    }
}
