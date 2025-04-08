import com.titan.dao.IngredientDAO;
import com.titan.entities.Dish;
import com.titan.entities.Ingredient;
import com.titan.entities.Price;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.util.List;

import static com.titan.entities.enums.UnitType.G;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IngredientTest {
  private final IngredientDAO subject = new IngredientDAO();

  @Test
  @Order(1)
  public void get_all_ingredients() {
    List<Ingredient> ingredients = subject.getAll(1, 10);
    assertFalse(ingredients.isEmpty());
  }

  @Test
  @Order(2)
  public void get_one_ingredient() {
    Ingredient ingredient = subject.getById(1L);
    assertEquals("sausage", ingredient.getIngredientName());
  }

  @Test
  @Order(3)
  public void add_new_ingredients() {
    List<Ingredient> expected = List.of(salt(),rice());

    List<Ingredient> actual = subject.saveAll(expected);

    assertEquals(expected, actual);
  }

  @Test
  @Order(4)
  public void get_available_quantity() {
    Ingredient egg = subject.getById(3);
    Ingredient bread = subject.getById(4);

    assertEquals(80, egg.getAvailableQuantity(), "Available quantity for egg");
    assertEquals(30, bread.getAvailableQuantity(), "Available quantity for bread");
  }

  private Ingredient salt() {
    Price price = new Price();
    price.setId(5);
    price.setIngredientId(5);
    price.setDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    price.setValue(2.5d);

    Ingredient ingredient = new Ingredient();
    ingredient.setIngredientId(5);
    ingredient.setIngredientName("salt");
    ingredient.setUnit(G);
    ingredient.setIngredientPrices(List.of(price));
    ingredient.setStockMovements(List.of());
    ingredient.setLastModified(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

    return ingredient;
  }

  private Ingredient rice() {
    Price price = new Price();
    price.setId(6);
    price.setIngredientId(6);
    price.setDate(LocalDateTime.of(2025, 1, 1, 0, 0, 0));
    price.setValue(3.5d);

    Ingredient ingredient = new Ingredient();
    ingredient.setIngredientId(6);
    ingredient.setIngredientName("rice");
    ingredient.setUnit(G);
    ingredient.setIngredientPrices(List.of(price));
    ingredient.setStockMovements(List.of());
    ingredient.setLastModified(LocalDateTime.of(2025, 1, 1, 0, 0, 0));

    return ingredient;
  }
}
