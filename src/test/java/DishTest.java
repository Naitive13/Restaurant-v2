import com.titan.dao.DishDAO;
import com.titan.entities.Dish;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(OrderAnnotation.class)
public class DishTest {
  private DishDAO subject = new DishDAO();

  @Test
  @Order(1)
  public void get_all_dishes() {
    List<Dish> dishes = subject.getAll(1,10);
    assertFalse(dishes.isEmpty());
  }

  @Test
  @Order(2)
  public void get_one_dish(){
    Dish dish = subject.getById(1L);
    assertEquals("hotdog", dish.getDishName());
  }

  @Test
  @Order(3)
  public void get_ingredients_cost(){
    Dish dish = subject.getById(1L);
    assertEquals(5500, dish.getIngredientsCost());
  }

}
