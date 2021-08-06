package view;

import controller.SkillController;
import model.Skill;

import java.util.List;

public class SkillView {

    private final SkillController controller = new SkillController();
    private final Console console = new Console();

    public void skillMenu() {
        String message =
                        "1 : Добавить навык\n" +
                        "2 : Переименовать навык\n" +
                        "3 : Удалить навык \n" +
                        "4 : Показать название навыка по id\n" +
                        "5 : Показать все навыки\n" +
                        "0 : Выход в главное меню\n" +
                        "-1 : Завершить программу\n";
        outerLoop:
        while (true) {
            console.printMessage(message);
            int input = console.inputInt();
            switch (input) {
                case 1:
                    addSkill();
                    break;
                case 2:
                    renameSkill();
                    break;
                case 3:
                    deleteSkill();
                    break;
                case 4:
                    printSkill();
                    break;
                case 5:
                    printAllSkills();
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

    private void addSkill() {
        String nameSkill = console.inputText("Имя навыка: ", false);
        Skill skill = controller.addSkill(nameSkill);
        if (skill != null) {
            console.printMessage("Создан новый навык: " + skill);
        }

    }

    private void renameSkill() {
        int index = console.inputInt("Введите индекс навыка: ");
        String name = console.inputText("Введите новое имя навыка: ", false);
        Skill skill = controller.renameSkill(index, name);
        if (skill != null) {
            console.printMessage("Навык изменён: " + skill);
        }
    }

    private void deleteSkill() {
        int index = console.inputInt("Введите индекс навыка: ");
        controller.deleteSkill(index);
    }

    private void printSkill() {
        int index = console.inputInt("Введите индекс навыка: ");
        Skill skill = controller.getSkill(index);
        if (skill != null){
            console.printMessage(skill.toString());
        }
    }

    private void printAllSkills() {
       List<Skill> skills = controller.getAllSkills();
       console.printMessage(skills.toString());
    }
}
