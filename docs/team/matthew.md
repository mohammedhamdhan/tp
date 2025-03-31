# Matthew Yeo - Project Portfolio Page

## Overview

---

O\$P\$ is an expense-tracker application that allows the user to keep track of their expenditure with ease! Users can also manage expenses in a group setting with simple 
splitting functions, as well as view analytics of their spending across multiple categories.

## Summary of Contributions

---

### Code Contributed

Code Contribution to the project: [Reposense Page](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=matthewyeo1&tabRepo=AY2425S2-CS2113-F11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Features Implemented
Here is the list of features implemented:

#### 1. Data Storage Feature

- **What It Does**: User data entered during each session is automatically saved in .txt files upon exiting the application and loaded each time the application starts.
- **Justification**: Users benefit from having their data stored automatically, avoiding the need to re-enter information each session.

#### 2. Comprehensive UI/UX

- **What It Does**: The application contains a list of commands that the user can input, dividers to separate each user command and system output messages for each action.
- **Justification**: Users can easily navigate through the application using short and concise commands for each feature and understand what is happening for each action.

---

### Enhancements Implemented

#### 1. Unit Tests Across Storage and Expense-Related Classes
- **Input Validation**: Test cases validate inputs and ensure that error messages are returned for invalid entries.
- **Boundary and Edge Case Tests**: Test cases check for negative values, null values, and other edge cases to ensure proper handling.
- **Error Handling**: Test cases confirm that appropriate error messages are displayed when specific errors occur.
- **Data Storage and Loading**: Test cases verify that the save file is created correctly and that data is properly saved and loaded.

#### 2. Improved Input Validation for Graph Commands
- **Details**: Ensures that different input errors in graph commands yield specific error messages relevant to each type of error.
- **Impact**: This improvement allows users to identify input mistakes immediately and correct them when re-entering commands.

---

### Contributions to User Guide

- **Documented Sections:**
    - `Basic Interface of O$P$ Application`
    - `Admin Commands: 'help', 'categories' and 'exit'`
    - `Smart Categories Classifier`

**Relevant Pull Requests:** 
    - [PR #1](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/1/files)
    - [PR #4](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/4/files)
    - [PR #57](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/57/files)

---

### Contributions to Developer Guide

#### Documented Sections
- **Documentation in Section 3**:
    - Section 3.1 `Architecture`
    - Section 3.2. `Parser Class`
    - Section 3.3. `UI Class`
    - Section 3.4 `Command Class`
    - Section 3.5 `Validator Classes`

- **Documentation in Section 4**:
    - Section 4.1.5 `Display Monthly Expenses`
    - Section 4.1.6 `Display Expenses for the Month with Categories`

- **Documentation in Section 5**:
    - Section 5.5.1.1 `Launch`
    - Section 5.5.2.7 `Display Monthly Expenses Chart`
    - Section 5.5.2.14 `Display Expenses for the Month with Categories`
    - Section 5.5.2.15 `Exit BudgetBuddy`

#### UML Sequence Diagrams
- Section 3.1: `Architecture`
- Section 3.5: `Validator Classes`
- Section 4.1.5: `Display Monthly Expenses`
- Section 4.1.6: `Display Expenses for the Month with Categories`

**Relevant Pull Request:** [PR #89](https://github.com/AY2425S1-CS2113-W10-1/tp/pull/89/files)

---

### Contributions to Team-Based Tasks
- **Release Management:** Managed the `v1.0` release.
- **UG and DG Updates:**
    - Added Table of Contents to both the User Guide and Developer Guide.
    - Added a FAQ section to the User Guide.
    - Reformatted the User Guide and Developer Guide with more specific subsections.
- **Useful Libraries:**
    - Incorporated XChart into Gradle dependencies, ensuring all dependencies are correctly installed.
    - Utilized JFrame to prevent closing XChart windows from ending the application.
    - Ensured XChart libraries are correctly built and included in the JAR file.

---

### Contributions Beyond the Project Team
- **Bug Finding and Suggestions:** [Bug Report](https://github.com/Alfred-Goh02/ped/issues)
- **PR Review for Other Teams:** [Pull Request](https://github.com/nus-cs2113-AY2425S1/tp/pull/2/files/b7aac5a22c63db81288d14b15b5d4cb9ecf65418)