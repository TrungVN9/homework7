package com.vendingmachine.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SnackTest {

    @Test
    public void testSnackCreation() {
        Snack snack = new Snack("Coke", 1.50, 10);
        assertEquals("Coke", snack.getName());
        assertEquals(1.50, snack.getPrice());
        assertEquals(10, snack.getQuantity());
    }

    @Test
    public void testSetQuantity() {
        Snack snack = new Snack("Coke", 1.50, 10);
        snack.setQuantity(5);
        assertEquals(5, snack.getQuantity());
    }
}
