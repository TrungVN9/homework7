package com.vendingmachine.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class WaitingForMoneyStateTest {

    private VendingMachine vendingMachine;
    private WaitingForMoneyState waitingForMoneyState;

    @BeforeEach
    public void setUp() {
        Snack coke = new Snack("Coke", 1.50, 10);
        Snack pepsi = new Snack("Pepsi", 1.40, 5);
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", coke);
        snacks.put("Pepsi", pepsi);

        SnackDispenseHandler handler = new SnackHandler("Coke", new SnackHandler("Pepsi", null));
        vendingMachine = new VendingMachine(snacks, handler);
        vendingMachine.selectSnack("Coke");
        waitingForMoneyState = new WaitingForMoneyState();
        vendingMachine.setState(waitingForMoneyState);
    }

    @Test
    public void testInsertEnoughMoney() {
        waitingForMoneyState.insertMoney(vendingMachine, 2.00);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertEquals(9, vendingMachine.getSnack("Coke").getQuantity());
    }

    @Test
    public void testInsertInsufficientMoney() {
        waitingForMoneyState.insertMoney(vendingMachine, 1.00);
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals(1.00, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testSelectAnotherSnack() {
        waitingForMoneyState.selectSnack(vendingMachine, "Pepsi");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals("Pepsi", vendingMachine.getSelectedSnack().getName());
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testCancelTransaction() {
        waitingForMoneyState.insertMoney(vendingMachine, 1.00);
        waitingForMoneyState.cancelTransaction(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertNull(vendingMachine.getSelectedSnack());
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testDispenseSnack() {
        waitingForMoneyState.dispenseSnack(vendingMachine);
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
    }
}
