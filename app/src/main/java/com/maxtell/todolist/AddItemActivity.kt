package com.maxtell.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.maxtell.todolist.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_item)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_item)
    }
    public fun saveItemAction(view:View){

    }
    public fun cancelAction(view:View){
        var cancelIntent: Intent = Intent(this, MainActivity::class.java)
        startActivity(cancelIntent)
    }
}