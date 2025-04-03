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
&nbsp;&nbsp;[5.1 Product Scope](#51-product-scope) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[5.1.1 Target User Profile](#511-target-user-profile) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[5.1.2 Value Proposition](#512-value-proposition) <br>
&nbsp;&nbsp;[5.2 User Stories](#52-user-stories) <br>
&nbsp;&nbsp;[5.3 Non-Functional Requirements](#53-non-functional-requirements) <br>
&nbsp;&nbsp;[5.4 Glossary](#54-glossary) <br>
&nbsp;&nbsp;[5.5 Test Cases](#55-test-cases) <br>

---

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

| Command             | Description |
|---------------------|-------------|
| `HELP`             | Displays the list of available commands. |
| `EXIT`             | Exits the application. |
| `ADD`              | Adds a new expense. |
| `LIST`             | Displays all recorded expenses. |
| `DELETE`           | Deletes an expense. |
| `EDIT`             | Edits an existing expense. |
| `BALANCE`          | Shows the balance overview. |
| `SETTLED_LIST`     | Displays a list of settled expenses. |
| `UNSETTLED_LIST`   | Displays a list of unsettled expenses. |
| `MARK`             | Marks an expense as settled. |
| `UNMARK`           | Unmarks an expense as unsettled. |
| `CREATE_GROUP`     | Creates a new group for expense sharing. |
| `VIEW_GROUP`       | Views details of a specific group. |
| `ADD_MEMBER`       | Adds a member to an existing group. |
| `REMOVE_MEMBER`    | Removes a member from a group. |
| `REMOVE_GROUP`     | Deletes an entire group. |
| `VIEW_ALL_GROUPS`  | Displays all user-created groups. |
| `SPLIT`            | Splits expenses among group members. |
| `CHANGE_CURRENCY`  | Changes the default currency for transactions. |
| `DEFAULT_CURRENCY` | The default currency (SGD). |
| `SUMMARY`          | Displays an expense summary. |
| `EXPORT`           | Exports expense data. |

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

#### Finding specific Expenses

The `findExpense()` method in the `seedu.duke.budget` package is responsible for searching and displaying expenses that match a given keyword. It helps users efficiently locate expenses by title or description.

- Prompts the user to enter a keyword to search for expenses.
- Validates that the keyword is not empty.
- Retrieves all stored expenses using `budgetManager.getAllExpenses()`.
- Filters expenses whose title or description contains the keyword (case-insensitive).
- Displays the list of matching expenses, if any.

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

The FriendsCommands class handles all friends and groups related operations in the application. It provides functionality for adding, deleting, and managing friend groups.

#### Checking if a group/member name is valid
The `isValidName()` method is a utility function used to validate input strings for group names and member names. It ensures that the input follows specific naming conventions and helps maintain data integrity.
- **Input validation:**
    - Accepts a single `String` parameter `name`.

- **Null and empty check:**
    - Method checks if the input is `null` or an empty string after trimming the whitespace.
    - If either condition is met, the method returns `false`.

- **Name format validation**
    - Uses a regular expression to validate that the name contains only letters (both uppercase and lowercase), numbers, and spaces.
    - If all conditions are met, the method returns `true`
    - If even one is violated, it returns `false`.

#### Creating a new group

The `createGroup()` method is responsible for creating a new group within the application. It follows a user-driven input process to define the group name and add members.

- **Group name Input:**
    - The method prompts the user to enter a group name.
    - The input is validated using the `isValidName()` method.
    - If name is invalid, the user is prompted to enter a name again.

- **Adding Group Members:**
    - Once a valid group name is added, user is prompted to add members.
    - User can continue to input multiple member names, each name is validated by the `isValidName()` method.
    - Process continues until user types 'done'.
    - Each valid member name is used to create a new `Friend` object, which is then added to the group using `groupManager.addFriendToGroup()`.

- **Saving the Group:**
    - Once the group creation is complete, the method saves the group using `groupManager.saveGroups()`
    - A success message is displayed upon creation.

#### Remove a Group

The `removeGroup()` is used to delete an entire group from the group management system.

- **Input:**
    - Prompts the user to enter the name of the group they want to remove.
    - Trims any leading or trailing whitespaces from the input.

- **Group Existence Check:**
    - Uses `groupManager.groupExists(groupName)` to verify whether the specified group exists.
    - If group does not exist, the method prints "Group does not exist."

- **Delete confirmation:**
    - Before removal, the method prompts the user to confirm: "Are you sure you want to remove [groupName]? (yes/no)"
    - If the user does not input "yes", the operation to remove is cancelled.

- **Group Removal:**
    - If confirmed, the method calls `groupManager.removeGroup(groupName)` to remove the group from the group management system. 
    - The updated group list is saved using `groupManager.saveGroups()`.


#### Viewing an existing group

The `viewGroup()` method is responsible for displaying the details of a specific group, it includes its members and associated expenses.
This method is essential for users who wish to view group details and any expenses related to group members.

- **Input:**
    - Prompts user to enter the group name that they want to view.
    - Trims any extra whitespaces from the input.

- **Group Existence Check:**
    - Uses the `groupManager.groupExists()` method to check whether the specified group exists. 
    - If the group does not exist, the method prints a "Group not found" message and terminates.

- **Loading Expense Data:**
    - The method reads from the `owedAmounts.txt` file, which contains expense data.
    - It loads this data into a map owedAmounts, where:
      - The key is the member's name.
      - The value is the accumulated amount they owe. 
    - The method ensures that the amounts are accumulated for each member instead of being overwritten.

- **Display Group Members and Expenses:**
    - Uses groupManager.getGroupMembers(groupName) to fetch the list of group members. 
    - If the group has no members, it displays "No members in this group."
    - For each member, it:
      - Retrieves their name. 
      - Checks the owedAmounts map for any recorded expenses. 
      - Displays the member’s name along with the accumulated expense amount. 
    - If a member has no recorded expense, the amount displayed is 0.00.

#### Viewing all user's Groups

The `viewAllGroups()` method is designed to display a list of all the groups that the user has created or is a part of. It provides a quick overview of the existing groups managed by the application.

- **Check for existing groups:**
    - Uses `groupManager.getGroups()` to retrieve a list of all groups.
    - If the list is empty, the method prints "You have no groups".

- **Displaying Groups:**
  - If the list is not empty, the method iterates over each group and prints its details using the `toString()` method of the Group class. 
  - This allows the user to see a comprehensive list of all group names and any other associated information that the Group class's `toString()` method returns.

#### Viewing group directly


#### Add a member

The `addMember()` method allows the user to add a new member to an existing group. If the specified group does not exist, the method offers the option to create the group and add the member simultaneously.

- **Input:**
  - Member name:
    - Prompts the user to enter the name of the member they want to add. 
    - Uses isValidName() to validate the input, ensuring it does not contain special characters or empty spaces.
    - Repeats the prompt until a valid name is entered.
  - Group name:
    - Prompts the user to enter the group name to which the member should be added. 
    - Similarly, the name is validated to avoid empty or invalid names.

- **Group Existence Check:** 
  - Uses `groupManager.groupExists(groupName)` to verify whether the specified group already exists.

- **Adding Member to existing group:**
  - If the group exists:
    - Uses `groupManager.addFriendToGroup(groupName, new Friend(name, groupName))` to add the member. 
    - Calls `groupManager.saveGroups()` to save the updated group data. 
    - Displays a success message indicating that the member has been added to the group.

- **Handling non-existent groups:**
  - If the group does not exist:
    - Asks the user if they would like to create the group. 
  - If the user enters "yes":
    - Creates the group and adds the member directly.
    - Saves the new group and member data. 
    - Displays a message confirming the creation and addition.
  - If the user enters "no":
    - Cancels the operation and notifies the user that the member was not added.

#### Remove a member

The `removeMember()` method allows the user to add a new member to an existing group. If the specified group does not exist, the method offers the option to create the group and add the member simultaneously.

- **Input:**
    - Member name:
        - Prompts the user to enter the name of the member they want to remove.
        - Trims any leading or trailing whitespaces from the input.
    - Group name:
        - Prompts the user to enter the group name to which the member should be removed.
        - Trims whitespace for the input.

- **Group Existence Check:**
    - Uses `groupManager.groupExists(groupName)` to verify whether the specified group exists.
    - If group does not exist, the method prints "Group does not exist."

- **Delete confirmation:**
    - Before removal, the method prompts the user to confirm: "Are you sure you want to remove [memberName] from [groupName]? (yes/no)"
    - If the user does not input "yes", the operation to remove is cancelled. 

- **Member Removal:**
    - If confirmed, the method iterates through the list of groups returned by `groupManager.getGroups()`. 
    - Locates the specified group by comparing the group name. 
    - Uses the `removeFriend()` method to attempt to remove the specified member from the group. 
    - If successful, it sets the removed flag to true and breaks out of the loop.

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
   - After completing the split, the command automatically calls the view-group method to show each member’s cumulative owed balance.
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
- Multiplies each expense’s amount by the exchange rate.
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

The `Friend` class in the `seedu.duke.friends` package represents an individual member within a group. It stores essential information about the friend, including their name and the group they belong to.

#### Friend Initialization
- **Constructor:** `Friend(String name, String group)`
- **Features:**
  - Initializes the `name` and `group` fields with the provided values.
  - Represents a friend as part of a specific group.

#### Getting Friend's Name
- **Method:** `getName()`
- **Features:**
  - Returns the name of the friend.
  - Provides a way to access the friend's name for display or processing.

#### Getting Friend's Group
- **Method:** `getGroup()`
- **Features:**
  - Returns the group to which the friend belongs.
  - Useful for organizing friends by their associated groups.

#### Design Considerations
- The class follows the **Single Responsibility Principle (SRP)**, focusing solely on holding friend-related data.
- Encapsulation is maintained through private attributes and public getter methods.
- The class is designed to be lightweight and efficient for group management operations.


### 3.2.0 Group Class

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
   - Returns the group’s name.

#### String Representation
- **Method:** `toString()`
- **Features:**
   - Constructs a formatted string showing the group name and member names.
   - If the group is empty, calls `messages.displayEmptyGroupMessage()` to display an appropriate message.
   - **Important:** Ensure that the `messages` field is initialized externally to prevent a `NullPointerException`.

### 3.2.1 GroupManager Class

The `GroupManager` class in the `seedu.duke.friends` package is responsible for managing groups and their members. It provides methods to add members, check group existence, retrieve group members, remove groups, and persist group data.

#### GroupManager Initialization
- **Constructor:** `GroupManager()`
- **Features:**
  - Initializes the list of groups by loading existing groups from the storage using `GroupStorage.loadGroups()`.
  - Uses the `Messages` object to display user messages.

---

#### Adding a Friend to a Group
- **Method:** `addFriendToGroup(String groupName, Friend friend)`
- **Features:**
  - Searches for an existing group with the given name.
  - If found, adds the `Friend` object to that group.
  - If not found, creates a new group with the specified name and adds the friend.
  - Ensures that the group is updated efficiently by adding the friend directly to the existing group if possible.

---

#### Checking Group Existence
- **Method:** `groupExists(String groupName)`
- **Features:**
  - Iterates through the list of groups to check whether a group with the specified name exists.
  - Returns `true` if the group is found, otherwise returns `false`.

---

#### Retrieving Group Members
- **Method:** `getGroupMembers(String groupName)`
- **Features:**
  - Searches for the specified group by name.
  - If found, returns a list of `Friend` objects from that group.
  - If not found, returns an empty list to indicate that the group does not exist.

---

#### Removing a Group
- **Method:** `removeGroup(String groupName)`
- **Features:**
  - Removes the specified group using a `removeIf` lambda function for efficiency.
  - If the group is successfully removed, it calls `saveGroups()` to persist changes.
  - Displays a success message if the group is deleted, otherwise uses `messages.displayMissingGroupMessage()` to indicate that the group was not found.

---

#### Saving Groups
- **Method:** `saveGroups()`
- **Features:**
  - Saves the current list of groups to the storage using `GroupStorage.saveGroups(groups)`.
  - Ensures that changes made to group structures are persistent across application sessions.

---

#### Retrieving All Groups
- **Method:** `getGroups()`
- **Features:**
  - Returns the current list of groups.
  - Useful for displaying all available groups in the application.

---

#### Design Considerations
- The class follows the **Single Responsibility Principle (SRP)**, focusing on group management while delegating storage operations to the `GroupStorage` class.
- Ensures that any modification to the group list is followed by a save operation to maintain data consistency.
- Uses the **Strategy Pattern** for loading and saving groups, allowing flexibility in storage implementations.
- Avoids group duplication by checking for existing groups before adding a

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

#### `errorMessageTag()` *(static)*
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

#### `createNewFileMessage()` *(static)*
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
This class centralizes user-facing messages, improving consistency and readability within the application. Static methods are used for messages that do not depend on instance data.

### 3.2.4 ExpenseClassifier Class

The auxiliary class for calculating the relative percentages of the expense categories: Food, Travel, Shopping, Entertainment and Miscellaneous by checking if the expense titles
and descriptions in the expense.txt file contain certain keywords related to the aforementioned categories. These percentages will then be displayed in charts upon request by the
user.

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
- Handles potential IOException by catching the error and displaying a message if there’s an issue with recording the change.
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

# 5. Appendix

## 5.1 Product scope

### 5.1.1 Target user profile
Groups of students traveling together who need a straightforward and accurate way to split costs and track their overall trip budget. Whether paying for accommodations, 
dining, transportation, or activities, these users want a single, hassle‐free solution to manage shared expenses, keep everyone on the same page, even across multiple 
currencies and minimize time spent on complicated payment calculations.

### 5.1.2 Value proposition
When friends travel together, they often struggle to split costs and track expenses for accommodations, dining, transportation, and activities. Relying on makeshift spreadsheets, 
group chat records, or mental math leads to confusion about who owes what, resulting in financial strain and potential conflict. Travelers need a unified budgeting and expense‐splitting 
solution that accurately records costs, updates balances in real time, and keeps everyone informed—allowing them to focus on enjoying their trip rather than worrying about the numbers.

## 5.2 User Stories

| As a...                                       | I want...                                                                                                     | So that I can...                                                             |
|-----------------------------------------------|---------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------|
| traveler                                      | add expenses easily with categories                                                                           | keep track of spending                                                       |
| traveler                                      | enter expenses in different currencies                                                                        | accurately track international transactions                                  |
| traveler                                      | see a summary of all expenses                                                                                 | understand the total trip cost at a glance                                   |
| attendee of a social gathering                | know how much I owe people                                                                                    | pay them the correct amount                                                  |
| user                                          | set a daily or overall trip budget                                                                            | track spending in real time and avoid overshooting financial limits          |
| traveler                                      | split expenses among group members                                                                            | fairly distribute costs                                                      |
| traveler                                      | see how much each person owes or is owed                                                                      | settle payments easily                                                       |
| traveler                                      | the system to automatically calculate balances after each expense                                             | not have to do the math myself                                               |
| traveler                                      | enter expenses in multiple currencies                                                                         | track spending across different countries                                    |
| traveler                                      | the system to convert expenses to a base currency                                                             | see all amounts in a consistent format                                       |
| traveler                                      | manually update exchange rates                                                                                | adjust based on real-time rates when needed                                  |
| traveler                                      | save all my expenses to a .txt file                                                                           | keep a record of my trip’s finances                                          |
| traveler                                      | reload past trips from a .txt file                                                                            | review old expenses                                                          |
| traveler                                      | export a final balance report                                                                                 | share it with my group members                                               |
| traveler                                      | a simple and intuitive interface                                                                              | quickly add and manage expenses                                              |
| traveler                                      | filter expenses by category or person                                                                         | analyze spending patterns                                                    |
| traveler                                      | add an expense with a title, description, date and amount                                                     | track my spending                                                            |
| traveler                                      | assign an expense to a specific group member                                                                  | know who paid for what                                                       |
| traveler                                      | delete an expense                                                                                             | remove incorrect entries                                                     |
| traveler                                      | edit an existing expense                                                                                      | rectify mistakes                                                             |
| traveler                                      | list all expenses in chronological order                                                                      | review my spending history                                                   |
| traveler                                      | filter expenses by category (e.g., food, transport)                                                           | analyze specific spending habits in these categories                         |
| traveler                                      | filter expenses by payer                                                                                      | see who has spent the most                                                   |
| traveler                                      | filter expenses by a specific date range                                                                      | track spending over time                                                     |
| traveler                                      | view a summary of total expenses                                                                              | see my trip's overall cost                                                   |
| student traveler                              | label expenses under custom categories like “museum tickets,” “school supplies,” or “night out”               | see where I’m spending the most and adjust my budget accordingly             |
| traveler                                      | the system to calculate how much each person owes after an expense is added                                   | not have to do the math myself                                               |
| traveler                                      | see an individual balance for each person                                                                     | check who owes whom                                                          |
| traveler                                      | manually mark/unmark an amount as settled                                                                     | keep track of paid debts                                                     |
| traveler                                      | the app to suggest the simplest way to settle debts                                                           | minimize transactions                                                        |
| traveler                                      | see a list of all unsettled expenses                                                                          | know what still needs to be paid                                             |
| traveler                                      | an option to divide expenses equally among all members                                                        | ensure everyone pays the same share                                          |
| traveler                                      | an option to enter custom split percentages                                                                   | allocate costs based on individual contributions                             |
| traveler who might explore multiple countries | the option to enter expenses in different currencies                                                          | track international spending                                                 |
| traveler                                      | the system to store exchange rates                                                                            | convert expenses accurately and do not need to enter exchange rates manually |
| traveler                                      | see all expenses converted to a single base currency                                                          | compare costs consistently                                                   |
| traveler                                      | export my trip’s expense data to a .txt file                                                                  | keep a record                                                                |
| traveler                                      | import a .txt file to reload past trips                                                                       | review previous expenses                                                     |
| traveler                                      | save my progress automatically                                                                                | not lose my data when I close the app                                        |
| traveler                                      | view a summary report of all expenses in multiple views (monthly, category-wise) before exporting             | review it first                                                              |
| traveler                                      | the summary report categorize my expenses into categories automatically without needing to input the category | simplify expense management                                                  |
| traveler                                      | add friend(s) to my list                                                                                      | settle my debts with them                                                    |
| traveler                                      | see a clear menu of commands                                                                                  | know how to use the app                                                      |
| traveler                                      | use a simple command-line interface to interact with the app                                                  | efficiently manage my data                                                   |
| traveler                                      | receive error messages if I enter an invalid command                                                          | fix mistakes                                                                 |
| traveler                                      | confirm before deleting an expense                                                                            | not accidentally lose data                                                   |
| traveler                                      | a search function to find expenses based on keywords                                                          | quickly locate past transactions                                             |
| traveler                                      | shortcut commands for frequent actions                                                                        | use the app more efficiently                                                 |
| traveler                                      | view my balance at any time with a single command                                                             | check how much I owe                                                         |
| traveler                                      | the app to have an easy to understand UI                                                                      | easily navigate my data                                                      |
| traveler                                      | start a new trip                                                                                              | track expenses separately for different trips                                |
| traveler                                      | view a list of past trips                                                                                     | revisit my previous expenses                                                 |
| traveler                                      | delete a trip along with its expenses                                                                         | remove old or test data                                                      |

## 5.3 Non-Functional Requirements
This application can be run on any *mainstream OS* as long as it has java`17` or above installed.

## 5.4 Glossary
* *Mainstream OS* - Windows, Linux, Unix, macOS

## 5.5 Test Cases
This section documents the test cases for the application. Each test case describes the input commands, the expected behavior, and the corresponding output from the system.

---

### **Test Case 1: Adding a New Expense**
- **Purpose**: Verify that the user can successfully add a new expense.
- **Input**:
  ```
  add
  Breakfast
  1x Big Breakfast from McDonald's
  01-01-2025
  10.00
  ```
- **Expected Output**:
  ```
  Expense added successfully:
  Title: Breakfast
  Description: 1x Big Breakfast from McDonald's
  Date: 01-01-2025
  Amount: 10.00
  ```
- **Behavior**:
    - The program prompts the user for the title, description, date, and amount of the expense.
    - Upon successful addition, the program confirms the details of the newly added expense.

---

### **Test Case 2: Listing All Expenses**
- **Purpose**: Verify that the program correctly lists all expenses.
- **Input**:
  ```
  list
  ```
- **Expected Output**:
  ```
  All expenses are in SGD
  List of Expenses:
  Expense #1
  Title: Breakfast
  Description: 1x Big Breakfast from McDonald's
  Date: 01-01-2025
  Amount: 10.00
  ```
- **Behavior**:
    - The program displays all stored expenses with their respective details.
    - If no expenses exist, the program outputs "No expenses found."

---

### **Test Case 3: Editing an Existing Expense**
- **Purpose**: Verify that the user can edit an existing expense.
- **Input**:
  ```
  edit
  1
  (press Enter to keep current title)
  (press Enter to keep current description)
  31-12-2025
  9.00
  ```
- **Expected Output**:
  ```
  Expense edited successfully:
  Title: Breakfast
  Description: 1x Big Breakfast from McDonald's
  Date: 31-12-2025
  Amount: 9.00
  ```
- **Behavior**:
    - The program prompts the user to select an expense by index.
    - The user can modify the title, description, date, and amount or press Enter to retain the current value.
    - Upon successful editing, the program confirms the updated details.

---

### **Test Case 4: Deleting an Expense**
- **Purpose**: Verify that the user can delete an existing expense.
- **Input**:
  ```
  delete
  1
  yes
  ```
- **Expected Output**:
  ```
  Are you sure you want to delete this expense? (yes/no)
  Title: Supper
  Description: 10x Egg Prata from SpringLeaf
  Date: 02-01-2025
  Amount: 25.00
  Expense deleted successfully:
  Title: Supper
  Description: 10x Egg Prata from SpringLeaf
  Date: 02-01-2025
  Amount: 25.00
  ```
- **Behavior**:
    - The program prompts the user to confirm the deletion of the selected expense.
    - Upon confirmation, the expense is removed, and the program confirms the deletion.

---

### **Test Case 5: Viewing Balance Overview**
- **Purpose**: Verify that the program displays the balance overview correctly.
- **Input**:
  ```
  balance
  ```
- **Expected Output**:
  ```
  Balance Overview
  ----------------
  Total number of unsettled expenses: 1
  Total amount owed: $25.00
  ```
- **Behavior**:
    - The program calculates and displays the total number of unsettled expenses and the total amount owed.

---

### **Test Case 6: Handling Invalid Commands**
- **Purpose**: Verify that the program handles invalid commands gracefully.
- **Input**:
  ```
  invalidcommand
  ```
- **Expected Output**:
  ```
  Invalid command.
  ```
- **Behavior**:
    - The program informs the user that the entered command is invalid and does not crash.

---

### **Test Case 7: Exiting the Program**
- **Purpose**: Verify that the program exits cleanly when the user issues the `exit` command.
- **Input**:
  ```
  exit
  ```
- **Expected Output**:
  ```
  Thank you for using the Expense Manager. Goodbye!
  ```
- **Behavior**:
    - The program terminates after displaying a farewell message.

---

### **Test Case 8: Displaying Help Information**
- **Purpose**: Verify that the program provides a comprehensive help message.
- **Input**:
  ```
  help
  ```
- **Expected Output**:
  ```
  AVAILABLE COMMANDS:
  ------------------
  help
    Description: Displays this help message
    Usage: help
  ...
  ```
- **Behavior**:
    - The program lists all available commands along with their descriptions and usage instructions.

---

### **Test Case 9: Editing an Expense with Invalid Index**
- **Purpose**: Verify that the program handles invalid expense indices during editing.
- **Input**:
  ```
  edit
  0
  ```
- **Expected Output**:
  ```
  Please enter a valid expense number.
  ```
- **Behavior**:
    - The program prompts the user to enter a valid expense index.

---

### **Test Case 10: Deleting an Expense with Invalid Index**
- **Purpose**: Verify that the program handles invalid expense indices during deletion.
- **Input**:
  ```
  delete
  0
  ```
- **Expected Output**:
  ```
  Please enter a valid expense number.
  ```
- **Behavior**:
    - The program prompts the user to enter a valid expense index.

---
