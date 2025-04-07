import com.titan.dao.IngredientDAO;
import com.titan.entities.Dish;
import com.titan.entities.Ingredient;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IngredientTest {
  private IngredientDAO subject = new IngredientDAO();

    @Test
    @Order(1)
    public void get_all_ingredients() {
        List<Ingredient> ingredients = subject.getAll(1,10);
        assertFalse(ingredients.isEmpty());
    }

    @Test
    @Order(2)
    public void get_one_ingredient(){
        Ingredient ingredient = subject.getById(1L);
        assertEquals("sausage", ingredient.getIngredientName());
    }

    @Test
    @Order(3)
    public void add_new_ingredient(){
        Ingredient expected = subject.getById(1L);

        List<Ingredient> dishes = subject.saveAll(List.of(expected));
        Ingredient actual = dishes.getFirst();

        assertEquals(expected,actual);
    }

    @Test
    @Order(4)
    public void get_available_quantity(){
        Ingredient egg = subject.getById(3);
        Ingredient bread = subject.getById(4);

        assertEquals(80, egg.getAvailableQuantity(),"Available quantity for egg");
        assertEquals(30, bread.getAvailableQuantity(),"Available quantity for bread");
    }
}
