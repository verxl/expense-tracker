package persistence;

import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AccountOverview ao = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccountOverview() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountOverview.json");
        try {
            AccountOverview ao = reader.read();
            assertEquals(0, ao.getExpenses().size());
            assertEquals(0, ao.getBudget());
            assertEquals(0, ao.getTotalExpense());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountOverview() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountOverview.json");
        try {
            AccountOverview ao = reader.read();

            assertEquals(500, ao.getBudget());
            assertEquals(65.20, ao.getTotalExpense());

            ArrayList<Expense> expenses = ao.getExpenses();
            assertEquals(2, ao.getExpenses().size());
            checkExpense(expenses.get(0), 20, "food", "credit", 102622);
            checkExpense(expenses.get(1), 45.20, "entertainment", "debit", 102622);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
