package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExpense(Expense expense, double amount, String category, String payment, int date) {
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
        assertEquals(payment, expense.getPaymentMethod());
        assertEquals(date, expense.getDateOfTransaction());
    }
}
