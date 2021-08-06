package view;

import controller.DeveloperController;
import controller.SkillController;
import controller.TeamController;

public class ConsoleProgramme {
    View view = new View();
    SkillController skillController = new SkillController();
    DeveloperController developerController = new DeveloperController();
    TeamController teamController = new TeamController();

    public void runProgram() {
        String message =
                "1 : Навыки\n" +
                        "2 : Разработчики\n" +
                        "3 : Команды\n" +
                        "-1 : Завершить программу\n";
        while (true) {
            view.printMessage(message);
            int input = view.inputInt();
            switch (input) {
                case 1:
                    skillMenu();
                    break;
                case 2:
                    developerMenu();
                    break;
                case 3:
                    teamMenu();
                    break;
                case -1:
                    System.exit(0);
                default:
                    view.messageIncorrectInput();
            }
        }
    }

    private void teamMenu() {
        String message =
                "1 : Вывести информацию о команде на экран\n" +
                        "2 : Создать новую команду\n" +
                        "3 : Удалить команду\n" +
                        "4 : Изменить статус команды\n" +
                        "0 : Выход в главное меню\n" +
                        "-1 : Завершить программу\n";
        outerLoop:
        while (true){
            view.printMessage(message);
            int input = view.inputInt();
            switch (input) {
                case 1:
                    teamController.printTeam();
                    break;
                case 2:
                    teamController.createTeam();
                    break;
                case 3:
                    teamController.deleteTeam();
                    break;
                case 4:
                    teamController.updateStatusTeam();
                    break;
                case 0:
                    break outerLoop;
                case -1:
                    System.exit(0);
                    break;
                default:
                    view.messageIncorrectInput();
            }
        }
    }

    private void skillMenu() {
        String message =
                "1 : Добавить навык\n" +
                        "2 : Переименовать навык\n" +
                        "3 : Удалить навык \n" +
                        "4 : Показать название навыка по id\n" +
                        "0 : Выход в главное меню\n" +
                        "-1 : Завершить программу\n";
        outerLoop:
        while (true) {
            view.printMessage(message);
            int input = view.inputInt();
            switch (input) {
                case 1:
                    skillController.addSkill();
                    break;
                case 2:
                    skillController.renameSkill();
                    break;
                case 3:
                    skillController.deleteSkill();
                    break;
                case 4:
                    skillController.printSkill();
                    break;
                case 0:
                    break outerLoop;
                case -1:
                    System.exit(0);
                    break;
                default:
                    view.messageIncorrectInput();
            }
        }
    }

    private void developerMenu() {
        String message = "1 : Добавить нового разработчика\n" +
                "2 : Удалить разработчика\n" +
                "3 : Получить данные о разработчике по id\n" +
                "4 : Изменить имя разработчика\n" +
                "5 : Изменить фамилию разработчика\n" +
                "6 : Установить новый список навыков для разработчика\n" +
                "0 : Выход в главное меню\n" +
                "-1 : Завершить программу\n";
        outerLoop:
        while (true) {
            view.printMessage(message);
            int input = view.inputInt();
            switch (input) {
                case 1:
                    developerController.addDeveloper();
                    break;
                case 2:
                    developerController.deleteDeveloper();
                    break;
                case 3:
                    developerController.printDeveloper();
                    break;
                case 4:
                    developerController.renameFirstName();
                    break;
                case 5:
                    developerController.renameLastName();
                    break;
                case 6:
                    developerController.editSkillForDeveloper();
                    break;
                case 0:
                    break outerLoop;
                case -1:
                    System.exit(0);
                    break;
                default:
                    view.messageIncorrectInput();
            }
        }
    }
}

