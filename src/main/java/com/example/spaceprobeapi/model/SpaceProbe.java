package com.example.spaceprobeapi.model;

import com.example.spaceprobeapi.enums.Direction;
import com.example.spaceprobeapi.exception.InvalidCommandException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "space_probe")
public class SpaceProbe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "planet_id", nullable = false)
    private Planet planet;

    public void executeCommands(String commands) {
        for (char command : commands.toCharArray()) {
            switch (command) {
                case 'M':
                    position.moveForward(direction);
                    break;
                case 'L':
                    direction = direction.turnLeft();
                    break;
                case 'R':
                    direction = direction.turnRight();
                    break;
                default:
                    throw new InvalidCommandException("Comando inválido: " + command);
            }
        }
    }
}
