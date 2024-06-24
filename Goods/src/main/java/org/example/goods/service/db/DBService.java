package org.example.goods.service.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Сервис для работы с сущностями в бд
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
    Page<T> findAll(Pageable pageable);

    /**
     * Удаление сущности из бд
     *
     * @param id идентификатор сущности
     */
    void delete(ID id);
}
