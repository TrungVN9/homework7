package com.vendingmachine.src;

public class SnackHandler extends SnackDispenseHandler {
    private String snackName;

    public SnackHandler(String snackName, SnackDispenseHandler nextHandler) {
        super(nextHandler);
        this.snackName = snackName;
    }

    @Override
    public void handleRequest(VendingMachine machine) {
        if (machine.getSelectedSnack().getName().equals(this.snackName)) {
            Snack snack = machine.getSnack(this.snackName);

            if (snack.getQuantity() <= 0) {
                System.out.printf("Sorry, %s is out of stock.%n", snack.getName());
                System.out.printf("Returning $%.2f.%n", machine.getMoneyInserted());
                return;
            }

            if (machine.getMoneyInserted() >= snack.getPrice()) {
                snack.setQuantity(snack.getQuantity() - 1);
                double change = machine.getMoneyInserted() - snack.getPrice();
                System.out.printf("Dispensed %s.%n", snack.getName());
                if (change > 0) {
                    System.out.printf("Returning change: $%.2f.%n", change);
                }
            } else {
                System.out.printf("Error: Insufficient funds detected by handler for %s.%n", snack.getName());
                System.out.printf("Returning $%.2f.%n", machine.getMoneyInserted());
            }
        } else if (nextHandler != null) {
            nextHandler.handleRequest(machine);
        } else {
            System.out.printf("Error: No handler found for %s. This should not happen.%n",
                    machine.getSelectedSnack().getName());
            System.out.printf("Returning $%.2f.%n", machine.getMoneyInserted());
        }
    }
}
