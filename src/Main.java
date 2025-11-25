package com.vendingmachine.src;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating 6 Snacks ...");
        // Create snacks
        Map<String, Snack> snacks = new HashMap<>();
        snacks.put("Coke", new Snack("Coke", 1.50, 5));
        snacks.put("Pepsi", new Snack("Pepsi", 1.50, 5));
        snacks.put("Cheetos", new Snack("Cheetos", 1.75, 3));
        snacks.put("Doritos", new Snack("Doritos", 1.75, 4));
        snacks.put("KitKat", new Snack("KitKat", 1.25, 6));
        snacks.put("Snickers", new Snack("Snickers", 1.25, 1));

        System.out.println("6 Snacks are successfully created! ");
        // Set up Chain of Responsibility in reverse order
        SnackDispenseHandler snickersHandler = new SnackHandler("Snickers", null);
        SnackDispenseHandler kitkatHandler = new SnackHandler("KitKat", snickersHandler);
        SnackDispenseHandler doritosHandler = new SnackHandler("Doritos", kitkatHandler);
        SnackDispenseHandler cheetosHandler = new SnackHandler("Cheetos", doritosHandler);
        SnackDispenseHandler pepsiHandler = new SnackHandler("Pepsi", cheetosHandler);
        SnackDispenseHandler cokeHandler = new SnackHandler("Coke", pepsiHandler);

        // Create Vending Machine
        VendingMachine machine = new VendingMachine(snacks, cokeHandler);
        machine.displayInventory();

        System.out.println(">>> TEST 1: Purchase a KitKat -- Successful return<<<");
        machine.selectSnack("KitKat");
        machine.insertMoney(1.00);
        machine.insertMoney(0.25);
        machine.displayInventory();

        System.out.println("\n>>> TEST 2: Insufficient funds for Doritos, then cancel <<<");
        machine.selectSnack("Doritos");
        machine.insertMoney(1.00);
        System.out.println("User decides to cancel.");
        machine.cancelTransaction();

        System.out.println("\n>>> TEST 3: Purchase Snickers, making quantity 0 <<<");
        machine.selectSnack("Snickers");
        machine.insertMoney(2.00); // Overpay to test change
        machine.displayInventory();

        System.out.println("\n>>> TEST 4: Attempt to purchase out-of-stock Snickers <<<");
        machine.selectSnack("Snickers");
        machine.insertMoney(1.25);
        machine.displayInventory();

        System.out.println("\n>>> TEST 5: Successful purchase of Coke <<<");
        machine.selectSnack("Coke");
        machine.insertMoney(1.50);
        machine.displayInventory();
    }
}
