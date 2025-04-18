package com.titan.controller.endpoint;

import com.titan.controller.mapper.DishIngredientRestMapper;
import com.titan.controller.mapper.DishOrderRestWithStatusListMapper;
import com.titan.controller.mapper.DishRestMapper;
import com.titan.model.entities.Dish;
import com.titan.model.entities.DishIngredient;
import com.titan.model.entities.DishOrder;
import com.titan.model.rest.DishIngredientRest;
import com.titan.model.rest.DishOrderRestWithStatusList;
import com.titan.model.rest.DishRest;
import com.titan.service.DishService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DishRestController {
  private final DishService dishService;
  private final DishRestMapper dishRestMapper;
  private final DishIngredientRestMapper dishIngredientRestMapper;
  private final DishOrderRestWithStatusListMapper dishOrderRestWithStatusListMapper;

  @GetMapping("/dishes")
  public ResponseEntity<Object> getAllDishes(@RequestParam int page, @RequestParam int pageSize) {
    List<Dish> dishes = dishService.getAllDishes(page, pageSize);
    List<DishRest> dishRests = dishes.stream().map(dishRestMapper::toRest).toList();
    return ResponseEntity.ok().body(dishRests);
  }

@GetMapping("/dishOrders")
public ResponseEntity<Object> getAllDishOrders(@RequestParam int page, @RequestParam int pageSize) {
        List<DishOrder> dishes = dishService.getAllDishOrder(page, pageSize);
        List<DishOrderRestWithStatusList> dishRest = dishes.stream().map(dishOrderRestWithStatusListMapper).toList();
        return ResponseEntity.ok().body(dishRest);
    }

  @PutMapping("/dishes/{id}/ingredients")
  public ResponseEntity<Object> updateDishIngredients(
      @PathVariable Long id, @RequestBody List<DishIngredientRest> dishIngredientsToAdd) {
    List<DishIngredient> dishIngredients =
        dishIngredientsToAdd.stream()
            .map(
                dishIngredientRest -> {
                  DishIngredient dishIngredient =
                      dishIngredientRestMapper.toModel(dishIngredientRest);
                  dishIngredient.setDishId(id);
                  return dishIngredient;
                })
            .toList();

    dishService.createOrUpdateDishIngredients(dishIngredients);
    return ResponseEntity.ok().body(dishIngredientsToAdd);
  }
}
