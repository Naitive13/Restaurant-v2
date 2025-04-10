package com.titan.controller.endpoint;

import com.titan.model.rest.IngredientRest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientRestController {
  @GetMapping("/ingredients")
  public List<IngredientRest> getIngredients(
      @RequestParam(required = false) Double priceMinFilter,
      @RequestParam(required = false) Double priceMaxFilter,
      @RequestParam int page,
      @RequestParam int pageSize) {


  }
}
