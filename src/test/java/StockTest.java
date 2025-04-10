import com.titan.repository.dao.IngredientDAO;
import com.titan.repository.dao.StockDAO;
import com.titan.model.entities.Ingredient;
import com.titan.model.entities.StockMovement;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.titan.model.enums.StockType.IN;
import static com.titan.model.enums.StockType.OUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockTest {
    StockDAO subject = new StockDAO();
    IngredientDAO ingredientDAO = new IngredientDAO();

    @Test
    @Order(1)
    public void get_all_stock_movement(){
        List<StockMovement> stockMovements = subject.getAll(1,10);

        assertEquals(10, stockMovements.size());

  }

    @Test
    @Order(2)
    public void get_stock_movement_for_one_ingredient(){
        List<StockMovement> stockMovements = subject.getStockMovementsFor(1);
        assertEquals(3, stockMovements.size());
    }

    @Test
    @Order(3)
    public void add_stock_movement (){
        List<StockMovement> expected = new ArrayList<>();
        expected.addAll(saltMovement());
        expected.addAll(riceMovement());

        List<StockMovement> actual = subject.saveAll(expected);

        assertEquals(expected,actual);
    }

    @Test
    @Order(4)
    public void get_available_quantity_for_new_stock_movement(){
        Ingredient salt = ingredientDAO.getById(5);
        Ingredient rice = ingredientDAO.getById(6);

        assertEquals(500, salt.getAvailableQuantity(), "available quantity for salt");
        assertEquals(2_000, rice.getAvailableQuantity(), "available quantity for rice");

    }

    private List<StockMovement> saltMovement(){
        StockMovement movementIn = new StockMovement();
        movementIn.setType(IN);
        movementIn.setIngredientId(5);
        movementIn.setQuantity(1_000);
        movementIn.setLastModified(LocalDateTime.of(2025,1,1,0,0,0));
        movementIn.setId(16);

        StockMovement movementOut = new StockMovement();
        movementOut.setType(OUT);
        movementOut.setIngredientId(5);
        movementOut.setQuantity(500);
        movementOut.setLastModified(LocalDateTime.of(2025,3,15,0,0,0));
        movementOut.setId(17);

        return List.of(movementIn, movementOut);
    }

    private List<StockMovement> riceMovement(){
        StockMovement movementIn = new StockMovement();
        movementIn.setType(IN);
        movementIn.setIngredientId(6);
        movementIn.setQuantity(3_000);
        movementIn.setLastModified(LocalDateTime.of(2025,1,1,0,0,0));
        movementIn.setId(18);

        StockMovement movementOut = new StockMovement();
        movementOut.setType(OUT);
        movementOut.setIngredientId(6);
        movementOut.setQuantity(1_000);
        movementOut.setLastModified(LocalDateTime.of(2025,3,15,0,0,0));
        movementOut.setId(19);

        return List.of(movementIn, movementOut);
    }

}
