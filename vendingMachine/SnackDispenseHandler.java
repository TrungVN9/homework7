package com.vendingmachine;

public abstract class SnackDispenseHandler {
    protected SnackDispenseHandler nextHandler;

    public SnackDispenseHandler(SnackDispenseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void setNext(SnackDispenseHandler next) {
        this.nextHandler = next;
    }

    public abstract void handleRequest(VendingMachine machine);
}
