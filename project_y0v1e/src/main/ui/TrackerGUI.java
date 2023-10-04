package ui;

import model.AccountOverview;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// REFERENCE: AlarmSystem (https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git)

// Represents a GUI and its related components
public class TrackerGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private AccountOverview account;
    private JInternalFrame controlPanel;
    private JLabel icon;

    // CONSTRUCTOR
    // MODIFIES: this
    // EFFECTS: creates a JFrame and adds visual component
    public TrackerGUI() {

        account = new AccountOverview(0);

        controlPanel = new JInternalFrame("Control Panel", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());

        icon = new JLabel(new ImageIcon("./data/moneyIcon.jpeg"));

        setContentPane(icon);
        setTitle("Expense Tracker");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();
        addMenu();
        printLogWhenClose();

        controlPanel.pack();
        controlPanel.setVisible(true);

        icon.add(controlPanel);
        controlPanel.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds control button to the controlPanel
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.add(new JButton(new AddAction()));
        buttonPanel.add(new JButton(new RemoveAction()));
        buttonPanel.add(new JButton(new SetBudgetAction()));
        buttonPanel.add(new JButton(new PrintAction()));
        buttonPanel.add(new JButton(new SaveAction()));
        buttonPanel.add(new JButton(new LoadAction()));
        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar to JFrame
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        addMenuItem(fileMenu, new SaveAction(), null);
        addMenuItem(fileMenu, new LoadAction(), null);
        menuBar.add(fileMenu);

        JMenu expenseMenu = new JMenu("Expense");
        addMenuItem(expenseMenu, new AddAction(), null);
        addMenuItem(expenseMenu, new RemoveAction(), null);
        menuBar.add(expenseMenu);

        this.setJMenuBar(menuBar);
    }

    /**
     * Adds an item with given handler to the given menu
     *
     * @param theMenu     menu to which new item is added
     * @param action      handler for new menu item
     * @param accelerator keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    /**
     * Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Represents action to be taken when user wants to save the entries
     * to the account.
     */
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField fileNameBox = new JTextField();
            JPanel savePanel = newFilePanel(fileNameBox);

            int result = JOptionPane.showConfirmDialog(null, savePanel,
                    "Enter the file name to be saved: ", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String fileName = fileNameBox.getText() + ".json";
                boolean success = saveAccount(fileName);
                if (success) {
                    JOptionPane.showMessageDialog(null, fileName + " has been saved successfully.",
                            "Successfully Saved.", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, fileName + " has not been saved successfully.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to load all the entries
     * in the account.
     */
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField fileNameBox = new JTextField();
            JPanel loadPanel = newFilePanel(fileNameBox);

            int result = JOptionPane.showConfirmDialog(null, loadPanel,
                    "Enter the file name to be loaded: ", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String fileName = fileNameBox.getText() + ".json";
                boolean success = loadAccount(fileName);
                if (success) {
                    JOptionPane.showMessageDialog(null, fileName + " has been loaded successfully.",
                            "Successfully Saved.", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            fileName + " has not been loaded successfully. Please check if there is any typo.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //EFFECTS: returns a JPanel for save/load
    private JPanel newFilePanel(JTextField fileNameBox) {
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new GridLayout(2, 2));
        filePanel.add(new JLabel("file name:"));
        filePanel.add(new JLabel(""));
        filePanel.add(fileNameBox);
        filePanel.add(new JLabel(".json"));
        return filePanel;
    }

    // MODIFIES: this
    // EFFECTS: saves account to file
    private boolean saveAccount(String fileName) {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/" + fileName);
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private boolean loadAccount(String fileName) {
        try {
            JsonReader jsonReader = new JsonReader("./data/" + fileName);
            AccountOverview acc = jsonReader.read();
            account.copyAccount(acc);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Represents action to be taken when user wants to add an expense
     * to the account.
     */
    private class AddAction extends AbstractAction {

        AddAction() {
            super("Add Expense");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField amount = new JTextField();
            JTextField category = new JTextField();
            JTextField payment = new JTextField();
            JTextField date = new JTextField();

            JPanel expensePanel = newExpensePanel(amount, category, payment, date);

            int result = JOptionPane.showConfirmDialog(null, expensePanel,
                    "Input the expense.", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean failure = (amount.getText().isEmpty()
                        || category.getText().isEmpty() || payment.getText().isEmpty() || date.getText().isEmpty());
                if (! failure) {
                    account.addExpense(Double.parseDouble(amount.getText()),
                            category.getText(), payment.getText(), Integer.parseInt(date.getText()));
                    JOptionPane.showMessageDialog(null, "This expense has been recorded.",
                            "Successful entry!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "This expense has not been successfully recorded. Please check if there is any blank.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Represents action to be taken when user wants to remove an expense
     * from the account.
     */
    private class RemoveAction extends AbstractAction {

        RemoveAction() {
            super("Remove Expense");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField amount = new JTextField();
            JTextField category = new JTextField();
            JTextField payment = new JTextField();
            JTextField date = new JTextField();

            JPanel expensePanel = newExpensePanel(amount, category, payment, date);

            int result = JOptionPane.showConfirmDialog(null, expensePanel,
                    "Input the expense you want to remove.", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean failure = (amount.getText().isEmpty()
                        || category.getText().isEmpty() || payment.getText().isEmpty() || date.getText().isEmpty());
                if (! failure) {
                    account.removeExpense(Double.parseDouble(amount.getText()),
                            category.getText(), payment.getText(), Integer.parseInt(date.getText()));
                    JOptionPane.showMessageDialog(null, "This expense has been removed.",
                            "Successful removal!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "This expense has not been successfully removed. Please check if there is any typo.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //EFFECTS: returns a Panel for entering an expense entry
    private JPanel newExpensePanel(JTextField amount, JTextField category, JTextField payment, JTextField date) {
        JPanel expensePanel = new JPanel();
        expensePanel.setLayout(new GridLayout(4, 2));
        expensePanel.add(new JLabel("amount:"));
        expensePanel.add(amount);
        expensePanel.add(new JLabel("category:"));
        expensePanel.add(category);
        expensePanel.add(new JLabel("payment:"));
        expensePanel.add(payment);
        expensePanel.add(new JLabel("date:"));
        expensePanel.add(date);
        return expensePanel;
    }

    /**
     * Represents action to be taken when user wants to print all the expenses
     * in the account.
     */
    private class PrintAction extends AbstractAction {

        PrintAction() {
            super("Print");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            ExpensePrinter expensePrinter = new ExpensePrinter();
            add(expensePrinter);
            expensePrinter.printAccountExpenses(account);
        }
    }

    /**
     * Represents action to be taken when user wants to print all the expenses
     * in the account.
     */
    private class SetBudgetAction extends AbstractAction {

        SetBudgetAction() {
            super("Set Budget");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            JTextField budget = new JTextField();

            JPanel budgetPanel = newBudgetPanel(budget);

            int result = JOptionPane.showConfirmDialog(null, budgetPanel,
                    "Input your budget for the month.", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                boolean failure = (budget.getText().isEmpty());
                if (!failure) {
                    account.addBudget(Double.parseDouble(budget.getText()));
                    JOptionPane.showMessageDialog(null, "This month's budget has been set.",
                            "Be a wise consumer!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "This month's budget has been set at 0.",
                            "Be a wise consumer!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    // EFFECTS: returns a Panel for setting budget
    private JPanel newBudgetPanel(JTextField budget) {
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(new GridLayout(1, 1));
        budgetPanel.add(new JLabel("budget:"));
        budgetPanel.add(budget);
        return budgetPanel;
    }

    // EFFECTS: prints EventLog to console when the windows closes
    private void printLogWhenClose() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {

                printLog(EventLog.getInstance());

            }
        });
    }

    private void printLog(EventLog el) {
        for (Event next: el) {
            System.out.println(next.toString() + "\n");
        }
    }
}
