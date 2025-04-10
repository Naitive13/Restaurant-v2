package com.titan.repository.mapper;

import com.titan.repository.dao.PriceDAO;
import com.titan.repository.dao.StockDAO;
import com.titan.model.entities.Ingredient;
import com.titan.model.enums.UnitType;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class IngredientMapper implements Function<ResultSet, Ingredient> {
    @Override
    @SneakyThrows
    public Ingredient apply(ResultSet rs) {
        StockDAO stockDAO = new StockDAO();
        PriceDAO priceDAO = new PriceDAO();
        Ingredient ingredient = new Ingredient();
        long ingredientId = rs.getLong("ingredient_id");

        ingredient.setIngredientId(ingredientId);
        ingredient.setIngredientName(rs.getString("ingredient_name"));
        ingredient.setUnit(UnitType.valueOf(rs.getString("unit")));
        ingredient.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
        ingredient.setIngredientPrices(priceDAO.getPricesFor(ingredientId));
        ingredient.setStockMovements(stockDAO.getStockMovementsFor(ingredientId));

        return ingredient;
    }
}
