package view;

import controller.DeveloperController;
import model.Developer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeveloperView {

    private final Console console = new Console();
    private final DeveloperController controller = new DeveloperController();


    public void developerMenu() {
        String message = "1 : Добавить нового разработчика\n" +
                "2 : Удалить разработчика\n" +
                "3 : Получить данные о разработчике по id\n" +
                "4 : Изменить имя разработчика\n" +
                "5 : Изменить фамилию разработчика\n" +
                "6 : Установить новый список навыков для разработчика\n" +
                "7 : Получить список всех разработчиков\n"+
                "0 : Выход в главное меню\n" +
                "-1 : Завершить программу\n";
        outerLoop:
        while (true) {
            console.printMessage(message);
            int input = console.inputInt();
            switch (input) {
                case 1:
                    addDeveloper();
                    break;
                case 2:
                    deleteDeveloper();
                    break;
                case 3:
                    printDeveloper();
                    break;
                case 4:
                    renameFirstName();
                    break;
                case 5:
                    renameLastName();
                    break;
                case 6:
                    editSkillForDeveloper();
                    break;
                case 7:
                    printAllDevelopers();
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

    private void editSkillForDeveloper() {
        int id = console.inputInt("Введите идентификатор разработчика: ");
        List<Integer> idSkillList = getListIdSkill();
        controller.editSkillForDeveloper(id, idSkillList);

    }

    private void renameLastName() {
        int id = console.inputInt("Введите идентификатор разработчика: ");
        String lastName = console.inputText("Введите новую фамилию разработчика: ", false);
        Developer developer = controller.renameLastName(id, lastName);
        if (developer != null) {
            console.printMessage("Изменена фамилия разработчика:" + developer);
        }
    }

    private void renameFirstName() {
        int id = console.inputInt("Введите идентификатор разработчика: ");
        String lastName = console.inputText("Введите новое имя разработчика: ", false);
        Developer developer = controller.renameFirstName(id, lastName);
        if (developer != null) {
            console.printMessage("Изменено имя разработчика:" + developer);
        }
    }

    private void printDeveloper() {
        int id = console.inputInt("Введите идентификатор разработчика: ");
        Developer developer = controller.getDeveloper(id);
        if (developer != null) {
            console.printMessage(developer.toString());
        }
    }

    private void deleteDeveloper() {
        int id = console.inputInt("Введите идентификатор разработчика: ");
        controller.deleteDeveloper(id);
    }

    private void addDeveloper() {
        String lastName = console.inputText("Введите фамилию разработчика: ", false);
        String firstName = console.inputText("Введите имя разработчика: ", false);
        int teamId = console.inputInt("Введите id команды: ");
        List<Integer> idSkillList = getListIdSkill();
        Developer developer = new Developer(lastName, firstName, teamId, null);
        Developer receivedDeveloper = controller.addDeveloper(developer, idSkillList);
        if (receivedDeveloper != null) {
            console.printMessage("Добавлен новый разработчик:" + developer);
        }
    }

    private void printAllDevelopers() {
        List<Developer> developers = controller.getAllDeveloper();
        if (developers != null){
            console.printMessage(developers.toString());
        }
    }

    private List<Integer> getListIdSkill() {
        List<Integer> skillList = new ArrayList<>();
        String skillsIdStr = console.inputText("Введите id скилов через пробел: ", true);
        if (skillsIdStr.equals("")) {
            return Collections.EMPTY_LIST;
        }
        String[] skillIdArr = skillsIdStr.split(" ");
        for (String skill : skillIdArr) {
            int indexSkill = 0;
            try {
                indexSkill = Integer.parseInt(skill);
            } catch (Exception e) {
                console.printMessage("Данные введены некорректно. Повторите ввод");
                getListIdSkill();
            }
            skillList.add(indexSkill);
        }
        return skillList;
    }
}
