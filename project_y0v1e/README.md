# My Personal Project

## An expense tracker

### Brief description

- The application allows users to track their expenses by marking down their expenses. 
- Users can set a **budget** for each month. 
- Users can put down the **expense**, the **category** of the expense, the **payment method**, and the **date of transaction**. 
- Users will be **notified** when the total expense <= budget - 10. Notification will be displayed in the application when the user enters an expense that makes the total expenses almost goes beyond the budget (10 bucks away). 
- (Visualization) A **pie chart** that shows the expenses and the category of the expenses in a month will be displayed. 
- Target users are people who want to keep track of their expenses. 
- I am interested in building an expense tracker because I often find it hard to keep track of my expenses. I unconsciously spend a lot more than expected. Most of the trackers only keep track of the expenses, but not reminding the users that they spend too much. Hence, I would like to design a tracker that helps users on financial planning. 

### User Story
- As a user, I want to be able to add expense to the tracker. 
- As a user, I want to be able to categorize my expenses. 
- As a user, I want to be able to note down the payment method. 
- As a user, I want to be able to note down the date of transaction. 
- As a user, I want to be able to set my budget for the month. 
- As a user, I want to be able to see statistics about my expenses/financial situation. 
- As a user, I want to be notified that I almost go beyond the budget. 
- As a user, I want to be able to remove an expense from the tracker.
- As a user, I want to save the budget and all the expenses to file. 
- As a user, I want to be able to load my budget and previous expenses from file. 

# Instructions for Grader

- You can generate the first required event related to adding an expense to an account by pressing the button 'Add Expense', or by pressing the menu tab 'Expense', then 'Add Expense'.
- You can generate the second required event related to adding the budget to an account by pressing the button 'Set Budget'.
- You can generate another event related to removing an expense from the account by pressing the button 'Remove Expense', or by pressing the menu tab 'Expense', then 'Remove Expense'.
- You can locate my visual component in the background of the frame.
- You can save the state of my application by pressing the button 'Save', or by pressing the menu tab 'File' then 'Save'.
- You can reload the state of my application by pressing the button 'Load', or by pressing the menu tab 'File' then 'Load'.

### Phase 4: Task 2

Wed Nov 30 17:37:04 PST 2022
Created a new account with budget 0.0

Wed Nov 30 17:37:10 PST 2022
Added budget by 2500.0

Wed Nov 30 17:37:24 PST 2022
Recorded an expense of 1500.0 which is categorized as rent paid by cash on 113022

Wed Nov 30 17:37:35 PST 2022
Recorded an expense of 25.0 which is categorized as food paid by credit on 301122

Wed Nov 30 17:37:50 PST 2022
Removed an expense of 25.0 which is categorized as food paid by credit on 301122

### Phase 3: Task 3

If I had time to do refactoring, I will create an abstract class that extracts the behaviour of printLog. 
It is because the method to print all Xs in Y and the printLog method have similar behaviours. 
Having an abstract class can reduce code redundancy. 
I will also write more helpers and use the helper in the method body to reduce redundant codes. Possible ones include:
- checking whether an entry is valid
- generating message displayed in the dialogue 
With more helpers, the XAction classes can be more neat and tidy. 
Also, it is easier to change the code by simply changing the helpers rather than making change to every class. 
