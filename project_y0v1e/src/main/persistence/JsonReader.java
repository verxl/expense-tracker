package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Reference: JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)

// Represents a reader that reads accountOverview from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads accountOverview from file and returns it
    public AccountOverview read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountOverview(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parse accountOverview from JSON object and returns it
    private AccountOverview parseAccountOverview(JSONObject jsonObject) {
        double budget = jsonObject.getDouble("budget");
        AccountOverview ao = new AccountOverview(budget);
        addExpenses(ao, jsonObject);
        return ao;
    }

    // MODIFIES: ao
    // EFFECTS: parses expenses from JSON object and adds them to accountOverview
    private void addExpenses(AccountOverview ao, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json: jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(ao, nextExpense);
        }
    }

    // MODIFIES: ao
    // EFFECTS: parses expense from JSON object and adds it to accountOverview
    private void addExpense(AccountOverview ao, JSONObject jsonObject) {
        double expense = jsonObject.getDouble("expense");
        String category = jsonObject.getString("category");
        String payment = jsonObject.getString("payment");
        int date = jsonObject.getInt("date");
        ao.addExpense(expense, category, payment, date);
    }


}
