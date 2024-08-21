package com.example.spaceprobeapi.model;

import com.example.spaceprobeapi.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Position {

    private int x;

    private int y;

    public void moveForward(Direction direction) {
        switch (direction) {
            case NORTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y--;
                break;
            case WEST:
                x--;
                break;
            default:
                throw new IllegalArgumentException("Direção inválida: " + direction);
        }
    }
}
