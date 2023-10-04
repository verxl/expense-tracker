package persistence;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountOverview ao = new AccountOverview(0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            Assertions.fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            AccountOverview ao = new AccountOverview(0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountOverview.json");
            writer.open();
            writer.write(ao);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountOverview.json");
            ao = reader.read();
            assertEquals(0, ao.getBudget());
            assertEquals(0, ao.getTotalExpense());
            assertEquals(0, ao.getExpenses().size());
        } catch (IOException e) {
            fail("Exception should not be thrown.");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            AccountOverview ao = new AccountOverview(2000);
            ao.addExpense(30.5, "food", "credit", 102722);
            ao.addExpense(55.67, "entertainment", "debit", 102722);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountOverview.json");
            writer.open();
            writer.write(ao);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountOverview.json");
            ao = reader.read();
            assertEquals(2000, ao.getBudget());
            assertEquals(86.17, ao.getTotalExpense());
            assertEquals(2, ao.getExpenses().size());
            checkExpense(ao.getExpenses().get(0), 30.5, "food", "credit", 102722);
            checkExpense(ao.getExpenses().get(1), 55.67, "entertainment", "debit", 102722);
        } catch (IOException e) {
            fail("Exception should not be thrown.");
        }
    }
}
