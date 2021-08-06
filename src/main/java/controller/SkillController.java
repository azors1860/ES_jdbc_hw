package controller;

import model.Skill;
import repository.impl.SkillRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;
import service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService service = new SkillService(new SkillRepositoryImpl());

    public Skill getSkill(int id) {
        Skill result = null;
        try {
            result = service.getSkill(id);
        } catch (UnknownItemException | RepositoryException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Skill addSkill(String nameSkill) {
        Skill result = null;
        try {
           result = service.addSkill(nameSkill);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Skill renameSkill(int index, String name) {
        Skill result = null;
        try {
           result = service.renameSkill(index, name);
        } catch (RepositoryException | IllegalArgumentException | UnknownItemException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void deleteSkill(int index) {
        try {
            service.deleteSkill(index);
        } catch (RepositoryException | UnknownItemException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Skill> getAllSkills(){
        List<Skill> result = null;
        try {
            result = service.getAllSkills();
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
