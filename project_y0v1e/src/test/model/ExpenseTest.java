package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    private Expense testExpense;

    @BeforeEach
    void runBefore() {
        testExpense = new Expense(20, "food", "credit", 101322);
    }

    @Test
    void testConstructor() {
        assertEquals(20, testExpense.getAmount());
        assertEquals("food", testExpense.getCategory());
        assertEquals("credit", testExpense.getPaymentMethod());
        assertEquals(101322, testExpense.getDateOfTransaction());
    }
}
