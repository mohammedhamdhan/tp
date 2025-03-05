package seedu.duke.storage;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import seedu.duke.messages.Messages;

public class DataStorage {
    public static final String DATA_FILE = "C:\\Users\\user\\tp-1\\data.txt";

    public static void ensureFileExists() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            throw new IllegalStateException(Messages.errorMessageTag()
                    + Messages.missingTaskFileErrorMessage()
                    + file.getAbsolutePath());
        }
    }


    public static List<String> loadData() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println(Messages.errorMessageTag() + e.getMessage());
        }
        return data;
    }

    public static void displayStoredData(String filePath) {
        Messages.loadDataMessage(filePath);
        List<String> data = loadData();
        if (data.isEmpty()) {
            Messages.emptyDataFileMessage();
        } else {
            for (String line : data) {
                System.out.println(line);
            }
        }
    }
}
