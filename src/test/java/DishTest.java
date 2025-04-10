import com.titan.repository.dao.DishDAO;
import com.titan.model.entities.Dish;
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
    assertEquals(5_500, dish.getIngredientsCost());
  }

  @Test
  @Order(4)
  public void get_gross_margin(){
    Dish dish = subject.getById(1L);
    assertEquals(15_000 - 5_500, dish.getGrossMargin());
  }

  @Test
  @Order(5)
  public void get_available_quantity(){
    Dish dish = subject.getById(1L);
    assertEquals(30, dish.getAvailableQuantity());
  }

  @Test
  @Order(6)
  public void add_new_dish(){
    Dish expected = subject.getById(1L);

    List<Dish> dishes = subject.saveAll(List.of(expected));
    Dish actual = dishes.getFirst();

    assertEquals(expected,actual);
  }

}
