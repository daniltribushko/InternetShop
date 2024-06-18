package org.example.userservice.services.db;

import java.util.List;

/**
 * @param <T>  Класс сущности для работы с бд
 * @param <ID> Класс идентификатора записи в бд
 * @author Tribushko Danil
 * <p>
 * Интерфейс сервиса для аботы с сущностями в бд
 */
public interface DBService<T, ID> {
    /**
     * Сохранение сущности в бд
     *
     * @param entity сущность для сохранениея в бд
     */
    void save(T entity);

    /**
     * Поиск сущности по идентификатору в бд
     *
     * @param id идентификатор сущности
     * @return сущность из бд
     */
    T findById(ID id);

    /**
     * Поиск всех сущностей из бд
     *
     * @return список сущностей из бд
     */
    List<T> findAll();

    /**
     * Удаление сущности из бд
     *
     * @param id идентификатор сущности
     */
    void delete(ID id);
}
