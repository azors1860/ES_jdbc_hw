package repository.impl;

import lombok.NonNull;
import model.Skill;
import repository.DataSourcePool;
import repository.SkillRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository<Skill> {

    private final String INSERT_SKILL = "INSERT INTO skill (name) VALUES (?);";
    private final String SELECT_SKILL = "SELECT * FROM skill WHERE id = ?";
    private final String UPDATE_SKILL = "UPDATE skill SET name = ? WHERE id = ?;";
    private final String DELETE_SKILL = "DELETE FROM skill WHERE ID = ?;";
    private final String SELECT_ALL_SKILLS = "SELECT * FROM skill";

    @Override
    public Skill create(@NonNull Skill item) throws RepositoryException {
        if (item == null) {
            throw new RuntimeException("Объект не существует");
        }
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SKILL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, item.getName());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int index = resultSet.getInt(1);
            item.setId(index);
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при создании навыка", throwables);
        }
        return item;
    }

    @Override
    public Skill update(@NonNull Skill item) throws RepositoryException, UnknownItemException {
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SKILL)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getId());
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UnknownItemException("Произошла ошибка при получении навыка с указанным ID");
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при изменении навыка", throwables);
        }
        return item;
    }

    @Override
    public Skill read(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id не может быть меньше 1");
        }
        Skill result = null;
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_SKILL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                result = new Skill(id, name);
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при чтении навыка", throwables);
        }
        if (result == null) {
            throw new UnknownItemException("Произошла ошибка при получении навыка с указанным ID");
        }
        return result;
    }

    @Override
    public void delete(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id не может быть меньше 1");
        }
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SKILL)) {
            statement.setInt(1, id);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UnknownItemException("Произошла ошибка при получении навыка с указанным ID");
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при удалении навыка", throwables);
        }
    }

    @Override
    public List<Skill> getAllItems() throws RepositoryException {
        List<Skill> result = new ArrayList<>();
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SKILLS)) {
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            String name = null;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                name = resultSet.getString("name");
                result.add(new Skill(id, name));
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("Возникла ошибка при получении списка навыков", throwables);
        }
        return result;
    }
}