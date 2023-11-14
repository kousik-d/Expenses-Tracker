package com.example.exptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(var list:List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>(){
    class ExpenseViewHolder(expenseView : View): RecyclerView.ViewHolder(expenseView) {
        val expensehead = expenseView.findViewById<TextView>(R.id.ExpenseHead)
        val expenseMoney = expenseView.findViewById<TextView>(R.id.ExpenseMoney)
        val expensedateLoc = expenseView.findViewById<TextView>(R.id.ExpenseDate_Location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.expense_item_layout,parent,false)
        return ExpenseViewHolder(itemview)
    }



    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.expensehead.text = list[position].ExpenseName
        holder.expenseMoney.text=list[position].ExpenseMoney.toString()
        holder.expensedateLoc.text = "${list[position].ExpenseDateTime} ${list[position].ExpenseLocation}"
    }
}