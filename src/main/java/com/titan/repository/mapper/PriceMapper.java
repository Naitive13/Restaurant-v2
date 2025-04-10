package com.titan.repository.mapper;

import com.titan.model.entities.Price;
import java.sql.ResultSet;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper implements Function<ResultSet, Price> {
  @Override
  @SneakyThrows
  public Price apply(ResultSet rs) {
    Price price = new Price();

    price.setDate(rs.getTimestamp("price_date").toLocalDateTime());
    price.setValue(rs.getDouble("unit_price"));
    price.setIngredientId(rs.getLong("ingredient_id"));
    price.setId(rs.getLong("price_id"));

    return price;
  }
}
