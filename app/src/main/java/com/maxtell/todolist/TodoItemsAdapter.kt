package com.maxtell.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TodoItemsAdapter(val todoItemsList:ArrayList<TodoItem>):
    RecyclerView.Adapter<TodoItemsAdapter.viewHolder>() {
    class viewHolder(val constraintLayout:ConstraintLayout):RecyclerView.ViewHolder(constraintLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item_layout,parent,false) as ConstraintLayout

        return viewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val nameTextView = constraintLayout.getChildAt(0) as TextView
        val urgencyTextView = constraintLayout.getChildAt(1) as TextView
        nameTextView.text = todoItemsList[position].name
        urgencyTextView.text = if (todoItemsList[position].isUrgent) "!!" else ""
    }

    override fun getItemCount(): Int {
        return todoItemsList.size
    }
}