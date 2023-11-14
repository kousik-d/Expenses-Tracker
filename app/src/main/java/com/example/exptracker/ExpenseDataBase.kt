package com.example.exptracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 1)
abstract class ExpenseDataBase:RoomDatabase() {

    abstract fun getExpenseDao() : ExpenseDAO
    companion object{
        @Volatile
        private var INSTANCE:ExpenseDataBase? = null
        private var LOCK=Any()

        operator fun invoke(context: Context): ExpenseDataBase = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: createDataBase(context).also {
                INSTANCE = it
            }
        }

        fun createDataBase(context: Context):ExpenseDataBase = Room.databaseBuilder(
            context,
            ExpenseDataBase::class.java,
            "ExpenseDB"
        ).build()
    }
}