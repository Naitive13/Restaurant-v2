package com.titan.controller.mapper;

import com.titan.model.entities.StockMovement;
import com.titan.model.rest.StockMovementRest;

public class StockMovementRestMapper implements BiMapper<StockMovementRest, StockMovement> {
  @Override
  public StockMovementRest toRest(StockMovement stockMovement) {
    StockMovementRest stockMovementRest = new StockMovementRest();
    stockMovementRest.setId(stockMovement.getId());
    stockMovementRest.setType(stockMovement.getType());
    stockMovementRest.setQuantity(stockMovement.getQuantity());
    stockMovementRest.setCreationDatetime(stockMovement.getLastModified());

    return stockMovementRest;
  }

  @Override
  public StockMovement toModel(StockMovementRest stockMovementRest) {
    return null;
  }
}
