import static com.titan.model.enums.StatusType.*;
import static org.junit.jupiter.api.Assertions.*;

import com.titan.repository.dao.DishDAO;
import com.titan.repository.dao.OrderDAO;
import com.titan.model.entities.DishOrder;
import com.titan.model.entities.Order;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {
  private final OrderDAO subject = new OrderDAO();
  private final DishDAO dishDAO = new DishDAO();

  @Test
  @org.junit.jupiter.api.Order(1)
  public void get_all_orders() {
    List<Order> orders = subject.getAll(1, 10);
    assertFalse(orders.isEmpty());
  }

  @Test
  @org.junit.jupiter.api.Order(2)
  public void get_one_order() {
    Order order = subject.getByReference("ORD001");
    assertEquals("ORD001", order.getReference());
  }

  @Test
  @org.junit.jupiter.api.Order(3)
  public void save_new_order() {
    Order expected = newOrder();
    Order actual = subject.saveAll(List.of(expected)).getFirst();

    assertEquals(expected, actual);
  }

  @Test
  @org.junit.jupiter.api.Order(4)
  public void get_actual_order_status() {
    Order order = subject.getByReference("ORD002");
    assertEquals(CREATED, order.getActualStatus().getStatus());
  }

  @Test
  @org.junit.jupiter.api.Order(5)
  public void confirm_order() {
    Order order = subject.getByReference("ORD002");
    order.updateStatus();
    assertEquals(CONFIRMED, order.getActualStatus().getStatus());
    assertTrue(
        order.getDishOrders().stream()
            .map(dishOrder -> dishOrder.getActualStatus().getStatus())
            .allMatch(CONFIRMED::equals));
  }

  @Test
  @org.junit.jupiter.api.Order(6)
  public void update_order_status_to_in_progress() {
    Order order = subject.getByReference("ORD002");
    order.updateStatus();
    System.out.println(order.getDishOrders().stream().map(DishOrder::getActualStatus).toList());
    assertEquals(IN_PROGRESS, order.getActualStatus().getStatus());
    assertTrue(
            order.getDishOrders().stream()
                    .map(dishOrder -> dishOrder.getActualStatus().getStatus())
                    .allMatch(IN_PROGRESS::equals));
  }

  @Test
  @org.junit.jupiter.api.Order(7)
  public void update_order_status_to_done() {
    Order order = subject.getByReference("ORD002");
    order.getDishOrders().forEach(DishOrder::updateStatus);
    Order updatedOrder = subject.getByReference("ORD002");
    assertEquals(DONE, updatedOrder.getActualStatus().getStatus());
    assertTrue(
            updatedOrder.getDishOrders().stream()
                    .map(dishOrder -> dishOrder.getActualStatus().getStatus())
                    .allMatch(DONE::equals));
  }

  private Order newOrder() {
    Order order = new Order();
    DishOrder dishOrder = new DishOrder();

    dishOrder.setOrderReference("ORD002");
    dishOrder.setId(2L);
    dishOrder.setQuantity(5);
    dishOrder.setDish(dishDAO.getById(1L));
    dishOrder.setStatusList(List.of());

    order.setReference("ORD002");
    order.setCreationDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    order.setDishOrders(List.of(dishOrder));
    order.setStatusList(List.of());

    return order;
  }
}
