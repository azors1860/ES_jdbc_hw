package models;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Skill {
    private int id;
    private String name;

    public Skill(String name) {
        this.name = name;
    }
}
