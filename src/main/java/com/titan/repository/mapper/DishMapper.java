package com.titan.repository.mapper;

import com.titan.repository.dao.DishIngredientDAO;
import com.titan.model.entities.Dish;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class DishMapper implements Function<ResultSet, Dish> {
    @Override
    @SneakyThrows
    public Dish apply(ResultSet rs) {
        DishIngredientDAO dishIngredientDAO = new DishIngredientDAO();
        Dish dish = new Dish();
        long dishId = rs.getLong("dish_id");

        dish.setDishId(dishId);
        dish.setDishName(rs.getString("dish_name"));
        dish.setDishPrice(rs.getLong("dish_price"));
        dish.setIngredientList(dishIngredientDAO.getIngredientsForDish(dishId));

        return dish;
    }
}
