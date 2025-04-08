# Asher Tan - Project Portfolio Page

## Overview

---

O$P$ is an expense-tracker application that empowers users to manage their spending efficiently. Among its many features, the expense splitting functionality plays a crucial role in helping groups share costs seamlessly. My primary contribution to the project is the design and implementation of the **SplitCommand** class, which handles the logic for dividing expenses among group members.

## Summary of Contributions

---

### Code Contributed
Code Contribution to the project: https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=asher&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21

### Features Implemented
Here is the list of features implemented:

#### 1. Split Expense Feature

- **Equal Split:**  
  Automatically divides the selected expense evenly among all members of a specified group.
- **Manual Split:**  
  Offers two modes:
  - **Absolute Mode (/a):** Users assign fixed monetary values to each member.
  - **Percentage Mode (/p):** Users specify the share as a percentage of the total expense.
- **Checks and Assertions:**  
  Includes assertions and input checks to ensure that:
  - The expense list and group data are valid.
  - User inputs do not exceed available amounts or percentages.
  - The input stream is not prematurely exhausted.
- **Balance Update:**  
  After a split, the updated balances are immediately displayed by invoking the group view functionality.

#### 2. Enhanced Testing for Split Functionality

- Developed comprehensive unit tests to cover:
  - Equal split scenarios.
  - Manual split (absolute and percentage) scenarios.
  - Handling of invalid inputs (e.g., non-numeric values, over-allocation).
  - Cancellation of the split operation.
- These tests ensure the reliability and robustness of the split feature.

### Contributions to Documentation

- **Developer Guide:**  
  Wrote detailed sections documenting the `SplitCommand` class, including its design, key methods, and integration with other modules.
- **User Guide:**  
  Contributed clear usage examples and instructions for the split feature, helping users understand how to interact with this functionality.

### Contributions to Team-Based Tasks

- **Code Review & Mentorship:**  
  Provided critical feedback on pull requests related to the expense splitting functionality, ensuring adherence to design standards and robust error handling.
- **Collaboration:**  
  Participated in design discussions and helped resolve issues related to input validation and file handling for the split feature.

---

**Relevant Pull Request:** 
[PR #65](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/65)
[PR #34](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/34)

---

### Contributions to Team-Based Tasks
- **Code Review**
  - Sequence Diagram for Split Class
  - Code reviews for teammates' PR

---