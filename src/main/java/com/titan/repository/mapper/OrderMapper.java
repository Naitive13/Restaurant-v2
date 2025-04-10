package com.titan.repository.mapper;

import com.titan.repository.dao.DishOrderDAO;
import com.titan.repository.dao.OrderStatusDAO;
import com.titan.model.entities.DishOrder;
import com.titan.model.entities.Order;
import com.titan.model.entities.OrderStatus;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

public class OrderMapper implements Function<ResultSet, Order> {
    @Override
    @SneakyThrows
    public Order apply(ResultSet rs) {
        Order order = new Order();
        DishOrderDAO dishOrderDAO = new DishOrderDAO();
        OrderStatusDAO orderStatusDAO = new OrderStatusDAO();

        String reference = rs.getString("order_reference");
        List<DishOrder> dishOrders = dishOrderDAO.getDishOrdersFor(reference);
        List<OrderStatus> orderStatus = orderStatusDAO.getStatusFor(reference);

        order.setReference(reference);
        order.setCreationDate(rs.getTimestamp("creation_date").toLocalDateTime());
        order.setStatusList(orderStatus);
        order.setDishOrders(dishOrders);

        return order;
    }
}
