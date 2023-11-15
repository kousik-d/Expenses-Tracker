package com.example.exptracker

import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDataBase: ExpenseDataBase) {

    var allExpenses: Flow<List<Expense>> = expenseDataBase.getExpenseDao().getExpenses()

    suspend fun insertExpense(expense: Expense) = expenseDataBase.getExpenseDao().insert(expense)

    suspend fun updateExpense(expense: Expense) = expenseDataBase.getExpenseDao().update(expense)

    suspend fun deleteExpense(expense: Expense) = expenseDataBase.getExpenseDao().delete(expense)

    suspend fun deleteAllExpense() = expenseDataBase.getExpenseDao().deleteAllExpense()


}