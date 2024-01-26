import java.util.ArrayList;

public class TransactionHistory {
    private final ArrayList<String> checkingHistory;
    private final ArrayList<String> savingHistory;

    public TransactionHistory() {
        checkingHistory = new ArrayList<>();
        savingHistory = new ArrayList<>();
    }

    public ArrayList<String> getCheckingHistory() { //returns checking history
        return checkingHistory;
    }

    public ArrayList<String> getSavingHistory() { //returns saving history
        return savingHistory;
    }

    public void addTransaction(Account account, double amount) { //adds the message and color based on if it was a withdraw or deposit
        if (account.getName().equals("Checking")) {
            if (amount < 0 ) {
                checkingHistory.add(" \u001B[31m\t-\u001B[0m " + amount + "$");
            } else {
                checkingHistory.add(" \u001B[32m+\u001B[0m " + amount + "$");
            }
        } else {
            if (amount < 0 ) {
                savingHistory.add(" \u001B[31m\t-\u001B[0m " + amount + "$");
            } else {
                savingHistory.add(" \u001B[32m+\u001B[0m " + amount + "$");
            }
        }
    }

}
