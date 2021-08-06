package service;

import lombok.NonNull;
import model.Skill;
import repository.impl.SkillRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.util.List;

public class SkillService {

    private final SkillRepositoryImpl repository;

    public SkillService(SkillRepositoryImpl repository) {
        this.repository = repository;
    }

    /**
     * Добавить навык.
     * @param nameSkill - название навыка.
     * @throws RepositoryException - возникает в случае проблем с БД.
     */
    public void addSkill(@NonNull String nameSkill) throws RepositoryException {
        if (nameSkill.equals("")){
            newThrowIllegalArgumentException("nameSkill(название навыка)");
        }
        Skill skill = new Skill(nameSkill);
        repository.create(skill);
    }

    /**
     * Удалить навык по id.
     * @param id - идентификатор навыка.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void deleteSkill(int id) throws RepositoryException, UnknownItemException {
        repository.delete(id);
    }

    /**
     * Переименовать навык по id.
     * @param id - идентификатор навыка.
     * @param name - имя навыка.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void renameSkill(int id, @NonNull String name) throws RepositoryException, UnknownItemException {
        if (name.equals("")){
            newThrowIllegalArgumentException("name(название навыка)");
        }
        Skill updateSkill = new Skill(id, name);
        repository.update(updateSkill);
    }

    /**
     * Получить навык по id.
     * @param id - идентификатор навыка.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public Skill getSkill(int id) throws UnknownItemException, RepositoryException {
       return repository.read(id);
    }

    /**
     * Получить все навыки из БД.
     * @throws RepositoryException - возникает в случае проблем с БД.
     */
    public List<Skill> getAllSkills() throws RepositoryException {
       return repository.getAllItems();
    }

    private void newThrowIllegalArgumentException(String nameStr){
        throw new IllegalArgumentException("Строка " + nameStr +" не может быть пустой!");
    }
}
