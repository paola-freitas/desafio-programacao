package com.example.spaceprobeapi.service;

import com.example.spaceprobeapi.enums.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void shouldMatchCardinalOrder() {
        Direction[] expectedOrder = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
        assertArrayEquals(expectedOrder, Direction.values());
    }
}
