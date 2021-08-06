package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private int teamId;
    private List<Skill> skills;

    public Developer(String firstName, String lastName, int teamId, List<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
        this.skills = skills;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder(String.format("Разработчик id %s, %s %s. Навыки: ", id, firstName, lastName));
        for (Skill skill: skills){
            builder.append(skill.getName()).append(" ");
        }
        return builder.toString();
    }
}
