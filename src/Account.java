public class Account {
    //Used to represent the customer’s savings and checking accounts; there should be two Account objects in your program, one for checking and one for savings.
    //Each account should store its name (i.e. “savings account”), its current balance (i.e. 145.78), and the Customer that owns the account.
    //Should have methods to update the account balance (add or remove money).
    //Should include any other static or instance variables and/or methods, including getters/setters and private helper methods, that you determine are necessary to implement the requirements.

    private final String name;
    private double balance;

    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getBalance() { //not really needed
        return balance;
    }

    public void setBalance(double bal) { //not really needed
        balance = bal;
    }

    public void withdraw(double amount) { //withdraws money
        setBalance(balance - amount);
        System.out.println(" Withdrawal Successful\n" +
                           " Balance: " + getBalance());
    }

    public void deposit(double amount) { //deposits money
        setBalance(balance + amount);
        System.out.println(" Deposit Successful\n" +
                           " Balance: " + getBalance());
    }
}
