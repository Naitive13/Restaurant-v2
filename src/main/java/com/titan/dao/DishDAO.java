package com.titan.dao;

import com.titan.dao.mapper.DishMapper;
import com.titan.db.Datasource;
import com.titan.entities.Dish;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
      st.setInt(1, page);
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
    DishMapper dishMapper = new DishMapper();
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
            + "ON CONFLICT (dish_id) DO UPDATE SET"
            + "dish_name = excluded.dish_name, dish_price=execluded.dish_price "
            + "RETURNING dish_id, dish_name, dish_price";

    try (Connection connection = this.datasource.getConnection();
        PreparedStatement st = connection.prepareStatement(query)) {

      dishToAdd.forEach(
          dish -> {
            try {
              st.setLong(1, dish.getDishId());
              st.setString(2, dish.getDishName());
              st.setDouble(3, dish.getDishPrice());
              st.addBatch();
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
            dishIngredientDAO.saveAll(dish.getIngredientList());
          });

      try (ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
          dishes.add(dishMapper.apply(rs));
        }
      }
      return dishes;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
