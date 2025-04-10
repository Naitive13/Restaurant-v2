package com.titan.repository.mapper;

import com.titan.repository.dao.DishDAO;
import com.titan.repository.dao.DishOrderStatusDAO;
import com.titan.model.entities.DishOrder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DishOrderMapper implements Function<ResultSet,DishOrder> {
    private final DishOrderStatusDAO dishOrderStatusDAO ;
    private final DishDAO dishDAO ;

    @Override
    @SneakyThrows
    public DishOrder apply(ResultSet rs) {
        DishOrder dishOrder = new DishOrder();
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
