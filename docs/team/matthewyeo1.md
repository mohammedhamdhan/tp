# Matthew Yeo - Project Portfolio Page

## Overview

---

O\$P\$ is an expense-tracker application that allows the user to keep track of their expenditure with ease! Users can also manage expenses in a group setting with simple 
splitting functions, as well as view analytics of their spending across multiple categories.

## Summary of Contributions

---

### Code Contributed

Code contribution to the project: [Reposense Page](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=matthewyeo1&tabRepo=AY2425S2-CS2113-F11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Features Implemented
Here is the list of features implemented:

#### 1. Data Storage Feature

- **Function**: User data entered during each session is automatically saved in .txt files upon exiting the application and loaded each time the application starts.
- **Justification**: Users benefit from having their data stored automatically, avoiding the need to re-enter information each session.

#### 2. Comprehensive UI/UX

- **Function**: The application contains a list of commands that the user can input, dividers to separate each user command and system output messages for each action.
- **Justification**: Users can easily navigate through the application using short and concise commands for each feature and understand what is happening for each action.

#### 3. Expense Classification Algorithm

- **Function**: An auxiliary function that calculates the relative percentages of each category according to the title and description of the user's expenses. 
- **Justification**: Users are able to view their expense distribution in number and percentages with visuals and can hence make more informed decisions for future expenditure.

---

### Enhancements Implemented

#### Unit Tests Across Storage and Expense-Related Classes
- **Input Validation**: Test cases validate inputs and ensure that error messages are returned for invalid entries.
- **Boundary and Edge Case Tests**: Test cases check for negative values, null values, and other edge cases to ensure proper handling.
- **Error Handling**: Test cases confirm that appropriate error messages are displayed when specific errors occur.
- **Data Storage and Loading**: Test cases verify that the save file is created correctly and that data is properly saved and loaded.

### Contributions to User Guide

- **Documented Sections:**
    - `Basic Interface of O$P$ Application`
    - `Admin Commands: 'help', 'categories' and 'exit'`
    - `Manage Expenses: 'add', 'edit' and 'delete`
    - `Smart Categories Classifier`

**Relevant Pull Requests:** 
    - [PR #1](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/1/files)
    - [PR #4](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/4/files)
    - [PR #57](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/57/files)

---

### Contributions to Developer Guide

#### Documented Sections
- **Documentation in Section 3**:
    - Section 3.1 `UI Class`
    - Section 3.2 `DataStorage Class`
    - Section 3.4 `Commands Class`
    - Section 3.8 `BudgetManager Class`
    - Section 4.4 `Messages Class`

- **Documentation in Section 4**:
    - Section 4.1.5 `Display Monthly Expenses`
    - Section 4.1.6 `Display Expenses for the Month with Categories`

- **Documentation in Section 5**:
  - Section 5 `Appendix`
  - Section 5.1 `Product Scope`
  - Section 5.1.1 `Target User Profile`
  - Section 5.1.2 `Value Proposition`
  - Section 5.2 `User Stories`
  - Section 5.3 `Non-Functional Requirements`
  - Section 5.4 `Glossary`
  - Section 5.5 `Test Cases`

#### Class & UML Sequence Diagrams
- Section 4.1: `Flow Diagram of Overall Program`
- Section 4.2: `Expense CRUD Feature`

**Relevant Pull Requests:**
    - [PR #82](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/82/files)
    - [PR #72](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/72/files)
    - [PR #86](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/86/files)

---

### Review/Mentoring Contributions:
**Bug Finding and Suggestions:** Daily thorough analysis of PR code for reviewing, critiquing and encouragement.

**Relevant Pull Requests:**
    - [PR #73](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/73/files)
    - [PR #67](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/67/files)
    - [PR #34](https://github.com/AY2425S2-CS2113-F11-2/tp/pull/34/files)

---

### Contributions to Team-Based Tasks
- **Release Management:** 
    - Managed the `v1.0` release.
- **UG and DG Updates:**
    - Added Table of Contents to both the User Guide and Developer Guide.
    - Reformatted the User Guide and Developer Guide with more specific subsections.
- **Progress Monitoring:** 
    - Regularly created GitHub Issues to assign weekly sprints to teammates.
    - Frequently reviewed and provided comments to teammates' PRs.
- **Codebase Maintenance:**
    - Proactive resolution of Java CI/CD pipeline errors and merging of code.
