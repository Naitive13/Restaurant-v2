package com.titan.controller.endpoint;

import com.titan.controller.mapper.IngredientRestMapper;
import com.titan.model.entities.Ingredient;
import com.titan.model.rest.IngredientRest;
import com.titan.service.IngredientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientRestController {
  private final IngredientService ingredientService;
  private final IngredientRestMapper ingredientRestMapper;

  @GetMapping("/ingredients")
  public ResponseEntity<Object> getIngredients(
      @RequestParam(required = false) Double priceMinFilter,
      @RequestParam(required = false) Double priceMaxFilter,
      @RequestParam int page,
      @RequestParam int pageSize) {

    List<Ingredient> ingredients;

    if (priceMinFilter != null || priceMaxFilter != null) {
      ingredients =
          ingredientService.getIngredientsByPrices(priceMinFilter, priceMaxFilter, page, pageSize);
    } else {
      ingredients = ingredientService.getAllIngredients(page, pageSize);
    }

    List<IngredientRest> ingredientRests = ingredients.stream().map(ingredientRestMapper::toRest).toList();
    return ResponseEntity.ok().body(ingredientRests);
  }

  @GetMapping("/ingredients/{id}")
  public ResponseEntity<Object> getIngredientById (@PathVariable Long id){
    Ingredient ingredient = ingredientService.getIngredientById(id);
    IngredientRest ingredientRest = ingredientRestMapper.toRest(ingredient);
    return ResponseEntity.ok().body(ingredientRest);
  }
}
