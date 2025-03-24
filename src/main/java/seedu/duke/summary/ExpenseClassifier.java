package seedu.duke.summary;

import seedu.duke.expense.Expense;
import seedu.duke.storage.DataStorage;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class ExpenseClassifier {

    public ExpenseClassifier() {}

    public void calculateCategoryProportions() {
        List<Expense> expenses = DataStorage.loadExpenses();
        Map<Categories, Integer> categoryCount = new HashMap<>();

        for (Categories category : Categories.values()) {
            categoryCount.put(category, 0);
        }

        Map<Categories, List<String>> categoryKeywords = new HashMap<>();
        categoryKeywords.put(Categories.Food, Arrays.asList("restaurant", "groceries", "dining", "snack", "lunch", "dinner"));
        categoryKeywords.put(Categories.Shopping, Arrays.asList("clothes", "electronics", "mall", "fashion", "accessory"));
        categoryKeywords.put(Categories.Travel, Arrays.asList("flight", "hotel", "transport", "train", "taxi"));
        categoryKeywords.put(Categories.Entertainment, Arrays.asList("movie", "concert", "game", "party", "music"));


        for (Expense expense : expenses) {
            String title = expense.getTitle();
            String description = expense.getDescription().toLowerCase();
            boolean categorized = false;

            for (Map.Entry<Categories, List<String>> entry : categoryKeywords.entrySet()) {
                for (String keyword : entry.getValue()) {
                    if (title.contains(keyword) || description.contains(keyword)) {
                        categoryCount.put(entry.getKey(), categoryCount.get(entry.getKey()) + 1);
                        categorized = true;
                        break;
                    }
                }
                if (categorized) break;
            }

            if (!categorized) {
                categoryCount.put(Categories.Miscellaneous, categoryCount.get(Categories.Miscellaneous) + 1);
            }
        }

        int totalExpenses = expenses.size();
        if (totalExpenses == 0) {
            System.out.println("No expenses to calculate proportions.");
            return;
        }

        System.out.println("Category Proportions (" + expenses.size() + "):");
        for (Map.Entry<Categories, Integer> entry : categoryCount.entrySet()) {
            double percentage = (entry.getValue() / (double) totalExpenses) * 100;
            System.out.printf("%s: %.2f%%\n", entry.getKey(), percentage);
        }

        // Add data visualization stuff here
    }
}
