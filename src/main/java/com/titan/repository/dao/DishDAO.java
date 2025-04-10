package com.titan.repository.dao;

import com.titan.repository.mapper.DishMapper;
import com.titan.repository.db.Datasource;
import com.titan.model.entities.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DishDAO implements CrudDAO<Dish> {
  private final Datasource datasource;
  private final DishMapper dishMapper;
  private final DishIngredientDAO dishIngredientDAO;

  @Override
  public List<Dish> getAll(int page, int pageSize) {
    List<Dish> dishes = new ArrayList<>();
    String query = "SELECT dish_id, dish_name, dish_price FROM dish LIMIT ? OFFSET ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setInt(1, pageSize);
      st.setInt(2, (page - 1) * pageSize);

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          Dish dish = dishMapper.apply(rs);
          dishes.add(dish);
        }
      }
      return dishes;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Dish getById(long id) {
    String query = "SELECT dish_id, dish_name, dish_price FROM dish WHERE dish_id = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setLong(1, id);
      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return dishMapper.apply(rs);
        } else {
          throw new RuntimeException("dish was not found");
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Dish> saveAll(List<Dish> dishToAdd) {
    List<Dish> dishes = new ArrayList<>();
    String query =
        "INSERT INTO dish "
            + "(dish_id, dish_name, dish_price) "
            + "VALUES (?,?,?) "
            + "ON CONFLICT (dish_id) DO UPDATE SET "
            + "dish_name = excluded.dish_name, dish_price=excluded.dish_price "
            + "RETURNING dish_id, dish_name, dish_price";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      dishToAdd.forEach(
          dish -> {
            try {
              st.setLong(1, dish.getDishId());
              st.setString(2, dish.getDishName());
              st.setDouble(3, dish.getDishPrice());

              try (ResultSet rs = st.executeQuery()) {
                dishIngredientDAO.saveAll(dish.getIngredientList());
                if (rs.next()) {
                  dishes.add(dishMapper.apply(rs));
                }
              }
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          });

      return dishes;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

   public Dish getByName(String name) {
    String query = "SELECT dish_id, dish_name, dish_price FROM dish WHERE dish_name = ?";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {
      st.setString(1, name);
      try (ResultSet rs = st.executeQuery()) {
        if (rs.next()) {
          return dishMapper.apply(rs);
        } else {
          throw new RuntimeException("dish was not found");
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
