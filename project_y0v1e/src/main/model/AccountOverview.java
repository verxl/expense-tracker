package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the account for the month with all the expenses, budget and total expense
public class AccountOverview implements Writable {
    protected ArrayList<Expense> expenses;
    private double budget;
    private double totalExpense;

    // CONSTRUCTOR
    // REQUIRES: budget > 0
    // MODIFIES: this
    // EFFECTS: constructs an account for all expenses and budget in one month
    public AccountOverview(double budget) {
        this.budget = budget;
        this.expenses = new ArrayList<>();
        this.totalExpense = 0;
        EventLog.getInstance().logEvent(new Event("Created a new account with budget " + budget));
    }

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public double getBudget() {
        return this.budget;
    }

    public double getTotalExpense() {
        return this.totalExpense;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds the given amount to budget
    public void addBudget(double amount) {
        this.budget += amount;
        EventLog.getInstance().logEvent(new Event("Added budget by " + amount));
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: decreases the budget by the given amount, if amount >= budget, reduce budget to 0
    public void reduceBudget(double amount) {
        if (amount < this.budget) {
            this.budget -= amount;
            EventLog.getInstance().logEvent(new Event("Reduced budget by " + amount));
        } else {
            this.budget = 0;
            EventLog.getInstance().logEvent(new Event("Reduced budget to zero."));
        }
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds the given amount to totalExpense
    public void addTotalExpense(double amount) {
        this.totalExpense += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: decreases the totalExpense by the given amount
    public void decreaseTotalExpense(double amount) {
        this.totalExpense -= amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: add an expense to expenses, decreases the budget of the month (order of expense not guaranteed)
    public void addExpense(double amount, String category, String payment, int date) {
        Expense newExpense = new Expense(amount, category, payment, date);
        expenses.add(newExpense);
        addTotalExpense(amount);
        EventLog.getInstance().logEvent(new Event("Recorded an expense of " + amount
                + " which is categorized as " + category + " paid by " + payment + " on " + date));
    }

    // REQUIRES: Expense that has an identical entry in expenses
    // MODIFIES: this
    // EFFECTS: delete the expense, adds the amount to budget (order of expense not guaranteed)
    public void removeExpense(double amount, String category, String payment, int date) {
        for (int i = 0; i < expenses.size(); i++) {
            Expense currentExpense = expenses.get(i);
            if (checkExpenseIdentity(currentExpense, amount, category, payment, date)) {
                expenses.remove(currentExpense);
                decreaseTotalExpense(amount);
                EventLog.getInstance().logEvent(new Event("Removed an expense of " + amount
                        + " which is categorized as " + category + " paid by " + payment + " on " + date));
            }
        }
    }

    // EFFECTS: returns the difference of budget and total expense
    public double getBalance() {
        return this.budget - this.totalExpense;
    }

    // EFFECTS: returns true if the difference of the budget and total expense <= 10, false otherwise
    public boolean needReminder() {
        double remainder = getBalance();
        return (remainder <= 10);
    }

    // EFFECTS: checks whether the two given expenses are identical, i.e. they have the same
    //          amount, category, payment method and date of transaction
    public boolean checkExpenseIdentity(Expense currentExpense,
                                        double amount, String category, String payment, int date) {
        return (currentExpense.getAmount() == amount
                && (currentExpense.getCategory().equals(category))
                && (currentExpense.getPaymentMethod().equals(payment))
                && currentExpense.getDateOfTransaction() == date);
    }

    // REQUIRES: a valid account
    // MODIFIES: this
    // EFFECTS: copies the account
    public void copyAccount(AccountOverview account) {
        expenses = new ArrayList<Expense>();
        expenses.addAll(account.getExpenses());
        budget = account.getBudget();
        totalExpense = account.getTotalExpense();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        json.put("budget", budget);
        json.put("total expense", totalExpense);
        return json;
    }

    // EFFECTS: returns expenses in this accountOverview as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense expense: expenses) {
            jsonArray.put(expense.toJson());
        }

        return jsonArray;
    }
}