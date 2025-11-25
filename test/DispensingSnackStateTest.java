package com.vendingmachine.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class DispensingSnackStateTest {

    private VendingMachine vendingMachine;
    private DispensingSnackState dispensingSnackState;

    @BeforeEach
    public void setUp() {
        Snack coke = new Snack("Coke", 1.50, 10);
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", coke);

        SnackDispenseHandler handler = new SnackHandler("Coke", null);
        vendingMachine = new VendingMachine(snacks, handler);
        vendingMachine.setSelectedSnack(coke);
        vendingMachine.setMoneyInserted(1.50);
        dispensingSnackState = new DispensingSnackState();
        vendingMachine.setState(dispensingSnackState);
    }

    @Test
    public void testDispenseSnack() {
        dispensingSnackState.dispenseSnack(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertEquals(9, vendingMachine.getSnack("Coke").getQuantity());
        assertNull(vendingMachine.getSelectedSnack());
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testSelectSnack() {
        dispensingSnackState.selectSnack(vendingMachine, "Coke");
        assertTrue(vendingMachine.getState() instanceof DispensingSnackState);
    }

    @Test
    public void testInsertMoney() {
        dispensingSnackState.insertMoney(vendingMachine, 1.00);
        assertTrue(vendingMachine.getState() instanceof DispensingSnackState);
        assertEquals(1.50, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testCancelTransaction() {
        dispensingSnackState.cancelTransaction(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof DispensingSnackState);
    }
}
