package models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Team {
    private int id;
    private List<Developer> developers;
    private TeamStatus status;

    public Team(int id, TeamStatus status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\nКоманда id " + id + ", cтатус " + status + ":\n");
        if (developers != null) {
            for (Developer developer : developers) {
                stringBuilder.append(developer).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}