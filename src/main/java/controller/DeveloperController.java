package controller;

import model.Developer;
import model.Skill;
import repository.impl.DeveloperRepositoryImpl;
import repository.impl.SkillRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import service.DeveloperService;
import service.SkillService;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class DeveloperController {

    private final View view = new View();
    private final DeveloperService developerService = new DeveloperService(new DeveloperRepositoryImpl());
    private final SkillService skillService = new SkillService(new SkillRepositoryImpl());

    public void addDeveloper() {
        String lastName = view.inputText("Введите фамилию разработчика: ", false);
        String firstName = view.inputText("Введите имя разработчика: ", false);
        int teamId = view.inputInt("Введите id команды: ");
        try {
            Developer newDeveloper = new Developer(firstName, lastName, teamId, getListSkillByIdSkill());
            developerService.addDeveloper(newDeveloper);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printDeveloper(){
        try {
            view.printMessage(getDeveloperById().toString());
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDeveloper(){
        int id = view.inputInt("Введите идентификатор разработчика: ");
        try {
            developerService.deleteDeveloper(id);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void renameLastName(){
        String lastName = view.inputText("Введите новую фамилию разработчика: ", false);
        try {
            developerService.renameLastName(getDeveloperById(), lastName);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void renameFirstName(){
        String firstName = view.inputText("Введите новое имя разработчика: ", false);
        try {
            developerService.renameFirstName(getDeveloperById(), firstName);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editSkillForDeveloper(){
        view.printMessage("Введите идентификатор разработчика и идентификаторы скилов, которые должны иметься у данного разработчика");
        try {
            developerService.setListSkills(getDeveloperById(), getListSkillByIdSkill());
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private Developer getDeveloperById() throws RepositoryException, UnknownItemException {
        int id = view.inputInt("Введите идентификатор разработчика: ");
        return developerService.getDeveloper(id);
    }

    private List<Skill> getListSkillByIdSkill() throws RepositoryException, UnknownItemException {
        List<Skill> skillList = new ArrayList<>();
        String skillsIdStr = view.inputText("Введите id скилов через пробел: ", true);
        if (skillsIdStr.equals("")){
            return skillList;
        }
        String[] skillIdArr = skillsIdStr.split(" ");
        for (String skill: skillIdArr){
            int indexSkill = Integer.parseInt(skill);
            skillList.add(skillService.getSkill(indexSkill));
        }
        return skillList;
    }
}
