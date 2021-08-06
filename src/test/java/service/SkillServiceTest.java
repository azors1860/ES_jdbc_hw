package service;

import model.Skill;
import org.junit.Test;
import org.mockito.Mockito;
import repository.SkillRepository;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SkillServiceTest {

    SkillRepository repository = Mockito.mock(SkillRepository.class);
    SkillService service = new SkillService(repository);

    @Test
    public void testGetSkillWhenSkillExist() throws UnknownItemException, RepositoryException {
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1, "Java"));
        skills.add(new Skill(2, "Docker"));
        skills.add(new Skill(3, "SQL"));
        when(repository.read(1)).thenReturn(skills.get(0));
        when(repository.read(3)).thenReturn(skills.get(2));
        assertEquals(service.getSkill(1),skills.get(0));
        assertEquals(service.getSkill(3),skills.get(2));
        verify(repository).read(1);
        verify(repository).read(3);
    }

    @Test(expected = UnknownItemException.class)
    public void testGetSkillWhenSkillNotExistThenException() throws UnknownItemException, RepositoryException {
        when(repository.read(100)).thenThrow(UnknownItemException.class);
        service.getSkill(100);
    }

    @Test
    public void testDeleteSkillWhenSkillExist() throws UnknownItemException, RepositoryException {
        service.deleteSkill(1);
        verify(repository).delete(1);
    }

    @Test
    public void testCreateSkill() throws RepositoryException {
        String nameSkill = "python";
        service.addSkill(nameSkill);
        verify(repository).create(new Skill(nameSkill));
    }

    @Test
    public void testUpdateSkillWhenSkillExist() throws UnknownItemException, RepositoryException {
        Skill skill = new Skill(1,"python");
        service.renameSkill(1, "python");
        verify(repository).update(skill);
    }
}
