package com.phonebook.persistence;

import java.util.List;

public interface CrudDao<T> {

    T add(T entity);

    T find(Long id);

    List<T> findAll();

    T update(T entity);

    void delete(Long id);
}
