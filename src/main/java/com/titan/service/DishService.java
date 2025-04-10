package com.titan.service;

import com.titan.model.entities.Dish;
import com.titan.model.entities.DishIngredient;
import com.titan.repository.dao.DishDAO;
import com.titan.repository.dao.DishIngredientDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishDAO dishDAO;
    private final DishIngredientDAO dishIngredientDAO;

    public List<Dish> getAllDishes(int page, int pageSize) {
        return dishDAO.getAll(page,pageSize);
    }

    public void createOrUpdateDishIngredients(List<DishIngredient> dishIngredients) {
        dishIngredientDAO.saveAll(dishIngredients);
    }
}
