package com.maxtell.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxtell.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var recyclerAdapter: TodoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    var todoItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        todoItemsList.add(TodoItem("buy groceries",true))
        todoItemsList.add(TodoItem("Do laundry",true))
        todoItemsList.add(TodoItem("Play guitar",false))

        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = TodoItemsAdapter(todoItemsList, this)

        binding.todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }

    }
    public fun navToAddItemAction(view: View){
        var intent:Intent = Intent(this,AddItemActivity::class.java)
        startActivity(intent)

    }

}