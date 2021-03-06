package service;

import model.Developer;
import model.Skill;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import repository.impl.DeveloperRepositoryImpl;
import repository.exception.RepositoryException;
import repository.exception.UnknownItemException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeveloperServiceTest {

    DeveloperRepositoryImpl repository = Mockito.mock(DeveloperRepositoryImpl.class);
    DeveloperService service = new DeveloperService(repository);
    List<Developer> developers;

    @Before
    public void init() {
        developers = new ArrayList<>();
        developers.add(
                new Developer(1, "aaa", "bbb", 2, Arrays.asList(new Skill("java"))));
        developers.add(
                new Developer(2, "xxx", "zzz", 3, Arrays.asList(new Skill("sql"))));
    }

    @Test
    public void testGetDeveloperWhenDeveloperExist() throws UnknownItemException, RepositoryException {
        when(repository.read(1)).thenReturn(developers.get(0));
        when(repository.read(2)).thenReturn(developers.get(1));
        assertEquals(service.getDeveloper(1), developers.get(0));
        assertEquals(service.getDeveloper(2), developers.get(1));
        verify(repository).read(1);
        verify(repository).read(2);
    }

    @Test(expected = UnknownItemException.class)
    public void testGetDeveloperWhenDeveloperNotExistThenException() throws UnknownItemException, RepositoryException {
        when(repository.read(100)).thenThrow(UnknownItemException.class);
        service.getDeveloper(100);
    }

    @Test
    public void testAddDeveloper() throws RepositoryException {
        service.addDeveloper(developers.get(1));
        verify(repository).create(developers.get(1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddDeveloperWhenArgIsNullThenException() throws RepositoryException {
        service.addDeveloper(null);
    }

    @Test
    public void testRenameLastNameWhenDeveloperExist() throws UnknownItemException, RepositoryException {
        service.renameLastName(developers.get(1), "????????????????");
        Developer developer =
                new Developer(2, "xxx", "????????????????", 3, Arrays.asList(new Skill("sql")));
        verify(repository).update(developer);
    }

    @Test(expected = NullPointerException.class)
    public void testRenameLastNameWhenArgDeveloperIsNullThenException() throws UnknownItemException, RepositoryException {
        service.renameLastName(null, "????????????????");
    }

    @Test(expected = NullPointerException.class)
    public void testRenameLastNameWhenArgNewLastNameIsNullThenException() throws UnknownItemException, RepositoryException {
        service.renameLastName(developers.get(1), null);
    }

    @Test
    public void testRenameFirstNameWhenDeveloperExist() throws UnknownItemException, RepositoryException {
        service.renameFirstName(developers.get(1), "????????????????");
        Developer developer =
                new Developer(2, "????????????????", "zzz", 3, Arrays.asList(new Skill("sql")));
        verify(repository).update(developer);
    }

    @Test(expected = NullPointerException.class)
    public void testRenameFirstNameWhenArgDeveloperIsNullThenException()
            throws UnknownItemException, RepositoryException {

        service.renameFirstName(null, "????????????????");
    }

    @Test(expected = NullPointerException.class)
    public void testRenameFirstNameWhenArgNewLastNameIsNullThenException()
            throws UnknownItemException, RepositoryException {

        service.renameFirstName(developers.get(1), null);
    }

    @Test
    public void testSetListSkillsWhenDeveloperExist() throws UnknownItemException, RepositoryException {
        service.setListSkills(developers.get(1), Arrays.asList(new Skill("??????????????????")));
        Developer developer =
                new Developer(2, "xxx", "zzz", 3, Arrays.asList(new Skill("??????????????????")));
        verify(repository).update(developer);
    }

    @Test(expected = NullPointerException.class)
    public void testSetListSkillsWhenArgDeveloperIsNullThenException()
            throws UnknownItemException, RepositoryException {

        service.setListSkills(null, Arrays.asList(new Skill("??????????????????")));
    }

    @Test(expected = NullPointerException.class)
    public void testSetListSkillsWhenArgListSkillIsNullThenException()
            throws UnknownItemException, RepositoryException {

        service.setListSkills(developers.get(1), null);
    }

    @Test
    public void testDeleteSkillWhenDeveloperExist() throws UnknownItemException, RepositoryException {
        service.deleteDeveloper(1);
        verify(repository).delete(1);
    }

}
