package repository.impl;

import lombok.NonNull;
import model.Developer;
import model.Skill;
import repository.DataSourcePool;
import repository.DeveloperRepository;
import repository.exception.RepositoryException;
import repository.exception.SqlSkillForDeveloperException;
import repository.exception.UnknownItemException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository<Developer> {

    private final String INSERT_DEVELOPER = "INSERT INTO developer (firstName, lastName, team_id) VALUES (?,?,?);";
    private final String UPDATE_DEVELOPER = "UPDATE developer SET firstName = ?, lastName = ?, team_id = ? WHERE id = ?;";
    private final String DELETE_DEVELOPER = "DELETE FROM developer WHERE ID = ?;";
    private final String DELETE_ALL_SKILLS_FOR_DEVELOPER = "DELETE FROM developer_skill WHERE developer_id = ?;";
    private final String SELECT_ALL_DEVELOPERS =
            "SELECT developer.id as id, firstname, lastname, name as skill, skill.id as skill_id, team_id " +
                    "FROM developer " +
                    "LEFT JOIN developer_skill on(developer.id=developer_skill.developer_id) " +
                    "LEFT JOIN skill on(developer_skill.skill_id=skill.id);";
    private final String SELECT_DEVELOPER =
            "SELECT developer.id as id, firstname, lastname, name as skill, skill.id as skill_id, team_id " +
                    "FROM developer " +
                    "LEFT JOIN developer_skill on(developer.id=developer_skill.developer_id)" +
                    "LEFT JOIN skill on(developer_skill.skill_id=skill.id)" +
                    "WHERE developer.id = ?";

    @Override
    public Developer create(@NonNull Developer item) throws RepositoryException {
        Connection connection = null;

        try {
            connection = DataSourcePool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_DEVELOPER, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getLastName());
            statement.setInt(3, item.getTeamId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int index = resultSet.getInt(1);
            item.setId(index);
            addSkillsForDeveloper(connection, index, item.getSkills());
            connection.commit();
        } catch (SQLException throwables) {
            String messageEx = "???????????????? ???????????? ?????? ???????????????? ????????????????????????. " + throwables.getMessage();
            if (throwables.getMessage().contains("developer_team_fkey")) {
                messageEx += " ?????????????????? ?????? ?????????????????????????? ?????????????? ???????????? ??????????";
            }
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RepositoryException(messageEx, throwables);
                }
            }
            throw new RepositoryException(messageEx, throwables);
        }
        return item;
    }

    @Override
    public Developer update(@NonNull Developer item) throws RepositoryException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            try {
                connection = DataSourcePool.getConnection();
                statement = connection.prepareStatement(UPDATE_DEVELOPER);
                connection.setAutoCommit(false);
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setInt(3, item.getTeamId());
                statement.setInt(4, item.getId());
                deleteAllSkillsForDeveloper(connection, item.getId());
                addSkillsForDeveloper(connection, item.getId(), item.getSkills());
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                if (connection != null) {
                    connection.rollback();
                }
                throw new RepositoryException("???????????????? ???????????? ?????? ?????????????????? ????????????????????????", e);
            } finally {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("???????????????? ???????????? ?????? ?????????????????? ????????????????????????", e);
        }
        return item;
    }

    @Override
    public Developer read(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id ???? ?????????? ???????? ???????????? 1");
        }
        Developer result = null;
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_DEVELOPER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            String firstName = null;
            String lastName = null;
            int teamId = 0;
            List<Skill> skills = new ArrayList<>();
            while (resultSet.next()) {
                if (firstName == null) {
                    firstName = resultSet.getString("firstName");
                    lastName = resultSet.getString("lastName");
                    teamId = resultSet.getInt("team_id");
                }
                int skillIndex = resultSet.getInt("skill_id");
                String skillName = resultSet.getString("skill");
                if (skillName != null) {
                    skills.add(new Skill(skillIndex, skillName));
                }
                resultSet.next();
            }
            if (firstName != null) {
                result = new Developer(id, firstName, lastName, teamId, skills);
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("???????????????? ???????????? ?????? ???????????? ????????????????????????", throwables);
        }
        if (result == null) {
            throw new UnknownItemException("?????????????????? ???????????? ?????? ?????????????????? ???????????????????????? ?? ?????????????????? ID");
        }
        return result;
    }

    @Override
    public void delete(int id) throws RepositoryException, UnknownItemException {
        if (id < 1) {
            throw new UnknownItemException("id ???? ?????????? ???????? ???????????? 1");
        }
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DEVELOPER)) {
            statement.setInt(1, id);
            int count = statement.executeUpdate();
            if (count == 0) {
                throw new UnknownItemException("?????????????????? ???????????? ?????? ?????????????????? ???????????????????????? ?? ?????????????????? ID");
            }
        } catch (SQLException throwables) {
            throw new RepositoryException("???????????????? ???????????? ?????? ???????????????? ????????????????????????", throwables);
        }
    }

    @Override
    public List<Developer> getAllItems() throws RepositoryException {
        List<Developer> result = new ArrayList<>();
        try (Connection connection = DataSourcePool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DEVELOPERS)) {
            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            int teamId = 0;
            String firsName = null;
            String lastName = null;
            List<Skill> skills = null;
            while (resultSet.next()) {
                int tempId = resultSet.getInt("id");
                if (id != tempId) {

                    if (id != 0) {
                        result.add(new Developer(id, lastName, firsName, teamId, skills));
                    }
                    id = tempId;
                    teamId = resultSet.getInt("team_id");
                    firsName = resultSet.getString("firstname");
                    lastName = resultSet.getString("lastname");
                    skills = new ArrayList<>();
                }
                int skillId = resultSet.getInt("skill_id");
                String skill = resultSet.getString("skill");
                if (skill != null) {
                    skills.add(new Skill(skillId, skill));
                }
            }
            if (id != 0) {
                result.add(new Developer(id, lastName, firsName, teamId, skills));
            }

        } catch (SQLException throwables) {
            throw new RepositoryException("???????????????? ???????????? ?????? ?????????????????? ???????????? ??????????????????????????", throwables);
        }
        return result;
    }

    /**
     * ?????????????? ?????? ???????????? ?? ???????????????????? ????????????????????????.
     *
     * @param connection - sql ???????????????????? ?? ????.
     * @param id         - ?????????????????????????? ????????????????????????
     * @throws SqlSkillForDeveloperException - ?????????????????? ?? ???????????? ?????????????? ?? ???? ?????? ???????????? ?? ???????????????? ????????????????????????.
     */
    private void deleteAllSkillsForDeveloper(Connection connection, int id) throws SqlSkillForDeveloperException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SKILLS_FOR_DEVELOPER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlSkillForDeveloperException("?????????????????? ???????????? ?????? ???????????????? ?????????????? ????????????????????????", e);
        }
    }

    /**
     * ???????????????? ?????????? ???????????? ????????????????????????.
     *
     * @param developerIndex - ?????????????????????????? ???????????????????????? .
     * @param skills         - ???????????? ??????????????, ?????????????? ???????????????????? ????????????????.
     * @throws SqlSkillForDeveloperException - ?????????????????? ?? ???????????? ?????????????? ?? ???? ?????? ???????????? ?? ???????????????? ????????????????????????.
     */
    private void addSkillsForDeveloper(Connection connection, int developerIndex, List<Skill> skills)
            throws SqlSkillForDeveloperException {
        if (skills.size() > 0) {
            String query = createQueryToAddDataToTableDevSk(skills, developerIndex);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new SqlSkillForDeveloperException("?????????????????? ???????????? ?????? ???????????????????? ?????????????? ????????????????????????", e);
            }
        }
    }

    /**
     * ?????????????? ???????????? ?????? ???????????????????? ?????????????? ????????????????????????.
     *
     * @param skills         - ???????????? ??????????????, ?????????????? ???????????????????? ????????????????.
     * @param developerIndex - ?????????????????????????? ????????????????????????.
     * @return - SQL ???????????? ?????? ???????????????????? ?????????????? ????????????????????????.
     */
    private String createQueryToAddDataToTableDevSk(List<Skill> skills, int developerIndex) {
        StringBuilder query = new StringBuilder("INSERT INTO developer_skill (developer_id,skill_id) VALUES ");
        for (int i = 0; i < skills.size(); i++) {
            query.append("(")
                    .append(developerIndex)
                    .append(",")
                    .append(skills.get(i).getId())
                    .append(")");
            if (i < skills.size() - 1) {
                query.append(",");
            } else {
                query.append(";");
            }
        }
        return query.toString();
    }
}