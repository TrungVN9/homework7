public class DispensingSnackState implements StateOfVendingMachine {
    @Override
    public void selectSnack(VendingMachine machine, String snackName) {
        System.out.println("Cannot select another snack while dispensing.");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.printf("Cannot insert money while dispensing. Returning $%.2f.%n", amount);
    }

    @Override
    public void dispenseSnack(VendingMachine machine) {
        System.out.println("Dispensing...");
        machine.getHandler().handleRequest(machine);
        machine.setState(new IdleState());
        machine.reset();
    }

    @Override
    public void cancelTransaction(VendingMachine machine) {
        System.out.println("Cannot cancel, currently dispensing.");
    }
}
