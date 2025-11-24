package com.vendingmachine;

public class WaitingForMoneyState implements StateOfVendingMachine {
    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        Snack snack = machine.getSnack(snackName);
        if (snack != null) {
            if (machine.getMoneyInserted() > 0) {
                System.out.printf("Selection changed. Returning previous amount: $%.2f%n", machine.getMoneyInserted());
                machine.setMoneyInserted(0);
            }
            machine.setSelectedSnack(snack);
            System.out.printf("Selection is now %s. Price: $%.2f.%n", snack.getName(), snack.getPrice());
        } else {
            System.out.printf("Sorry, '%s' is not a valid selection.%n", snackName);
        }
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        machine.setMoneyInserted(machine.getMoneyInserted() + amount);
        System.out.printf("Inserted $%.2f. Total inserted: $%.2f.%n", amount, machine.getMoneyInserted());

        if (machine.getMoneyInserted() >= machine.getSelectedSnack().getPrice()) {
            machine.setState(new DispensingSnackState());
            machine.dispenseSnack();
        } else {
            double needed = machine.getSelectedSnack().getPrice() - machine.getMoneyInserted();
            System.out.printf("Please insert $%.2f more.%n", needed);
        }
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("Please insert enough money to dispense the snack.");
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("Transaction cancelled.");
        if (machine.getMoneyInserted() > 0) {
            System.out.printf("Returning $%.2f.%n", machine.getMoneyInserted());
        }
        machine.setState(new IdleState());
        machine.reset();
    }
}
