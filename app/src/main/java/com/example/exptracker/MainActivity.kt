package com.example.exptracker

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var expenseviewmodel : ExpenseViewModel
    private lateinit var ExpenseAdapter : ExpenseAdapter
    private lateinit var ExpenseRv : RecyclerView
    private lateinit var list: List<Expense>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list= mutableListOf()

        ExpenseRv = findViewById(R.id.ExpenseRv)
        ExpenseAdapter = ExpenseAdapter(list)
        ExpenseRv.adapter = ExpenseAdapter
        ExpenseRv.layoutManager = LinearLayoutManager(this)

        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> deleteExpense(viewHolder.absoluteAdapterPosition)
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(ExpenseRv)

        val expenseRepository = ExpenseRepository(ExpenseDataBase.invoke(this))
        val factory = ExpenseViewModelFactory(expenseRepository)

        expenseviewmodel = ViewModelProvider(this,factory).get(ExpenseViewModel::class.java)

        expenseviewmodel.allExpenses.observe(this, Observer {
            ExpenseAdapter.list = it
            ExpenseAdapter.notifyDataSetChanged()
        })

    }




    fun CreateDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_expense_dialog)

        val expensename = dialog.findViewById<EditText>(R.id.ExpenseNameId)
        val expenseLocation = dialog.findViewById<EditText>(R.id.MoneyLocationid)
        val expenseMoney = dialog.findViewById<EditText>(R.id.Moneyid)

        val addbtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val cancelbtn = dialog.findViewById<Button>(R.id.idBtnCancel)



        cancelbtn.setOnClickListener {dialog.dismiss()}

        addbtn.setOnClickListener {
            if(expensename.text.isNotEmpty() && expenseLocation.text.isNotEmpty() && expenseMoney.text.isNotEmpty()){

                val money = expenseMoney.text.toString()
                val expense = Expense(0,expensename.text.toString(),expenseLocation.text.toString(),"",money)
                Toast.makeText(this, "Item Inserted $expense", Toast.LENGTH_SHORT).show()
                expenseviewmodel.insertExpense(expense)
                ExpenseAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun deleteExpense(index:Int){
        expenseviewmodel.deleteExpense(ExpenseAdapter.list[index])
        ExpenseAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tools_bar,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Add_to_list -> CreateDialog()
            R.id.deleteAll -> DeleteAllExpense()
            R.id.sortByDate -> Toast.makeText(this,"SortBy date",Toast.LENGTH_SHORT).show()
            R.id.sortByMoney -> SortByMoney()
        }
        return true
    }

    //Not Working Effeciently
    fun SortByMoney(){
        val newList = ExpenseAdapter.list
        newList.sortedWith(compareBy({it.ExpenseMoney}))
        Toast.makeText(this,"${newList}",Toast.LENGTH_SHORT).show()
        Log.i("SORTED","${newList}")
        ExpenseAdapter.notifyDataSetChanged()
    }



    private fun DeleteAllExpense() {
        expenseviewmodel.deleteAllExpense()
        Toast.makeText(this,"Deleted All Items",Toast.LENGTH_SHORT).show()
        ExpenseAdapter.notifyDataSetChanged()
    }
}