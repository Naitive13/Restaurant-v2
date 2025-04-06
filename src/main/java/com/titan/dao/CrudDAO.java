package com.titan.dao;

import java.util.List;

public interface CrudDAO<T> {
    List<T> getAll(int page, int pageSize);
    T getById (long id);
    List<T> saveAll(List<T> tList);
}
