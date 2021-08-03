package controllers;

import repository.TeamRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import services.TeamService;
import view.View;

public class TeamController {
    private final TeamService service = new TeamService(new TeamRepository());
    private final View view = new View();

    public void printTeam() {
        int id = view.inputInt("Введите идентификатор команды: ");
        try {
            System.out.println(service.getTeam(id));
        } catch (UnknownItemException | RepositoryException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTeam() {
        int id = view.inputInt("Введите идентификатор команды: ");
        try {
            service.deleteTeam(id);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTeam() {
        try {
            int id = 0;
            id = service.create(getStatusTeam());
            view.printMessage("Создана команда id " + id);
        } catch (RepositoryException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateStatusTeam() {
        int id = view.inputInt("Введите идентификатор команды: ");
        try {
            boolean status = getStatusTeam();
            service.updateStatus(id, status);
        } catch (RepositoryException | IllegalArgumentException | UnknownItemException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean getStatusTeam() {
        int status = view.inputInt("Введите значение соответствующий статусу команды. ACTIVE - 1, DELETED - 2 : ");
        if (status == 1) {
            return true;
        } else if (status == 2) {
            return false;
        } else {
            throw new IllegalArgumentException("Некорректно введено значение");
        }
    }
}
