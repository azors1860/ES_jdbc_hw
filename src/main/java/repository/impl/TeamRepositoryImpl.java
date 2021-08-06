package repository.impl;

import lombok.NonNull;
import model.Developer;
import model.Team;
import model.TeamStatus;
import repository.DataSourcePool;
import repository.TeamRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Chuvashov Sergey
 */
public class TeamRepositoryImpl implements TeamRepository<Team> {

    DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl();

    private final String INSERT_TEAM = "INSERT INTO team (team_status) values (?)";
    private final String DELETE_TEAM = "DELETE FROM team WHERE ID = ?;";
    private final String UPDATE_TEAM = "UPDATE team SET team_status = ? WHERE id = ?;";
    private final String SELECT_ALL_TEAMS =
            "SELECT t.id as team_id, d.id as developer_id, team_status FROM team AS t " +
            "LEFT JOIN developer AS d on(t.id=d.team_id);";
    private final String SELECT_TEAM =
            "SELECT t.id as team_id, d.id as developer_id, team_status FROM team AS t " +
            "LEFT JOIN developer AS d on(t.id=d.team_id) " +
            "WHERE t.id = ?;";

    @Override
    public Team create(@NonNull Team item) throws RepositoryException {
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TEAM, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getStatus().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int index = resultSet.getInt(1);
            item.setId(index);
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при создании команды", throwables);
        }
        return item;
    }

    @Override
    public Team update(@NonNull Team item) throws RepositoryException, UnknownItemException {
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TEAM)) {
            statement.setString(1, item.getStatus().toString());
            statement.setInt(2, item.getId());
            int count = statement.executeUpdate();
            if (count == 0){
                throw new UnknownItemException("Произошла ошибка при получении команды с указанным ID");
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при изменении команды", throwables);
        }
        return item;
    }

    @Override
    public Team read(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id не может быть меньше 1");
        }
        Team team = null;
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TEAM)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Developer> developers = new ArrayList<>();
            String status = null;
            while (resultSet.next()) {
                if (status == null) {
                    status = resultSet.getString("team_status");
                }
                int idDeveloper = resultSet.getInt("developer_id");
                developers.add(developerRepository.read(idDeveloper));
            }
            if (status != null) {
                team = new Team(id, developers, TeamStatus.valueOf(status));
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при чтении команды", throwables);
        }
        if (team == null) {
            throw new UnknownItemException("Произошла ошибка при получении команды с указанным ID");
        }
        return team;
    }

    @Override
    public void delete(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id не может быть меньше 1");
        }
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TEAM)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при удалении команды", throwables);
        }
    }

    @Override
    public List<Team> getAllItems() throws RepositoryException {
        List<Team> result = new ArrayList<>();
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TEAMS)) {

            int id = 0;
            ResultSet resultSet = statement.executeQuery();
            List<Developer> developers = null;
            String status = null;

            while (resultSet.next()) {
                int tempId = resultSet.getInt("team_id");
                if (tempId != id){
                    if (id != 0){
                        result.add(new Team(id, developers, TeamStatus.valueOf(status)));
                    }
                    id = tempId;
                    status = resultSet.getString("team_status");
                    developers = new ArrayList<>();
                }
                int idDeveloper = resultSet.getInt("developer_id");
                developers.add(developerRepository.read(idDeveloper));
            }

            if (id != 0){
                result.add(new Team(id, developers, TeamStatus.valueOf(status)));
            }

        } catch (SQLException | UnknownItemException throwables) {
            throw new RepositoryException("Возникла ошибка при получении списка команд", throwables);
        }
        return result;
    }
}
