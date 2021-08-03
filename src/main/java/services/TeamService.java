package services;

import models.Team;
import models.TeamStatus;
import repository.TeamRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

public class TeamService {
    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить команду по ID.
     * @param id - Идентификатор команды.
     * @return - Команда, полученная по ID.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public Team getTeam(int id) throws UnknownItemException, RepositoryException {
        return repository.read(id);
    }

    /**
     * Удалить команду.
     * @param id - Идентификатор команды.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void deleteTeam(int id) throws RepositoryException, UnknownItemException {
        repository.delete(id);
    }

    /**
     * Создать новую команду.
     * @param status - Статус команды
     *      true  - TeamStatus.ACTIVE
     *      false - TeamStatus.DELETED
     * @return - Идентификатор присвоенный новой команде.
     * @throws RepositoryException - возникает в случае проблем с БД.
     */
    public int create(boolean status) throws RepositoryException {
        return repository.createAndReturnId(new Team(0, null, getTeamStatus(status)));
    }

    /**
     * Изменить статус команды.
     * @param id - Идентификатор команды.
     * @param status - Статус команды, который необходимо установить.
     * @throws RepositoryException - возникает в случае проблем с БД.
     * @throws UnknownItemException - возникает в случае, если объект с указанным id не найден.
     */
    public void updateStatus(int id, boolean status) throws RepositoryException, UnknownItemException {
        Team team = new Team(id, getTeamStatus(status));
        repository.update(team);
    }

    /**
     * Возвращает TeamStatus с соответствующим статусов.
     *      true  - TeamStatus.ACTIVE
     *      false - TeamStatus.DELETED
     * @param status - Значение статуса
     * @return - TeamStatus с соответствующим значением.
     */
    private TeamStatus getTeamStatus(boolean status) {
        if (status) {
            return TeamStatus.ACTIVE;
        } else {
            return TeamStatus.DELETED;
        }
    }
}
