import java.util.ArrayList;
import java.util.Scanner;
public class ATM {
    private String selector;
    private final Scanner sc;
    private final ArrayList<String> existingPINS;
    private final ArrayList<Customer> existingCustomers;
    private final TransactionHistory transactionHistory;
    public ATM() {
        selector = "";
        sc = new Scanner(System.in);
        existingPINS = new ArrayList<>();
        existingCustomers = new ArrayList<>();
        transactionHistory = new TransactionHistory();
    }

    public void start() { //Starts the program
        welcome(false);
    }

    public void welcome(boolean accountCreated) { //Welcomes the user
        sc.reset();
        if (accountCreated) { //Just a message so the user knows the account creation was successful
            System.out.println("""
                              ATM              \s
                 \u001B[32mAccount Creation Successful!\u001B[0m 
                 1) Enter PIN
                 2) Create New Account
                """);
        } else {
            System.out.println("""
                                  ATM              \s
                     1) Enter PIN
                     2) Create New Account
                    """);
        }
        System.out.print(" Select Option: ");
        selector = sc.nextLine();
        while (!(selector.equals("1") || selector.equals("2"))) { //Checks if the user inputted a valid selection
            System.out.println(" Invalid Option");
            System.out.print(" Select an option: ");
            selector = sc.nextLine();
        }

        if (selector.equals("1")) {
            int accountIndex = Login(); //Allows the user to login
            if (accountIndex != -1) { //Checks if the user returned to the menu or not
                selectAccount(accountIndex);
            } else {
                welcome(false);
            }
        } else {
            newAccount(); //Allows the user to create a new account
            welcome(true); //Resets back to the main menu
        }
    }

    public void newAccount() {
        System.out.println("\n     Creating New Account     ");
        System.out.print(" Name: ");
        String name = sc.nextLine();
        String PIN;
        while (true) { //Infinite while loop
            System.out.print(" Create PIN: ");
            if (sc.hasNextInt()) { //Checks for an integer
                PIN = sc.nextLine();
                if  (Integer.parseInt(PIN) >= 0 && Integer.parseInt(PIN) <= 9999 && PIN.length() > 3) { //checks if the PIN is 4 digits long
                    break; //stops the while loop
                } else {
                    System.out.println(" PIN must be 4 integers long");
                }
            } else { //if not an integer print an error statement and try again
                System.out.println(" Invalid Input");
                sc.next(); //prevents an infinite loop with the previously imputed value
            }
        }
        Customer customer = new Customer(name);
        existingCustomers.add(customer);
        existingPINS.add(PIN);
    }

    public int Login() {
        System.out.println("\n            Login             ");
        System.out.print(" \n PIN('Exit' to return to the main menu): ");
        try {
            String PIN = sc.nextLine();
            if (PIN.equals("Exit") || PIN.equals("EXIT")) { //Checks if the user wants to return to menu
                return -1;
            } else {
                if (existingPINS.contains(PIN)) {
                    return existingPINS.indexOf(PIN); //returns the index of the PIN and User in the 2 Arraylists
                } else {
                    System.out.println(" Invalid PIN");
                    return Login();//if the PIN is invalid just redoes the method;
                }
            }
        } catch(Exception e) {
            System.out.println(" Invalid PIN");
            return Login();
        }
    }

    public void selectAccount(int index) {
        System.out.println("""
                 \nPlease choose an account to access
                 1) Checking Account
                 2) Savings Account
                 3) Change PIN
                 4) Back\
                """);
        System.out.print(" Select an option: ");
        selector = sc.nextLine();
        while (!(selector.equals("1") || selector.equals("2") || selector.equals("3") || selector.equals("4"))) { //makes sure input is valid
            System.out.println(" Invalid Option, try again");
            System.out.print("\n Select an option: ");
            selector = sc.nextLine();
        }
        switch (selector) { //chooses a case based on the selector
            case "1" -> accountOptions(index, "Checking");
            case "2" -> accountOptions(index, "Saving");
            case "3" -> changePIN(index);
            default -> welcome(false); //resets
        }
    }

    public void changePIN(int index) {
        String PIN;
        System.out.println("\n Change PIN");
        while (true) {//same thing as before
            System.out.print(" Enter current PIN: ");
            if (sc.nextLine().equals(existingPINS.get(index))) { //checks if the inputted PIN is correct
                System.out.print(" New PIN: ");
                if (sc.hasNextInt()) {//makes sure the new PIN is correct as well
                    PIN = sc.nextLine();
                    if  (Integer.parseInt(PIN) >= 0 && Integer.parseInt(PIN) <= 9999 && PIN.length() > 3) {
                        break;
                    } else {
                        System.out.println(" PIN must be 4 integers long");
                    }
                } else {
                    System.out.println(" PIN must be 4 integers long");
                    sc.next();
                }
            } else {
                System.out.println("Incorrect PIN");
            }
        }
        existingPINS.set(index, PIN);//changes the PIN in the arraylist
    }

    public void accountOptions(int index, String accountName) {
        double amount;
        System.out.println(" " + existingCustomers.get(index).getName() + "'s " + accountName + " Account");
        System.out.println("""
                 1) Withdraw
                 2) Deposit
                 3) Transaction History
                 4) Exit
                """);
        System.out.print(" Select an option: ");
        selector = sc.nextLine();
        while ((!(selector.equals("1") || selector.equals("2") || selector.equals("3") || selector.equals("4")))) { //makes sure there is a valid input
            System.out.println(" Invalid Option, Try Again");
            System.out.print(" Select an option: ");
            selector = sc.nextLine();
        }
        switch (selector) {
            case "1" -> { //withdraw
                while (true) { //infinite while loop until user inputs a correct value
                    System.out.print(" How much you like to withdraw? ('Exit' to exit): ");
                    if (sc.hasNextDouble()) {
                        amount = sc.nextDouble();
                        if (amount <= existingCustomers.get(index).getAccount(accountName).getBalance()) {
                            break;//breaking loop once the user inputs a valid input
                        } else {
                            System.out.println(" Insufficient Funds");
                        }
                    } else {
                        if (sc.nextLine().equals("Exit") || sc.nextLine().equals("exit") || sc.nextLine().equals("EXIT")) { //checking if user wants to go back to menu
                            welcome(false);
                        }
                        System.out.println(" Unrecognized amount, try again");
                        sc.next();//makes sure the scanner doesn't pick up the invalid value
                    }
                }
                existingCustomers.get(index).getAccount(accountName).withdraw(amount); //makes withdrawal
                transactionHistory.addTransaction(existingCustomers.get(index).getAccount(accountName), -amount); //adds to transaction history
                welcome(false);
            }
            case "2" -> {
                while (true) {//same thing
                    System.out.print(" How much would you like to deposit?: ");
                    if (sc.hasNextDouble()) { //checks for a double
                        amount = sc.nextDouble();
                        break;
                    } else { //if not double sends error and repeats
                        System.out.println(" Unrecognized amount, try again");
                        sc.next();
                    }
                }
                existingCustomers.get(index).getAccount(accountName).deposit(amount); //makes deposit in the account
                transactionHistory.addTransaction(existingCustomers.get(index).getAccount(accountName), amount); //Adds the transaction to history using arraylist
                welcome(false);
            }
            case "3" -> {
                System.out.println(" Checking Transaction History");
                if (transactionHistory.getCheckingHistory().isEmpty()) { //if no history prints message
                    System.out.println(" No Transactions");
                }
                for (int i = 0; i < transactionHistory.getCheckingHistory().size(); i++) { //prints checking history
                    System.out.println(transactionHistory.getCheckingHistory().get(i));
                }
                System.out.println(" Saving Transaction History");
                if (transactionHistory.getSavingHistory().isEmpty()) { //if not history prints message
                    System.out.println(" No Transactions");
                }
                for (int i = 0; i < transactionHistory.getSavingHistory().size(); i++) { //prints checking history
                    System.out.println(transactionHistory.getSavingHistory().get(i));
                }
            }
            default -> selectAccount(index);//option to return to selectAccount
        }

    }

}
