package controller;

import model.Developer;
import model.Skill;
import repository.impl.DeveloperRepositoryImpl;
import repository.impl.SkillRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import service.DeveloperService;
import service.SkillService;

import java.util.ArrayList;
import java.util.List;

public class DeveloperController {

    private final DeveloperService service = new DeveloperService(new DeveloperRepositoryImpl());
    private final SkillService skillService = new SkillService(new SkillRepositoryImpl());

    public Developer addDeveloper(Developer newDeveloper, List<Integer> skillIdList) {
        Developer result = null;
        try {
            List<Skill> skills = getListSkillByIdSkill(skillIdList);
            newDeveloper.setSkills(skills);
            result = service.addDeveloper(newDeveloper);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Developer getDeveloper(int id) {
        Developer result = null;
        try {
            result = service.getDeveloper(id);
        } catch (UnknownItemException | RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void deleteDeveloper(int id) {
        try {
            service.deleteDeveloper(id);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public Developer renameLastName(int id, String newLastName) {
        Developer result = null;
        try {
            Developer developer = service.getDeveloper(id);
            result = service.renameLastName(developer, newLastName);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Developer renameFirstName(int id, String newFirstName) {
        Developer result = null;
        try {
            Developer developer = service.getDeveloper(id);
            result = service.renameFirstName(developer, newFirstName);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void editSkillForDeveloper(int id, List<Integer> skillIdList) {
        try {
            Developer developer = service.getDeveloper(id);
            List<Skill> skills = getListSkillByIdSkill(skillIdList);
            service.setListSkills(developer, skills);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Developer> getAllDeveloper(){
        List<Developer> result = null;
        try {
            result = service.getAllDevelopers();
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private List<Skill> getListSkillByIdSkill(List<Integer> skillIdList) throws RepositoryException, UnknownItemException {
        List<Skill> result = new ArrayList<>();
        for (int id : skillIdList) {
            result.add(skillService.getSkill(id));
        }
        return result;
    }
}
