package ru.luttsev.deals.service;

import java.util.List;

public interface CrudService<T, K> {

    List<T> getAll();

    T getById(K id);

    T save(T entity);

    void deleteById(K id);

}
