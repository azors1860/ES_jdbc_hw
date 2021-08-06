package service;

import lombok.NonNull;
import model.Developer;
import model.Skill;
import repository.impl.DeveloperRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.util.List;

public class DeveloperService {

    private final DeveloperRepositoryImpl repository;

    public DeveloperService(DeveloperRepositoryImpl repository) {
        this.repository = repository;
    }

    /**
     * Получить разработчика по id.
     *
     * @param id - идентификатор разработчика.
     * @return - разработчик.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public Developer getDeveloper(int id) throws UnknownItemException, RepositoryException {
        return repository.read(id);
    }

    /**
     * Добавить разработчика.
     *
     * @param developer - добавляемый разработчик.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     */
    public void addDeveloper(@NonNull Developer developer) throws RepositoryException {
        repository.create(developer);
    }

    /**
     * Переименовать фамилию разработчика.
     *
     * @param developer   - разработчик
     * @param newLastName - новая фамилия
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void renameLastName(@NonNull Developer developer, @NonNull String newLastName)
            throws RepositoryException, UnknownItemException {

        if (newLastName.equals("")) {
            newThrowIllegalArgumentException("newLastName(новая фамилия)");
        }
        developer.setLastName(newLastName);
        repository.update(developer);
    }

    /**
     * Установить новое имя разработчику.
     *
     * @param developer    - разработчик.
     * @param newFirstName - новое имя.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void renameFirstName(@NonNull Developer developer, @NonNull String newFirstName)
            throws RepositoryException, UnknownItemException {

        if (newFirstName.equals("")) {
            newThrowIllegalArgumentException("newFirstName(новое имя)");
        }
        developer.setFirstName(newFirstName);
        repository.update(developer);
    }

    /**
     * Установить новый список скилов разработчику.
     *
     * @param developer - разработчик.
     * @param skills    - список скилов.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void setListSkills(@NonNull Developer developer, @NonNull List<Skill> skills)
            throws RepositoryException, UnknownItemException {

        developer.setSkills(skills);
        repository.update(developer);
    }

    /**
     * Изменить сущность разработчика.
     *
     * @param updateDeveloper - обновленный разработчик.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void updateDeveloper(@NonNull Developer updateDeveloper) throws RepositoryException, UnknownItemException {
        repository.update(updateDeveloper);
    }

    /**
     * Удалить разработчика по id.
     *
     * @param id - идентификатор разработчика.
     * @throws RepositoryException  - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void deleteDeveloper(int id) throws RepositoryException, UnknownItemException {
        repository.delete(id);
    }

    /**
     * Получить всех разработчиков из БД.
     * @return - Список всех разработчиков
     * @throws RepositoryException  - возникает в случае проблем с БД.
     */
    public List<Developer> getAllDevelopers() throws RepositoryException {
       return repository.getAllItems();
    }

    private void newThrowIllegalArgumentException(String nameStr) {
        throw new IllegalArgumentException("Строка " + nameStr + " не может быть пустой!");
    }

}
