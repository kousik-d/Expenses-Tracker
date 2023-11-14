package com.example.exptracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: ExpenseRepository): ViewModel() {
    var allExpenses = repository.allExpenses.asLiveData()

    fun insertExpense(expense: Expense) = viewModelScope.launch(IO) {
        repository.insertExpense(expense)
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch(IO) {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch(IO) {
        repository.deleteExpense(expense)
    }

    fun deleteAllExpense() = viewModelScope.launch(IO) {
        repository.deleteAllExpense()
    }

}