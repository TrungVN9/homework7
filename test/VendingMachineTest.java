package com.vendingmachine.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private VendingMachine vendingMachine;
    private Map<String, Snack> snacks;

    @BeforeEach
    public void setUp() {
        Snack coke = new Snack("Coke", 1.50, 10);
        Snack pepsi = new Snack("Pepsi", 1.40, 5);
        Snack chips = new Snack("Chips", 2.00, 0);

        snacks = new HashMap<>();
        snacks.put("Coke", coke);
        snacks.put("Pepsi", pepsi);
        snacks.put("Chips", chips);

        SnackDispenseHandler handler = new SnackHandler("Coke",
                new SnackHandler("Pepsi", new SnackHandler("Chips", null)));
        vendingMachine = new VendingMachine(snacks, handler);
    }

    @Test
    public void testSelectSnackAndInsertEnoughMoney() {
        vendingMachine.selectSnack("Coke");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals("Coke", vendingMachine.getSelectedSnack().getName());

        vendingMachine.insertMoney(2.00);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertEquals(9, snacks.get("Coke").getQuantity());
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testSelectSnackAndInsertInsufficientMoney() {
        vendingMachine.selectSnack("Pepsi");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals("Pepsi", vendingMachine.getSelectedSnack().getName());

        vendingMachine.insertMoney(1.00);
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        assertEquals(1.00, vendingMachine.getMoneyInserted());
        assertEquals(5, snacks.get("Pepsi").getQuantity());
    }

    @Test
    public void testSelectSnackAndCancel() {
        vendingMachine.selectSnack("Coke");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        vendingMachine.insertMoney(1.00);

        vendingMachine.cancelTransaction();
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertNull(vendingMachine.getSelectedSnack());
        assertEquals(0, vendingMachine.getMoneyInserted());
    }

    @Test
    public void testSelectOutOfStockSnack() {
        vendingMachine.selectSnack("Chips");
        assertTrue(vendingMachine.getState() instanceof WaitingForMoneyState);
        vendingMachine.insertMoney(2.00);
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertEquals(0, snacks.get("Chips").getQuantity());
    }

    @Test
    public void testSelectInvalidSnack() {
        vendingMachine.selectSnack("Sprite");
        assertTrue(vendingMachine.getState() instanceof IdleState);
        assertNull(vendingMachine.getSelectedSnack());
    }
}
