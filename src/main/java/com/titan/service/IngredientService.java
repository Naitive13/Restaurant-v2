package com.titan.service;

import com.titan.model.entities.Ingredient;
import com.titan.repository.dao.IngredientDAO;
import com.titan.service.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
  private final IngredientDAO ingredientDAO;

  public List<Ingredient> getIngredientsByPrices(
      Double priceMinFilter, Double priceMaxFilter, int page, int pageSize) {
    validatePrice(priceMinFilter, priceMaxFilter);
    validatePagination(page, pageSize);

    List<Ingredient> ingredients = ingredientDAO.getAll(page, pageSize);

    return ingredients.stream()
        .filter(
            ingredient -> {
              double unitPrice = ingredient.getActualPrice();
              return unitPrice >= priceMinFilter && unitPrice <= priceMaxFilter;
            })
        .toList();
  }

  public List<Ingredient> getAllIngredients(int page, int pageSize) {
    validatePagination(page, pageSize);
    return ingredientDAO.getAll(page, pageSize);
  }

  public Ingredient getIngredientById(Long id) {
    return ingredientDAO.getById(id);
  }

  public void createIngredients(List<Ingredient> ingredients) {
    ingredientDAO.saveAll(ingredients);
  }

  private void validatePagination(int page, int pageSize) {
    if (page < 1) {
      throw new ClientException("Page number must be at least 1. Provided value: " + page);
    }
    if (pageSize < 1) {
      throw new ClientException("Page size must be at least 1. Provided value: " + pageSize);
    }
  }

  private void validatePrice(Double priceMinFilter, Double priceMaxFilter) {
    if (priceMinFilter == null || priceMaxFilter == null) {
      throw new ClientException(
          "You must use priceMinFilter and priceMaxFilter together but not individually");
    }
    if (priceMinFilter < 0) {
      throw new ClientException("PriceMinFilter " + priceMinFilter + " is negative");
    }
    if (priceMaxFilter < 0) {
      throw new ClientException("PriceMaxFilter " + priceMaxFilter + " is negative");
    }
    if (priceMinFilter > priceMaxFilter) {
      throw new ClientException(
          "PriceMinFilter " + priceMinFilter + " is greater than PriceMaxFilter " + priceMaxFilter);
    }
  }
}
