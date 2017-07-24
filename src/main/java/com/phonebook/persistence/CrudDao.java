package com.phonebook.persistence;

import java.util.List;

public interface CrudDao<T> {

    T add(T entity);

    T find(Long id);

    T update(T entity);

    List<T> findAll();

    void delete(Long id);
}
