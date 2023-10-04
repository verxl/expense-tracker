package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

// REFERENCE: TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)

public class Tracker {
    private static final String JSON_STORE = "./data/account.json";
    private AccountOverview finance;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tracker
    public Tracker() throws FileNotFoundException {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() throws FileNotFoundException {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("end")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee You Soon!");
    }

    // MODIFIES: this
    // EFFECTS: initialize an account for the month
    private void init() {
        finance = new AccountOverview(0);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("set -> set your budget for the month");
        System.out.println("add -> add an expense");
        System.out.println("remove -> remove an expense");
        System.out.println("check -> check the finance of the month");
        System.out.println("save -> save account to file");
        System.out.println("load -> load account from file");
        System.out.println("end -> quit the application\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("set")) {
            doSetBudget();
        } else if (command.equals("add")) {
            doAddExpense();
        } else if (command.equals("remove")) {
            doRemoveExpense();
        } else if (command.equals("check")) {
            doCheckFinance();
        } else if (command.equals("save")) {
            saveAccount();
        } else if (command.equals("load")) {
            loadAccount();
        } else {
            System.out.println("Selection not valid... \nPlease select again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the budget of the account
    private void doSetBudget() {
        System.out.println("Enter budget for this month: $");
        double amount = input.nextDouble();

        if (amount > 0.0) {
            finance.addBudget(amount);
        } else if (amount < 0.0) {
            finance.reduceBudget(amount);
        } else {
            System.out.println("Cannot set a zero budget...\n");
        }
        printBalance();
    }

    // MODIFIES: this
    // EFFECTS: conducts an addition of expense
    private void doAddExpense() {
        System.out.println("Enter the amount of the expense:");
        double amount = input.nextDouble();

        System.out.println("Enter the category for this expense:");
        String category = input.next();

        System.out.println("Enter the payment method for this expense:");
        String payment = input.next();

        System.out.println("Enter the date of transaction (MMDDYY)");
        int date = input.nextInt();

        if (amount > 0.0) {
            finance.addExpense(amount, category, payment, date);
            System.out.println("The expense has been recorded.");
            if (finance.needReminder()) {
                System.out.println("There are less than ten bucks left for this month!");
            }
        } else {
            System.out.println("Cannot add a negative expense...\n");
        }

        printBalance();
    }

    // MODIFIES: this
    // EFFECTS: conducts a removal of expense
    private void doRemoveExpense() {
        System.out.println("Enter the amount of the expense you wanted to delete:");
        double amount = input.nextDouble();

        System.out.println("Enter the category for this expense:");
        String category = input.next();

        System.out.println("Enter the payment method for this expense:");
        String payment = input.next();

        System.out.println("Enter the date of transaction (MMDDYY)");
        int date = input.nextInt();

        if (amount > 0.0) {
            finance.removeExpense(amount, category, payment, date);
            System.out.println("The expense has been removed.");
        } else {
            System.out.println("Cannot find the corresponding transaction...\n");
        }
        printBalance();
    }

    private void doCheckFinance() {
        printBalance();
    }

    // EFFECTS: prints the budget, total expense and balance of the account of the month
    private void printBalance() {
        System.out.println("Budget: $" + finance.getBudget());
        System.out.println("Total Expense: $" + finance.getTotalExpense());
        System.out.println("Balance: $" + finance.getBalance());
    }

    // EFFECTS: saves account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(finance);
            jsonWriter.close();
            System.out.println("Saved account to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            finance = jsonReader.read();

            System.out.println("Loaded account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
