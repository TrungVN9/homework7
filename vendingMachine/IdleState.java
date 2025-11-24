package com.vendingmachine;

public class IdleState implements StateOfVendingMachine {
    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        Snack snack = machine.getSnack(snackName);
        if (snack != null) {
            machine.setSelectedSnack(snack);
            System.out.printf("Selected %s. Price: $%.2f. Please insert money.%n", snack.getName(), snack.getPrice());
            machine.setState(new WaitingForMoneyState());
        } else {
            System.out.printf("Sorry, '%s' is not a valid selection.%n", snackName);
        }
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Please select a snack first.");
        if (amount > 0) {
            System.out.printf("Returning $%.2f.%n", amount);
        }
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("Please select a snack and insert money first.");
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("Nothing to cancel.");
    }
}
