package controller;

import repository.SkillRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import service.SkillService;
import view.View;

public class SkillController {
    private final SkillService service = new SkillService(new SkillRepository());
    private final View view = new View();

    public void printSkill(){
        int index = view.inputInt("Введите индекс навыка: ");
        try {
            System.out.println(service.getSkill(index));
        } catch (UnknownItemException | RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addSkill() {
        String nameSkill = view.inputText("Имя навыка: ", false);
        try {
            service.addSkill(nameSkill);
        } catch (RepositoryException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void renameSkill() {
        int index = view.inputInt("Введите индекс навыка: ");
        String name = view.inputText("Введите новое имя навыка: ", false);

        try {
            service.renameSkill(index, name);
        } catch (RepositoryException | IllegalArgumentException | UnknownItemException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteSkill() {
        int index = view.inputInt("Введите индекс навыка: ");
        try {
            service.deleteSkill(index);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
