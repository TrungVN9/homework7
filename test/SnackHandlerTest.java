package com.vendingmachine.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class SnackHandlerTest {

    private VendingMachine vendingMachine;
    private SnackHandler cokeHandler;
    private SnackHandler pepsiHandler;

    @BeforeEach
    public void setUp() {
        Snack coke = new Snack("Coke", 1.50, 10);
        Snack pepsi = new Snack("Pepsi", 1.40, 0);
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", coke);
        snacks.put("Pepsi", pepsi);

        pepsiHandler = new SnackHandler("Pepsi", null);
        cokeHandler = new SnackHandler("Coke", pepsiHandler);

        vendingMachine = new VendingMachine(snacks, cokeHandler);
    }

    @Test
    public void testHandleRequestWithEnoughMoney() {
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(2.00);
        assertEquals(9, vendingMachine.getSnack("Coke").getQuantity());
    }

    @Test
    public void testHandleRequestWithInsufficientMoney() {
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(1.00);
        assertEquals(10, vendingMachine.getSnack("Coke").getQuantity());
    }

    @Test
    public void testHandleRequestForOutOfStockSnack() {
        vendingMachine.selectSnack("Pepsi");
        vendingMachine.insertMoney(2.00);
        assertEquals(0, vendingMachine.getSnack("Pepsi").getQuantity());
    }
}
