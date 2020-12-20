package com.maxtell.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.maxtell.todolist.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private var isNewItem = true
    private lateinit var oldItem:TodoItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_item)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_item)

        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemUrgency = intent.getBooleanExtra("ITEM_URGENCY",false)

        if(itemName != null){
            binding.editTextItem.setText(itemName)
            binding.tvTitle.setText(R.string.edit_item_message)

            oldItem = TodoItem(itemName)
            isNewItem = false
        }

        if(itemUrgency == true){
            binding.cbUrgent.isChecked = true
            oldItem.isUrgent = itemUrgency
        }
    }
    public fun saveItemAction(view:View){
        val itemName = binding.editTextItem.text.toString()
        val isItemUrgent = binding.cbUrgent.isChecked
        val newTodoItem = TodoItem(itemName,isItemUrgent)

        val dbo = DatabaseOperations(this)
        if(isNewItem){
            dbo.addItem(dbo,newTodoItem)
        }else{
            dbo.updateItem(dbo, this.oldItem, newTodoItem)
        }
    }
    public fun cancelAction(view:View){
        var cancelIntent: Intent = Intent(this, MainActivity::class.java)
        startActivity(cancelIntent)
    }
}