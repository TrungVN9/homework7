package com.vendingmachine.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class IdleStateTest {

    private VendingMachine vendingMachine;
    private IdleState idleState;

    @BeforeEach
    public void setUp() {
        Snack coke = new Snack("Coke", 1.50, 10);
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", coke);

        SnackDispenseHandler handler = new SnackHandler("Coke", null);
        vendingMachine = new VendingMachine(snacks, handler);
        idleState = new IdleState();
        vendingMachine.setState(idleState);
    }

    @Test
    public void testSelectSnack() {
        idleState.selectSnack(vendingMachine, "Coke");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals("Coke", vendingMachine.getSelectedSnack().getName());
    }

    @Test
    public void testSelectInvalidSnack() {
        idleState.selectSnack(vendingMachine, "Pepsi");
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertNull(vendingMachine.getSelectedSnack());
    }

    @Test
    public void testInsertMoney() {
        idleState.insertMoney(vendingMachine, 1.00);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testDispenseSnack() {
        idleState.dispenseSnack(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof IdleState);
    }

    @Test
    public void testCancelTransaction() {
        idleState.cancelTransaction(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof IdleState);
    }
}
