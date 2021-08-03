package repository;

import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

public interface Repository<T> {
    /**
     * Создать объект в базе данных.
     * @param item - объект, необходимо записать в БД.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    void create(T item) throws RepositoryException, UnknownItemException;

    /**
     * Изменить существующий объект в базе данных.
     * @param item - объект, который будет записан вместо старого.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    void update(T item) throws RepositoryException, UnknownItemException;

    /**
     * Получить объект из базы данных.
     * @param id - идентификатор объекта
     * @return - объект полученный из БД.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    T read(int id) throws RepositoryException, UnknownItemException;

    /**
     * Удалить объект из базы данных.
     * @param id - идентификатор объекта.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    void delete(int id) throws RepositoryException, UnknownItemException;
}
