package com.titan.repository.mapper;

import com.titan.model.entities.StockMovement;
import com.titan.model.enums.StockType;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
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
