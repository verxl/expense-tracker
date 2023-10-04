package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an expense (entry) with an amount, category, payment method and date of transaction
public class Expense implements Writable {

    private double amount;
    private String category;
    private String paymentMethod;
    private int dateOfTransaction;

    // CONSTRUCTOR
    // REQUIRES: amount > 0, category and payment are non-zero strings, date is a 6-digit integer (MM-DD-YY)
    // MODIFIES: this
    // EFFECTS: constructs an expense
    public Expense(double amount, String category, String payment, int date) {
        this.amount = amount;
        this.category = category;
        this.paymentMethod = payment;
        this.dateOfTransaction = date;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public int getDateOfTransaction() {
        return this.dateOfTransaction;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expense", amount);
        json.put("category", category);
        json.put("payment", paymentMethod);
        json.put("date", dateOfTransaction);
        return json;
    }
}
