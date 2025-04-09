package com.titan.dao.mapper;

import com.titan.dao.DishDAO;
import com.titan.dao.DishOrderStatusDAO;
import com.titan.entities.DishOrder;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.function.Function;

public class DishOrderMapper implements Function<ResultSet,DishOrder> {
    @Override
    @SneakyThrows
    public DishOrder apply(ResultSet rs) {
        DishOrder dishOrder = new DishOrder();
        DishOrderStatusDAO dishOrderStatusDAO = new DishOrderStatusDAO();
        DishDAO dishDAO = new DishDAO();
        long dishOrderId = rs.getLong("dish_order_id");
        long dishId = rs.getLong("dish_id");

        dishOrder.setId(dishOrderId);
        dishOrder.setOrderReference(rs.getString("order_reference"));
        dishOrder.setDish(dishDAO.getById(dishId));
        dishOrder.setStatusList(dishOrderStatusDAO.getStatusListFor(dishOrderId));
        dishOrder.setQuantity(rs.getInt("quantity"));

        return dishOrder;
    }
}
