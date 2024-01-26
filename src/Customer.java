public class Customer {
    //Customer class:
    //Used to create the customer.
    //There only needs to be one Customer object in the program (i.e. you don't need to design for multiple customers using the ATM).
    //Should store the customer's name and PIN.
    //Should have a method to update the customer’s PIN.
    //Should include any other static or instance variables and/or methods, including getters/setters and private helper methods, that you determine are necessary to implement the requirements.
    private final String name;
    private int PIN;
    private final Account checking;
    private final Account saving;
    public Customer(String name, int pin) {
        PIN = pin;
        this.name = name;
        checking = new Account("checking");
        saving = new Account("saving");
    }

    public Account getAccount(String name) {
        if (name.equals("Checking")) {
            return checking;
        }
        return saving;
    }

    public String getName() {return name;}


    public void printAccountOptions() {
        System.out.println(" ");
    }
}
