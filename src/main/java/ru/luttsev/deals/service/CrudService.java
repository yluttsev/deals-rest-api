package ru.luttsev.deals.service;

import java.util.List;

/**
 * Базовый сервис для CRUD операций
 *
 * @param <T> сущность для работы
 * @param <K> ID сущности для работы
 * @author Yuri Luttsev
 */
public interface CrudService<T, K> {

    /**
     * Получение всех сущностей
     *
     * @return список надйенных сущностей
     */
    List<T> getAll();

    /**
     * Получение сущности по ID
     *
     * @param id ID сущности
     * @return найденная сущность
     */
    T getById(K id);

    /**
     * Сохранение сущности
     *
     * @param entity сущность для сохранения
     * @return сохраненная сущность
     */
    T save(T entity);

    /**
     * Удаление сущности по ID
     *
     * @param id ID сущности
     */
    void deleteById(K id);

}
