package com.maxtell.todolist

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxtell.todolist.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var recyclerAdapter: TodoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor){
            while (moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                val isUrgent = if (itemUrgency == 0)false else true

                val newTodoItems = TodoItem(itemName,isUrgent)
                newTodoItems.dateString = itemDate

                todoItemsList.add(newTodoItems)


                if(itemDate==getDateAsString()){
                    todaysItemsList.add(newTodoItems)
                }else{
                    pastItemsList.add(newTodoItems)
                }

            }
        }

//        todoItemsList.add(TodoItem("buy groceries",true))
//        todoItemsList.add(TodoItem("Do laundry",true))
//        todoItemsList.add(TodoItem("Play guitar",false))

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

    public fun displayTodayItems(view : View){
        recyclerAdapter = TodoItemsAdapter(todaysItemsList, this)

        binding.todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        binding.todaysItemsButton.background = getDrawable(R.color.teal_700)
    }
    public fun displayPastItems(view : View){
        recyclerAdapter = TodoItemsAdapter(pastItemsList, this)

        binding.todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        binding.pastItemsButton.background = getDrawable(R.color.teal_200)
    }

    fun getDateAsString():String{
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$year/$month/$day"
    }


}