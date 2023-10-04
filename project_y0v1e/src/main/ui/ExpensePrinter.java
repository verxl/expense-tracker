package ui;

import model.AccountOverview;

import javax.swing.*;

// Represents the JInternalFrame that prints an account (with its budget, expenses and total expense)

public class ExpensePrinter extends JInternalFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private JTextArea textArea;

    // CONSTRUCTOR
    // MODIFIES: this
    // EFFECTS: constructs a JInternalFrame
    public ExpensePrinter() {
        super("Account", false, true, false, false);
        setSize(WIDTH, HEIGHT);
        setLocation(WIDTH / 4, HEIGHT / 2);
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: prints all the expenses in the given account to the JTextArea with table head
    public void printAccountExpenses(AccountOverview account) {
        textArea.setText(textArea.getText() + "Budget: ");
        textArea.setText(textArea.getText() + account.getBudget() + "\n");
        textArea.setText(textArea.getText() + "Amount  Category  Payment Method  Date");
        for (int i = 0; i < account.getExpenses().size(); i++) {
            textArea.setText(textArea.getText() + "\n"
                    + account.getExpenses().get(i).getAmount() + " ; "
                    + account.getExpenses().get(i).getCategory() + " ; "
                    + account.getExpenses().get(i).getPaymentMethod() + " ; "
                    + account.getExpenses().get(i).getDateOfTransaction());
        }
        textArea.setText(textArea.getText() + "\n");
        textArea.setText(textArea.getText() + "Total Expense: ");
        textArea.setText(textArea.getText() + account.getTotalExpense());
    }
}
