package com.vendingmachine;

import java.util.Map;

public class VendingMachine {
    private Map<String, Snack> snacks;
    private StateOfVendingMachine state;
    private SnackDispenseHandler handler;
    private Snack selectedSnack;
    private double moneyInserted;

    public VendingMachine(Map<String, Snack> snacks, SnackDispenseHandler handler) {
        this.snacks = snacks;
        this.handler = handler;
        this.state = new IdleState();
        this.moneyInserted = 0;
        System.out.println("Vending Machine is Ready in Idle State.");
        System.out.println("Please make a selection.");
    }

    public void selectSnack(String snackName) {
        state.selectSnack(this, snackName);
    }

    public void insertMoney(double amount) {
        state.insertMoney(this, amount);
    }

    public void dispenseSnack() {
        state.dispenseSnack(this);
    }

    public void cancelTransaction() {
        state.cancelTransaction(this);
    }

    public void reset() {
        this.selectedSnack = null;
        this.moneyInserted = 0;
        System.out.println("\nMachine is idle. Please make a selection.");
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Snack> entry : snacks.entrySet()) {
            System.out.printf("%s: %d left\n", entry.getKey(), entry.getValue().getQuantity());
        }
        System.out.println("-------------------------");
    }

    // Getters and Setters
    public Snack getSnack(String snackName) {
        return snacks.get(snackName);
    }

    public StateOfVendingMachine getState() {
        return state;
    }

    public void setState(StateOfVendingMachine state) {
        System.out.printf("--- Vending Machine state changed to: %s ---", state.getClass().getSimpleName());
        this.state = state;
    }

    public SnackDispenseHandler getHandler() {
        return handler;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public void setSelectedSnack(Snack selectedSnack) {
        this.selectedSnack = selectedSnack;
    }

    public double getMoneyInserted() {
        return moneyInserted;
    }

    public void setMoneyInserted(double moneyInserted) {
        this.moneyInserted = moneyInserted;
    }
}
