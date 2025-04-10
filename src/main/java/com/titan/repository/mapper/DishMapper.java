package com.titan.repository.mapper;

import com.titan.repository.dao.DishIngredientDAO;
import com.titan.model.entities.Dish;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishMapper implements Function<ResultSet, Dish> {
    private final DishIngredientDAO dishIngredientDAO;

    @Override
    @SneakyThrows
    public Dish apply(ResultSet rs) {
        Dish dish = new Dish();
        long dishId = rs.getLong("dish_id");

        dish.setDishId(dishId);
        dish.setDishName(rs.getString("dish_name"));
        dish.setDishPrice(rs.getLong("dish_price"));
        dish.setIngredientList(dishIngredientDAO.getIngredientsForDish(dishId));

        return dish;
    }
}
