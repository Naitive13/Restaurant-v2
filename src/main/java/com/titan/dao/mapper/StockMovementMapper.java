package com.titan.dao.mapper;

import com.titan.entities.StockMovement;
import com.titan.entities.enums.StockType;
import com.titan.entities.enums.UnitType;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class StockMovementMapper implements Function<ResultSet, StockMovement> {
  @Override
  @SneakyThrows
  public StockMovement apply(ResultSet rs) {
    StockMovement stockMovement = new StockMovement();

    stockMovement.setId(rs.getLong("stock_id"));
    stockMovement.setIngredientId(rs.getLong("ingredient_id"));
    stockMovement.setQuantity(rs.getDouble("quantity"));
    stockMovement.setLastModified(rs.getTimestamp("last_modified").toLocalDateTime());
    stockMovement.setType(StockType.valueOf(rs.getString("movement")));

    return stockMovement;
  }
}
