package service;

import model.Team;
import model.TeamStatus;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import repository.TeamRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TeamServiceTest {
    TeamRepository repository = Mockito.mock(TeamRepository.class);
    TeamService service = new TeamService(repository);
    List<Team> teams = new ArrayList<>();

    @Test
    public void testGetTeamWhenTeamExist() throws UnknownItemException, RepositoryException {

        teams.add(new Team(1,TeamStatus.ACTIVE));
        teams.add(new Team(2,TeamStatus.ACTIVE));
        teams.add(new Team(3,TeamStatus.DELETED));
        teams.add(new Team(4,TeamStatus.DELETED));

        when(repository.read(1)).thenReturn(teams.get(1));
        when(repository.read(3)).thenReturn(teams.get(3));
        assertEquals(service.getTeam(1),teams.get(1));
        assertEquals(service.getTeam(3),teams.get(3));
        verify(repository).read(1);
        verify(repository).read(3);
    }

    @Test(expected = UnknownItemException.class)
    public void testGetTeamWhenTeamNotExistThenException() throws UnknownItemException, RepositoryException {
        when(repository.read(100)).thenThrow(UnknownItemException.class);
        service.getTeam(100);
    }

    @Test
    public void testDeleteTeamWhenTeamExist() throws UnknownItemException, RepositoryException {
        service.deleteTeam(1);
        verify(repository).delete(1);
    }

    @Test
    public void testCreateTeam() throws RepositoryException {
        service.create(true);
        verify(repository).createAndReturnId(new Team(0, null, TeamStatus.ACTIVE));

        service.create(false);
        verify(repository).createAndReturnId(new Team(0, null, TeamStatus.DELETED));
    }

    @Test
    public void testUpdateTeamWhenTeamExist() throws UnknownItemException, RepositoryException {
        Team team1 = new Team(1, TeamStatus.DELETED);
        Team team2 = new Team(3, TeamStatus.ACTIVE);
        service.updateStatus(1, false);
        service.updateStatus(3, true);
        verify(repository).update(team1);
        verify(repository).update(team2);
    }
}
