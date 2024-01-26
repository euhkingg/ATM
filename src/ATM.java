import java.util.ArrayList;
import java.util.Scanner;
public class ATM {
    private String selector;
    private Scanner sc;
    private ArrayList<String> existingPINS;
    private ArrayList<Customer> existingCustomers;
    public ATM() {
        selector = "";
        sc = new Scanner(System.in);
        existingPINS = new ArrayList<String>();
        existingCustomers = new ArrayList<Customer>();
    }

    public void start() {
        welcome(false);
    }

    public void welcome(boolean accountCreated) {
        if (accountCreated) {
            System.out.println("""
                              ATM              \s
                 Account Creation Successful!
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
        while (!(selector.equals("1") || selector.equals("2"))) {
            System.out.println(" Invalid Option");
            System.out.print(" Select an option: ");
            selector = sc.nextLine();
        }

        if (selector.equals("1")) {
            int accountIndex = Login();
            if (accountIndex != -1) {
                selectAccount(accountIndex);
            } else {
                welcome(false);
            }
        } else {
            newAccount();
            welcome(true);
        }
    }

    public void newAccount() {
        boolean isInt = false;
        System.out.println("     Creating New Account     ");
        System.out.print(" Name: ");
        String name = sc.nextLine();
        String PIN;
        while (true) {
            System.out.print(" Create PIN: ");
            if (sc.hasNextInt()) {
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
        }
        Customer customer = new Customer(name, Integer.parseInt(PIN));
        existingCustomers.add(customer);
        existingPINS.add(String.valueOf(PIN));
    }

    public int Login() {
        System.out.println("            Login             ");
        System.out.print(" \n PIN('Exit' to return to the main menu): ");
        try {
            String PIN = sc.nextLine();
            if (PIN.equals("Exit") || PIN.equals("EXIT")) {
                return -1;
            } else {
                if (existingPINS.contains(PIN)) {
                    return existingPINS.indexOf(PIN);
                } else {
                    System.out.println(" Invalid PIN");
                    return Login();
                }
            }
        } catch(Exception e) {
            System.out.println(" Invalid PIN");
            return Login();
        }
    }

    public void selectAccount(int index) {
        System.out.println("""
                 Please choose an account to access
                 1) Checking Account
                 2) Savings Account
                 3) Back\
                """);
        System.out.print(" Select an option: ");
        selector = sc.nextLine();
        while (!(selector.equals("1") || selector.equals("2") || selector.equals("3"))) {
            System.out.println(" Invalid Option, try again");
            System.out.print("\n Select an option: ");
            selector = sc.nextLine();
        }
        if (selector.equals("1")) {
            accountOptions(index, "Checking");
        } else if (selector.equals("2")) {
            accountOptions(index, "Saving");
        } else {
            welcome(false);
        }
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
        while ((!(selector.equals("1") || selector.equals("2") || selector.equals("3") || selector.equals("4")))) {
            System.out.println(" Invalid Option, Try Again");
            System.out.print(" Select an option: ");
            selector = sc.nextLine();
        }
        if (selector.equals("1")) {
            while (true) {
                System.out.print(" How much you like to withdraw? ('Exit' to exit) : ");
                if (sc.hasNextDouble()) {
                    amount = sc.nextDouble();
                    if (amount <= existingCustomers.get(index).getAccount(accountName).getBalance()) {
                        break;
                    } else {
                        System.out.println(" Insufficient Funds");
                    }
                } else {
                    if (sc.nextLine().equals("Back") || sc.nextLine().equals("back") || sc.nextLine().equals("Back")) {
                        welcome(false);
                    }
                    System.out.println(" Unrecognized amount, try again");
                    sc.next();
                }
            }
            existingCustomers.get(index).getAccount(accountName).withdraw(amount);
            welcome(false);
        } else if (selector.equals("2")) {
            while (true) {
                System.out.print(" How much would you like to deposit?: ");
                if (sc.hasNextDouble()) {
                    amount = sc.nextDouble();
                    break;
                } else {
                    System.out.println(" Unrecognized amount, try again");
                    sc.next();
                }
            }
            existingCustomers.get(index).getAccount(accountName).deposit(amount);
            welcome(false);
        } else if (selector.equals("3")) {

        } else {
            selectAccount(index);
        }



    }

}
