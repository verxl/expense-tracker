package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class AccountOverviewTest {
    private AccountOverview testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new AccountOverview(500);
    }

    @Test
    void testConstructor() {
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(500, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testAddBudgetOnce() {
        testAccount.addBudget(300);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(800, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testAddBudgetMultiple() {
        testAccount.addBudget(100);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(600, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

        testAccount.addBudget(110.10);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(710.10, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

        testAccount.addBudget(10.25);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(720.35, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testReduceBudgetOnce() {
        testAccount.reduceBudget(300 );
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(200, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testReduceBudgetMultiple() {
        testAccount.reduceBudget(20);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(480, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

        testAccount.reduceBudget(49.55);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(430.45, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

        testAccount.reduceBudget(30.45);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(400, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testReduceBudgetNeg() {
        testAccount.reduceBudget(560);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(0, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

    }

    @Test
    void testAddTotalExpense() {
        testAccount.addTotalExpense(50.05);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(500, testAccount.getBudget());
        assertEquals(50.05, testAccount.getTotalExpense());
    }

    @Test
    void testDecreaseTotalExpense() {
        testAccount.decreaseTotalExpense(70.78);
        assertEquals(new ArrayList<Expense>(), testAccount.getExpenses());
        assertEquals(500, testAccount.getBudget());
        assertEquals(-70.78, testAccount.getTotalExpense());
    }

    @Test
    void testAddExpenseOnce() {
        testAccount.addExpense(156.45, "food", "credit", 101322);

        assertEquals(1, testAccount.expenses.size());
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(156.45, testAccount.getTotalExpense());
    }

    @Test
    void testAddExpenseMultiple() {
        testAccount.addExpense(156.45, "food", "credit", 101322);
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(156.45, testAccount.getTotalExpense());
        assertEquals(1, testAccount.expenses.size());

        testAccount.addExpense(20, "entertainment", "debit", 101422);
        assertEquals(20, testAccount.expenses.get(1).getAmount());
        assertEquals("entertainment", testAccount.expenses.get(1).getCategory());
        assertEquals("debit", testAccount.expenses.get(1).getPaymentMethod());
        assertEquals(101422, testAccount.expenses.get(1).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(176.45, testAccount.getTotalExpense());
        assertEquals(2, testAccount.expenses.size());

        testAccount.addExpense(2.50, "others", "cash", 101522);
        assertEquals(2.50, testAccount.expenses.get(2).getAmount());
        assertEquals("others", testAccount.expenses.get(2).getCategory());
        assertEquals("cash", testAccount.expenses.get(2).getPaymentMethod());
        assertEquals(101522, testAccount.expenses.get(2).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(178.95, testAccount.getTotalExpense());
        assertEquals(3, testAccount.expenses.size());
    }

    @Test
    void testRemoveExpenseOnce() {
        testAccount.addExpense(156.45, "food", "credit", 101322);

        testAccount.removeExpense(156.45, "food", "credit", 101322);
        assertEquals(new ArrayList<Expense>(), testAccount.expenses);
        assertEquals(0, testAccount.expenses.size());
        assertEquals(0, testAccount.getTotalExpense());
    }

    @Test
    void testRemoveExpenseTwice() {
        testAccount.addExpense(156.45, "food", "credit", 101322);
        testAccount.addExpense(20, "entertainment", "debit", 101422);
        testAccount.addExpense(2.50, "others", "cash", 101522);
        assertEquals(3, testAccount.expenses.size());
        assertEquals(178.95, testAccount.getTotalExpense());

        testAccount.removeExpense(20, "entertainment", "debit", 101422);
        assertEquals(2,testAccount.expenses.size());
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(2.50, testAccount.expenses.get(1).getAmount());
        assertEquals("others", testAccount.expenses.get(1).getCategory());
        assertEquals("cash", testAccount.expenses.get(1).getPaymentMethod());
        assertEquals(101522, testAccount.expenses.get(1).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(158.95, testAccount.getTotalExpense());

        testAccount.removeExpense(2.50, "others", "cash", 101522);
        assertEquals(1,testAccount.expenses.size());
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(156.45, testAccount.getTotalExpense());
    }

    @Test
    void testRemoveExpenseMultiple() {
        testAccount.addExpense(156.45, "food", "credit", 101322);
        testAccount.addExpense(20, "entertainment", "debit", 101422);
        testAccount.addExpense(2.50, "others", "cash", 101522);
        assertEquals(3, testAccount.expenses.size());
        assertEquals(178.95, testAccount.getTotalExpense());

        testAccount.removeExpense(20, "entertainment", "debit", 101422);
        assertEquals(2,testAccount.expenses.size());
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(2.50, testAccount.expenses.get(1).getAmount());
        assertEquals("others", testAccount.expenses.get(1).getCategory());
        assertEquals("cash", testAccount.expenses.get(1).getPaymentMethod());
        assertEquals(101522, testAccount.expenses.get(1).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(158.95, testAccount.getTotalExpense());

        testAccount.removeExpense(2.50, "others", "cash", 101522);
        assertEquals(1,testAccount.expenses.size());
        assertEquals(156.45, testAccount.expenses.get(0).getAmount());
        assertEquals("food", testAccount.expenses.get(0).getCategory());
        assertEquals("credit", testAccount.expenses.get(0).getPaymentMethod());
        assertEquals(101322, testAccount.expenses.get(0).getDateOfTransaction());
        assertEquals(500, testAccount.getBudget());
        assertEquals(156.45, testAccount.getTotalExpense());

        testAccount.removeExpense(156.45, "food", "credit", 101322);
        assertEquals(0,testAccount.expenses.size());
        assertEquals(new ArrayList<Expense>(), testAccount.expenses);
        assertEquals(500, testAccount.getBudget());
        assertEquals(0, testAccount.getTotalExpense());

    }

    @Test
    void testGetInitBalance() {
        assertEquals(500, testAccount.getBalance());
    }

    @Test
    void testGetBalanceWithExpense() {
        testAccount.addExpense(156.45, "food", "credit", 101322);
        assertEquals(343.55, testAccount.getBalance());
    }

    @Test
    void testNeedReminderFalse() {
        assertEquals(500, testAccount.getBalance());
        assertFalse(testAccount.needReminder());
    }

    @Test
    void testNeedReminderTrue() {
        testAccount.addExpense(491, "food","credit", 101322);
        assertEquals(9, testAccount.getBalance());
        assertTrue(testAccount.getBalance() <= 10);
        assertTrue(testAccount.needReminder());
    }

    @Test
    void testCheckExpenseIdentityTrue() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertTrue(testAccount.checkExpenseIdentity(expense1, 491, "food", "credit", 101322));
    }

    @Test
    void testCheckExpenseIdentityAmountFalse() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 156, "food", "credit", 101322));
    }

    @Test
    void testCheckExpenseIdentityCatFalse() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 491, "entertainment", "credit", 101322));
    }

    @Test
    void testCheckExpenseIdentityPaymentFalse() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 491, "food", "debit", 101322));
    }

    @Test
    void testCheckExpenseIdentityDateFalse() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 491, "food", "credit", 101922));
    }

    @Test
    void testCheckExpenseIdentityFalseTwice() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 491, "entertainment", "debit", 101322));
    }

    @Test
    void testCheckExpenseIdentityFalseMultiple() {
        Expense expense1 = new Expense(491, "food","credit", 101322);
        assertFalse(testAccount.checkExpenseIdentity(expense1, 300, "others", "cash", 101522));
    }

    @Test
    void testCopyAccount() {
        AccountOverview duplicateAccount = new AccountOverview(0);
        testAccount.addExpense(491, "food","credit", 101322);
        assertEquals(500, testAccount.getBudget());
        assertEquals(491, testAccount.getExpenses().get(0).getAmount());
        assertEquals("food", testAccount.getExpenses().get(0).getCategory());
        assertEquals("credit", testAccount.getExpenses().get(0).getPaymentMethod());
        assertEquals(101322, testAccount.getExpenses().get(0).getDateOfTransaction());
        assertEquals(491, testAccount.getTotalExpense());
        duplicateAccount.copyAccount(testAccount);
        assertEquals(500, duplicateAccount.getBudget());
        assertEquals(491, duplicateAccount.getExpenses().get(0).getAmount());
        assertEquals("food", duplicateAccount.getExpenses().get(0).getCategory());
        assertEquals("credit", duplicateAccount.getExpenses().get(0).getPaymentMethod());
        assertEquals(101322, duplicateAccount.getExpenses().get(0).getDateOfTransaction());
        assertEquals(491, duplicateAccount.getTotalExpense());
    }
}