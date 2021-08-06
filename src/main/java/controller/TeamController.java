package controller;

import model.Team;
import repository.impl.TeamRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import service.TeamService;

import java.util.List;

public class TeamController {
    private final TeamService service = new TeamService(new TeamRepositoryImpl());

    public Team getTeam(int id) {
        Team result = null;
        try {
           result = service.getTeam(id);
        } catch (UnknownItemException | RepositoryException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void deleteTeam(int id) {
        try {
            service.deleteTeam(id);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public Team createTeam(boolean status) {
        Team result = null;
        try {
            result = service.create(status);
        } catch (RepositoryException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Team updateStatusTeam(int id, boolean status) {
        Team result = null;
        try {
           result = service.updateStatus(id, status);
        } catch (RepositoryException | IllegalArgumentException | UnknownItemException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<Team> getAllTeams(){
        List<Team> result = null;
        try {
            result = service.getAllTeams();
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());;
        }
        return result;
    }
}
