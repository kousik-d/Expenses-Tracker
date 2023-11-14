package com.example.exptracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Expenses")
data class Expense (
    @PrimaryKey(autoGenerate = true)
    var ExpenseId:Int=0,
    var ExpenseName:String,
    var ExpenseLocation: String,
    var ExpenseDateTime:String,
    var ExpenseMoney: String
        )