package view;

import controller.TeamController;
import model.Team;

import java.util.List;

public class TeamView {

    private final Console console = new Console();
    private final TeamController controller = new TeamController();

    public void teamMenu() {
        String message =
                "1 : Вывести информацию о команде на экран\n" +
                        "2 : Создать новую команду\n" +
                        "3 : Удалить команду\n" +
                        "4 : Изменить статус команды\n" +
                        "5 : Вывести список всех команд\n" +
                        "0 : Выход в главное меню\n" +
                        "-1 : Завершить программу\n";
        outerLoop:
        while (true) {
            console.printMessage(message);
            int input = console.inputInt();
            switch (input) {
                case 1:
                    printTeam();
                    break;
                case 2:
                    createTeam();
                    break;
                case 3:
                    deleteTeam();
                    break;
                case 4:
                    updateStatusTeam();
                    break;
                case 5:
                    printAllTeams();
                    break;
                case 0:
                    break outerLoop;
                case -1:
                    System.exit(0);
                    break;
                default:
                    console.messageIncorrectInput();
            }
        }
    }

    private void printAllTeams() {
        List<Team> teamList = controller.getAllTeams();
        if (teamList != null) {
            console.printMessage(teamList.toString());
        }
    }

    private void updateStatusTeam() {
        int id = console.inputInt("Введите идентификатор команды: ");
        boolean status = getStatusTeam();
        Team team = controller.updateStatusTeam(id, status);
        if (team != null) {
            console.printMessage("Изменена команда: " + team);
        }
    }

    private void deleteTeam() {
        int id = console.inputInt("Введите идентификатор команды: ");
        controller.deleteTeam(id);
    }

    private void createTeam() {
        boolean status = getStatusTeam();
        Team team = controller.createTeam(status);
        if (team != null) {
            console.printMessage("Создана новыя команда: " + team);
        }
    }

    private void printTeam() {
        int id = console.inputInt("Введите идентификатор команды: ");
        Team team = controller.getTeam(id);
        if (team != null) {
            console.printMessage(team.toString());
        }
    }

    private boolean getStatusTeam() {
        int status = console.inputInt("Введите значение соответствующий статусу команды. ACTIVE - 1, DELETED - 2 : ");
        if (status == 1) {
            return true;
        } else if (status == 2) {
            return false;
        } else {
            console.printMessage("Некорректно введено значение. Повторите ввод ещё раз");
            return getStatusTeam();
        }
    }
}
