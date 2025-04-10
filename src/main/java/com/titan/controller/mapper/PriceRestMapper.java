package com.titan.controller.mapper;

import com.titan.model.entities.Price;
import com.titan.model.rest.PriceRest;
import org.springframework.stereotype.Component;

@Component
public class PriceRestMapper implements BiMapper<PriceRest, Price> {
  @Override
  public PriceRest toRest(Price price) {
    PriceRest priceRest = new PriceRest();
    priceRest.setPrice(price.getValue());
    priceRest.setId(price.getId());
    priceRest.setDateValue(price.getDate());

    return priceRest;
  }

  @Override
  public Price toModel(PriceRest priceRest) {
    return null;
  }
}
