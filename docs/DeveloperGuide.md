# Developer Guide

---

## Table of Contents

---

[1. Introduction](#1-introduction) <br>
[2. Setup Guide](#2-setup-guide) <br>
&nbsp;&nbsp;[2.1 Prerequisites](#21-prerequisites) <br>
[3. Design](#3-design) <br>
&nbsp;&nbsp;[3.1.0 UI Class](#310-ui-class) <br>
&nbsp;&nbsp;[3.1.1 DataStorage Class](#311-datastorage-class) <br>
&nbsp;&nbsp;[3.1.2 GroupStorage Class](#312-groupstorage-class) <br>
&nbsp;&nbsp;[3.1.3 Commands Class](#313-commands-class) <br>
&nbsp;&nbsp;[3.1.4 ExpenseCommands Classes](#314-expensecommands-classes) <br>
&nbsp;&nbsp;[3.1.5 FriendsCommands Class](#315-friendscommands-class) <br>
&nbsp;&nbsp;[3.1.6 SplitCommand Class](#316-splitcommand-class) <br>
&nbsp;&nbsp;[3.1.7 BudgetManager Class](#317-budgetmanager-class) <br>
&nbsp;&nbsp;[3.1.8 Expense Class](#318-expense-class) <br>
&nbsp;&nbsp;[3.1.9 Friend Class](#319-friend-class) <br>
&nbsp;&nbsp;[3.2.0 Group Class](#320-group-class) <br>
&nbsp;&nbsp;[3.2.1 GroupManager Class](#321-groupmanager-class) <br>
&nbsp;&nbsp;[3.2.2 Messages Class](#322-messages-class) <br>
&nbsp;&nbsp;[3.2.3 Summary Class](#323-summary-class) <br>
&nbsp;&nbsp;[3.2.4 ExpenseClassifier Class](#324-expenseclassifier-class) <br>
&nbsp;&nbsp;[3.2.5 Currency Class](#325-currency-class) <br>
[4. Overall Application Architecture](#4-overall-application-architecture) <br>
&nbsp;&nbsp;[4.1 Application Class Diagram](#41-application-class-diagram) <br>
&nbsp;&nbsp;[4.2 Expense CRUD Feature](#42-expense-crud-feature) <br>
&nbsp;&nbsp;[4.3 Create Group Feature](#43-create-group-feature) <br>
&nbsp;&nbsp;[4.4 Split Expense Feature](#44-split-expense-feature) <br>
&nbsp;&nbsp;[4.5 Change Currency Feature](#45-change-currency-feature) <br>
&nbsp;&nbsp;[4.6 Data Visualization Feature](#46-data-visualization-feature) <br>
[5. Appendix](#5-appendix) <br>

## Acknowledgements

---

## 1. Introduction

---

O\$P\$ is an expense-tracker application that allows
the user to keep track of their expenditure with ease!
Users can also manage expenses in a group setting with
simple splitting functions, as well as view analytics
of their spending across multiple categories.

## 2. Setup Guide

---

### 2.1 Prerequisites

1.  Ensure you have Java 17 or above installed on your PC. **[Version 17 is preferred]**
2.  You may download [here](https://se-education.org/guides/tutorials/javaInstallationMac.html) for Mac users and [here](https://www.oracle.com/sg/java/technologies/downloads/) for Windows users.
3.  If you have it installed already, you may check it by running `java -version` in your terminal.
4.  Download the latest `.jar` file from here. **[link will be updated once v1 is ready]**
5.  Copy the file to the folder you want to use as the home folder for your **O$P$ budget tracking app** 🙂.
6.  Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar oSpS.jar` command to run the application.
7.  Type the command in the command box and press **Enter** to execute it.
8.  **Example:** Typing `help` and pressing **Enter** will open a mini window showing a list of all possible commands.
9.  Refer to the [features](https://docs.google.com/document/d/125Cg7wzuc4XFo3wsziwL2f64KN1uUfvFL5dIm6IQrSk/edit?tab=t.xl7ogrtj0a5q#heading=h.61o02m6y9xrc) section below for details on all commands and functionalities.

---

## 3. Design

---

### 3.1.0 UI Class

This class displays system output messages whenever an action has occurred or invoked by the user using the listed commands.

### Methods

#### `handleUserInput()`

This method continuously prompts the user for input and processes commands accordingly. It ensures that the application runs until the user chooses to exit.

#### Functionality:

1. Displays a prompt for the user to enter a command.
2. Reads user input if available.
3. Calls `processCommand` to handle the command.
4. Displays a divider after processing each command (if still running).
5. If no input is available, it displays an empty input message and stops execution.

### `processCommand()`

This method processes user input by mapping commands to their corresponding functionalities. The commands are predefined in the `Commands` class.

#### Functionality:

1. Trims and converts user input to lowercase.
2. Uses a `switch` statement to execute the appropriate method based on the input.
3. Handles multiple functionalities, including expense management, group operations, currency conversion, and data export.
4. Displays an error message for invalid commands.

### 3.1.1 DataStorage Class

The data storage class initializes the expense.txt file to store expenses created by the user on app start-up and its contents
are saved upon exit.

### File Handling

- **Data File:** The expense data is stored in `expenses.txt`.
- **Separator:** Each entry in the file is delimited using `|`.
- **Assertions:** Used to ensure correct program behavior.

### Methods

#### `ensureFileExists()`

Ensures that the data file exists. If the file does not exist, it is created.

- Uses `File.createNewFile()`.
- Prints a message if a new file is created.
- Handles `IOException` in case of errors.
- Assertion: Checks that the file exists after execution.

#### `saveExpenses(List<Expense> expenses)`

Saves a list of expenses to the data file.

- Iterates through `expenses` and writes each expense to `expenses.txt`.
- Each expense is stored with attributes separated by `|`.
- Returns `true` if saving is successful, `false` otherwise.
- Handles `IOException`.
- Assertion: Ensures `expenses` is not null before execution.

#### `loadExpenses()`

Loads expenses from the data file.

- Reads each line and splits it using `|`.
- Extracts attributes and creates `Expense` objects.
- Returns a list of `Expense` objects.
- Handles `FileNotFoundException`.

#### `resetExpenses()`

Clears all data in `expenses.txt`. Used mainly for testing.

- Writes an empty string to the file.
- Handles `IOException`.
- Assertion: Ensures the file is empty after reset.

### 3.1.2 GroupStorage Class

User-created groups are stored here, each containing the names of the members to be assigned an expense.

### 3.1.3 Commands Class

All approved commands that can be used within the application are stored here as macros. The UI class contains a switch-case block
that checks if the user input matches any of these commands and handles invalid commands.

#### Command List

Below are the commands supported by the application:

| Command            | Description                                    |
| ------------------ | ---------------------------------------------- |
| `HELP`             | Displays the list of available commands.       |
| `EXIT`             | Exits the application.                         |
| `ADD`              | Adds a new expense.                            |
| `LIST`             | Displays all recorded expenses.                |
| `DELETE`           | Deletes an expense.                            |
| `EDIT`             | Edits an existing expense.                     |
| `BALANCE`          | Shows the balance overview.                    |
| `SETTLED_LIST`     | Displays a list of settled expenses.           |
| `UNSETTLED_LIST`   | Displays a list of unsettled expenses.         |
| `MARK`             | Marks an expense as settled.                   |
| `UNMARK`           | Unmarks an expense as unsettled.               |
| `CREATE_GROUP`     | Creates a new group for expense sharing.       |
| `VIEW_GROUP`       | Views details of a specific group.             |
| `ADD_MEMBER`       | Adds a member to an existing group.            |
| `REMOVE_MEMBER`    | Removes a member from a group.                 |
| `REMOVE_GROUP`     | Deletes an entire group.                       |
| `VIEW_ALL_GROUPS`  | Displays all user-created groups.              |
| `SPLIT`            | Splits expenses among group members.           |
| `CHANGE_CURRENCY`  | Changes the default currency for transactions. |
| `DEFAULT_CURRENCY` | The default currency (SGD).                    |
| `SUMMARY`          | Displays an expense summary.                   |
| `EXPORT`           | Exports expense data.                          |

### 3.1.4 ExpenseCommands Classes

The ExpenseCommand class handles all expense-related operations in the application. It provides functionality for adding, deleting, editing, and managing expenses.

#### Adding Expenses

The `executeAddExpense()` method handles the addition of new expenses with the following features:

- Validates input fields (title, description, date, amount)
- Ensures date format is DD-MM-YYYY
- Prevents negative amounts
- Handles empty inputs gracefully
- Uses assertions to validate state

#### Deleting Expenses

The `executeDeleteExpense()` method manages expense deletion with these features:

- Validates expense index before deletion
- Updates owed amounts in the owesData.txt file
- Handles invalid indices gracefully
- Uses assertions to ensure valid state

#### Displaying Settled Expenses

The `displaySettledExpenses()` method manages the display of settled expenses with these features:

- Retrieves all expenses from budgetManager before filtering
- Checks for empty expense lists and displays an appropriate message
- Prints the current currency to ensure user is aware which currency the amounts are printed in
- Iterates through the expense list while filtering for settled expenses
- Handles edge cases gracefully, such as no settled expenses found
- Uses proper singular/plural formatting for the summary output

#### Displaying Unsettled Expenses

The `displayUnsettledExpenses()` method manages the display of unsettled expenses with these features:

- Retrieves all expenses from budgetManager before filtering
- Checks for empty expense lists and displays an appropriate message
- Ensures expenses are printed in the correct currency
- Iterates through the expense list while filtering for unsettled expenses
- Handles edge cases gracefully, such as no unsettled expenses found
- Uses proper singular/plural formatting for the summary output

#### Marking Expenses

The `executeMarkCommand()` method manages marking an expense as settled with these features:

- Prompts the user to enter an expense number for marking
- Trims and parses user input to handle potential formatting issues
- Attempts to mark the specified expense using budgetManager.markExpense()
- Handles invalid indices gracefully with an IndexOutOfBoundsException
- Handles non-numeric input with a NumberFormatException
- Provides clear error messages to guide the user toward valid input

#### Unmarking Expenses

The `executeUnmarkCommand()` method manages unmarking an expense as unsettled with these features:

- Prompts the user to enter an expense number for unmarking
- Trims and parses user input to handle potential formatting issues
- Attempts to unmark the specified expense using budgetManager.unmarkExpense()
- Handles invalid indices gracefully with an IndexOutOfBoundsException
- Handles non-numeric input with a NumberFormatException
- Provides clear error messages to guide the user toward valid input

#### Expense Summaries

The class provides comprehensive expense summary functionality:

1. Viewing Summaries:

   - Monthly Summary: Shows total expenses and count per month
   - Category-wise Summary: Shows total expenses and count per category
   - Interactive menu for choosing summary view
   - Formatted output with clear headers and separators

2. Exporting Summaries:

   - Export to text files (monthly_summary.txt, category_summary.txt)
   - Same formatting as view summaries
   - Error handling for file operations
   - Confirmation messages for successful exports

3. Data Organization:
   - Uses Maps to group and aggregate expenses
   - Maintains both totals and counts
   - Sorts data for better readability
   - Handles empty expense lists gracefully

#### Error Handling

Both methods implement comprehensive error handling:

- NumberFormatException for invalid number inputs
- IndexOutOfBoundsException for invalid indices
- FileNotFoundException for file operations
- General exception handling with user-friendly messages

#### Data Validation

The class uses assertions to validate:

- Non-null objects
- Valid indices
- Non-negative amounts
- Valid expense states

### 3.1.5 FriendsCommands Class

####

### 3.1.6 SplitCommand Class

The **SplitCommand** class is responsible for managing the splitting of expenses among group members.
It enables users to select an expense from a list and split that expense among a group either equally or through a manual assignment, either via absolute amounts or percentages.

---

#### Key Features

- **Expense Selection:**

  - Displays a list of available expenses.
  - Allows the user to choose an expense by entering its corresponding number.

- **Splitting Options:**

  - **Equal Split:**  
    Divides the selected expense evenly among all members of the chosen group.
  - **Manual Split:**  
    Requires the user to select a single splitting mode for the entire group:
    - **Absolute Mode:**
      - The user assigns a fixed monetary amount to each member.
    - **Percentage Mode:**
      - The user assigns a percentage for each member.

- **Automatic Balance Display:**
  - Once the split is completed, the command automatically shows cumilatively how much each member of the group owes, including from past splitting.
  - It uses the `owedAmounts.txt` file to sum all owed amounts for each member. It also displays the updated cumulative balance.

---

#### Implementation Details

- **User Input Validation:**

  - Validates the selected expense index and group name.
  - Ensures numeric inputs for amounts and percentages are valid.
  - Prevents assignments that exceed the remaining expense (in absolute mode) or cumulative percentage (in percentage mode).

- **File Handling and Persistence:**

  - Each valid assignment is appended in real time to the `owedAmounts.txt` file using a helper method.
  - The file maintains a persistent record of all split transactions.
  - When viewing a group, the system aggregates all entries per member to count the cumulative balance.

- **Integration with FriendsCommands:**

  - The SplitCommand class holds a reference to the **FriendsCommands** instance.
  - After completing the split, it calls the `viewGroupDirect(String groupName)` method to display the updated balances immediately.

- **Error Handling:**
  - Gracefully handles input errors (e.g., non-numeric values, attempts to over-assign amounts or percentages).
  - Provides clear error messages and re-prompts the user for valid input.

---

#### Workflow Summary

1. **Expense Selection:**

   - The user is presented with a list of expenses and selects one to split.

2. **Choosing a Split Option:**

   - **Equal Split:**
     - The expense is divided equally among the members of the specified group.
   - **Manual Split:**
     - The user selects one split method for the entire group (absolute or percentage).
     - **Absolute Mode:**
       - Each assignment reduces the remaining available amount.
       - The system displays the remaining unassigned amount after each entry.
     - **Percentage Mode:**
       - Each assignment reduces the available percentage from the total 100%.
       - The system displays the remaining percentage after each entry.

3. **Automatic Balance Update:**
   - After completing the split, the command automatically calls the view-group method to show each member's cumulative owed balance.
   - This balance is computed by aggregating all past and current assignments from `owedAmounts.txt`.

---

### 3.1.7 BudgetManager Class

The `BudgetManager` class manages a collection of expenses and provides operations to add, delete, edit, and retrieve expenses. It also integrates with the `DataStorage` class to persist expense data.

#### Expense Management

- **Storage:** Expenses are stored in a list and persisted in `expenses.txt` via `DataStorage`.
- **Validation:** Index checks ensure operations are performed on valid expenses.
- **Assertions:** Used to verify that expense counts and modifications are logically sound.

#### Methods

#### `BudgetManager()`

Initializes the `BudgetManager` with a list of expenses loaded from storage.

- Calls `DataStorage.loadExpenses()` to retrieve stored expenses.
- Initializes the `expenses` list.

#### `addExpense(Expense expense)`

Adds an expense to the list and saves it to storage.

- Appends the given `Expense` object to `expenses`.
- Calls `DataStorage.saveExpenses(expenses)`.

#### `deleteExpense(int index)`

Deletes an expense at the specified index.

- Validates the index before removal.
- Calls `DataStorage.saveExpenses(expenses)` after deletion.
- Returns the deleted `Expense`.
- Throws `IndexOutOfBoundsException` for invalid indices.

#### `editExpense(int index, String title, String description, String date, double amount)`

Modifies the attributes of an expense at the given index.

- Updates only non-null fields (title, description, date, amount).
- Calls `DataStorage.saveExpenses(expenses)` after modification.
- Throws `IndexOutOfBoundsException` if the index is invalid.

#### `getAllExpenses()`

Returns a copy of the list of all stored expenses.

- Prevents direct modification by returning a new `ArrayList`.

#### `getExpenseCount()`

Returns the total number of recorded expenses.

#### `getUnsettledExpenseCount()`

Counts the number of unsettled expenses.

- Iterates through `expenses` and counts those where `getDone()` is false.
- Assertion: Ensures count is non-negative.

#### `getTotalBalance()`

Calculates the total amount of unsettled expenses.

- Sums up the amounts of expenses where `getDone()` is false.
- Returns the total balance.

#### `getExpense(int index)`

Retrieves an expense at the specified index.

- Throws `IndexOutOfBoundsException` if index is invalid.

#### `markExpense(int index)`

Marks an expense as settled.

- Updates `setDone(true)` for the given expense.
- Saves the updated list using `saveAllExpenses()`.
- Throws `IndexOutOfBoundsException` for invalid indices.

#### `unmarkExpense(int index)`

Unmarks an expense, making it unsettled.

- Updates `setDone(false)` for the given expense.
- Saves the updated list using `saveAllExpenses()`.
- Throws `IndexOutOfBoundsException` for invalid indices.

#### `editExpenseCurrency(Double finalExchangeRate)`

Applies a currency exchange rate to all expenses.

- Multiplies each expense's amount by the exchange rate.
- Calls `editExpense()` to update each expense.
- Skips execution if `expenses` is empty.

#### `saveAllExpenses()`

Saves all expenses to persistent storage.

- Calls `DataStorage.saveExpenses(expenses)`.

#### `setExpenseAmountToZero(int index)`

Sets an expense's amount to 0.0.

- Retrieves the expense at `index`.
- Calls `setAmount(0.0)` and saves changes.
- Throws `IndexOutOfBoundsException` if the index is invalid.

### 3.1.8 Expense Class

### 3.1.9 Friend Class

### 3.2.0 Group Class

### Group Class

The `Group` class in the `seedu.duke.friends` package manages a collection of `Friend` objects under a specified group name.

#### Group Initialization

- **Constructor:** `Group(String name)`
- **Features:**
  - Sets the group name.
  - Initializes an empty list of `Friend` objects.

#### Adding Friends

- **Method:** `addFriend(Friend friend)`
- **Features:**
  - Adds a `Friend` object to the group.

#### Removing Friends

- **Method:** `removeFriend(String friendName)`
- **Features:**
  - Searches for the first friend with a matching name and removes them.
  - Returns `true` if removal is successful; otherwise returns `false`.
  - Note: Uses a for-each loop for removal, which may require caution regarding concurrent modifications.

#### Retrieving Friends

- **Method:** `getFriends()`
- **Features:**
  - Returns the list of `Friend` objects in the group.

#### Getting Group Name

- **Method:** `getName()`
- **Features:**
  - Returns the group's name.

#### String Representation

- **Method:** `toString()`
- **Features:**
  - Constructs a formatted string showing the group name and member names.
  - If the group is empty, calls `messages.displayEmptyGroupMessage()` to display an appropriate message.
  - **Important:** Ensure that the `messages` field is initialized externally to prevent a `NullPointerException`.

### 3.2.1 GroupManager Class

### 3.2.2 Messages Class

The `Messages` class provides standardized system messages for user interaction within the O$P$ expense-tracker application. It includes messages for system events, errors, user prompts, and notifications.

### Methods

#### `displayWelcomeMessage()`

Displays a welcome message when the application starts.

- Outputs: "Welcome to O$P$ expense-tracker! How can I help you?"
- Calls `setDivider()` to format the output.

#### `enterCommandMessage()`

Prompts the user to enter a command.

- Outputs: "Enter command: "

#### `emptyInputMessage()`

Handles cases where the user provides no input.

- Outputs: "No input detected. Exiting program..."

#### `setDivider()`

Displays a horizontal divider for visual separation.

- Outputs: A line of 80 underscores.

#### `errorMessageTag()` _(static)_

Returns the error message prefix.

- Returns: "ERROR: "

#### `invalidIndexMessage()`

Displays an error message for an invalid expense index.

- Returns: "Invalid expense index."

#### `displayExitMessage()`

Displays a farewell message when the user exits the application.

- Outputs: "Thank you for using the Expense Manager. Goodbye!"

#### `displayInvalidCommandMessage()`

Displays an error message for invalid user commands.

- Outputs: "Invalid command."

#### `createNewFileMessage()` _(static)_

Notifies the user when a new file is created.

- Outputs: "Created a new file!"

#### `displayEmptyGroupMessage()`

Displays a message when attempting to access a group with no members.

- Returns: "No members in this group."

#### `displayMissingFileExceptionMessage()`

Displays an error message when the owed amounts file is missing.

- Outputs: "Owed amounts file not found. No amounts to display."

#### `displayInvalidAmountExceptionMessage()`

Handles errors when parsing owed amounts.

- Outputs: "Error parsing owed amounts. Some amounts may not be displayed."

#### `displayMissingGroupMessage()`

Displays an error message when the specified group is not found.

- Outputs: "Group not found."

#### `displayCommandList()`

Displays the list of available commands along with their descriptions and usage examples.

- Prints a formatted command list to the console.
- Groups related commands for clarity.
- Provides details about user input expectations.

### 3.2.3 Summary Class

The Summary class manages the visualization and analysis of expense data. It provides comprehensive analytics through different views and formats.

#### Key Features:

- Generates monthly expense summaries with trend analysis
- Creates category-wise breakdowns of expenses
- Calculates percentage distributions across expense categories
- Provides comparative analysis between different time periods
- Supports data export functionality for external analysis
- Integrates with ExpenseClassifier for accurate categorization
- Maintains historical data for trend analysis

#### Implementation:

- Uses Java's built-in charting libraries for visualization
- Implements data aggregation algorithms for summary generation
- Provides both textual and graphical representation options
- Maintains data integrity through proper validation
- Supports multiple currency representations

### 3.2.4 ExpenseClassifier Class

The ExpenseClassifier class implements intelligent expense categorization through keyword analysis and pattern matching.

#### Categories and Keywords:

- Food: restaurant, cafe, grocery, meal, dinner, lunch, breakfast
- Travel: flight, hotel, taxi, transport, uber, grab
- Shopping: mall, store, shop, retail, clothing, electronics
- Entertainment: movie, concert, game, theatre, park
- Miscellaneous: other, misc, general, utility, service

#### Classification Logic:

- Primary keyword matching in expense titles
- Secondary context analysis in descriptions
- Fuzzy matching for similar terms
- Default categorization for unmatched expenses
- Confidence scoring for categorization accuracy
- Machine learning integration capability for improved accuracy

### 3.2.5 Currency Class

The Currency class handles all currency-related operations within the application. It provides functionality for managing currency codes, conversion rates, and performing currency conversions.

#### Currency Constructor

The `Currency()` method sets up the exchange rates for different currencies with these features:

- Initializes a map to store the exchange rates between different currencies.
- Attempts to read current currency from the file `./currentCurrency`
- Handles any file-related issues or data retrieval failures gracefully by providing default exchange rates when necessary.

#### Initialising Exchange Rates

The `initializeExchangeRates()` method initializes currency exchange rates by adding predefined exchange rates for various currencies

- Each currency code is mapped to its corresponding exchange rate value.
- The exchange rates are hardcoded and include values for a wide range of currencies (e.g., AED, AFN, ALL, AMD, etc.).
- Ensures that the exchangeRates map is populated with accurate conversion values for use in currency calculations.

#### Change Currency Method

The `changeCurrency()` method handles the currency change process with these features:

- Prompts the user to choose between entering their own exchange rate (option 1) or using an estimated exchange rate (option 2).
- Handles the user's input by checking if they input '1' or '2', guiding them to make a valid choice.
- If the user selects option 1, it asks for the currency code (based on ISO 4217 standard) and validates if the currency exists in the exchangeRates map.
- In option 1, the user is also prompted to enter a custom exchange rate, which is validated and parsed.
- If the user selects option 2, it retrieves the current exchange rate for the selected currency using the getExchangeRate() method.
- If a valid exchange rate is found for the new currency, it calculates the exchange rate relative to the current currency.
- Handles NumberFormatException gracefully and prompts the user to input a valid number if there are formatting issues.
- The method invokes editExpenseCurrency() to update the expense currency with the final exchange rate and the new currency.

#### Edit Expense Currency Method

The `editExpenseCurrency()` method manages the process of updating the expense currency with these features:

- Accepts a finalExchangeRate and a newCurrency as parameters to update the expense currency.
- Calls the budgetManager.editExpenseCurrency() method to apply the new exchange rate.
- Updates the currentCurrency to the new currency.
- Attempts to write the new currency to a file using the writeToFile() method.
- Handles potential IOException by catching the error and displaying a message if there's an issue with recording the change.
- Prints a success message confirming that the currency has been successfully changed to the new currency.

#### Write to File Method

The `writeToFile()` method handles writing the new currency to a file with these features:

- Accepts a newCurrency string as a parameter to be written to a file.
- Creates a FileWriter to open the file `./currentCurrency` in write mode, with the false parameter indicating that the file will be overwritten (not appended).
- Writes the newCurrency string to the file.
- Closes the FileWriter after writing the data to ensure resources are properly released.
- Declares the IOException to be thrown, which is handled by the calling method.

# 4. Overall Application Architecture

### 4.1 Application Class Diagram

### 4.2 Expense CRUD Feature

Below is the UML sequence diagram for the classes involved in the CRUD operations regarding user-created expenses. The main application class calls the constructor
for the UI class, which calls its own method `processCommand()` that processes the addition, editing, deletion and saving of expenses depending on specific user inputs
as shown in the diagram.

![ExpenseCRUDFeatureSequenceDiagram.drawio.png](diagrams/ExpenseCRUDFeatureSequenceDiagram.drawio.png)

### 4.3 Create Group Feature

### 4.4 Split Expense Feature

### 4.5 Change Currency Feature

### 4.6 Data Visualization Feature

The data visualization feature provides users with interactive and informative views of their expense patterns.

#### Architecture Components:

1. **Data Processing Layer**

   - Aggregates expense data from storage
   - Performs statistical calculations
   - Prepares data for visualization

2. **Visualization Engine**

   - Generates charts and graphs
   - Supports multiple visualization types
   - Handles currency formatting
   - Manages color schemes and layouts

3. **User Interface Layer**
   - Provides interactive controls
   - Handles view selection
   - Manages display preferences
   - Supports export functionality

#### Sequence Flow:

1. User requests visualization
2. System aggregates relevant data
3. Data is processed and categorized
4. Visualization engine generates appropriate charts
5. UI layer presents the visualization
6. User can interact with the visualization

[5. Appendix](#5-appendix) <br>
