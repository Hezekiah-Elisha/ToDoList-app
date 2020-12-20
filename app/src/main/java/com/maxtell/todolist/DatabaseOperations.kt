package com.maxtell.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseOperations(context: Context) : SQLiteOpenHelper(
        context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "TodoItems.db"
        const val  DATABASE_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase) {

        p0.execSQL(DatabaseInfo.SQL_CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

        p0.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
        onCreate(p0)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }


    //add items to the db
    fun addItem(dbo : DatabaseOperations, todoItem: TodoItem) {
        val db = dbo.writableDatabase
        val itemName = todoItem.name
        val isItemUrgent = todoItem.isUrgent
        val itemUrgency = if(isItemUrgent)1 else 0
        val itemDate = todoItem.getDateAsString()


        val contentValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY, itemUrgency)
            put(DatabaseInfo.TableInfo.COLUMN_DATE, itemDate)
        }
        val rowID = db.insert(DatabaseInfo.TableInfo.TABLE_NAME,null, contentValues)
    }

    //to fetch an item in the db
    fun getAllItems(dbo: DatabaseOperations): Cursor{
        val db = dbo.readableDatabase
        val projection = arrayOf(
                BaseColumns._ID,
                DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
                DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY,
                DatabaseInfo.TableInfo.COLUMN_DATE
        )
        val selection = ""
        val selectionArgs = null
        val sortOrder = null

        val cursor = db.query(
            DatabaseInfo.TableInfo.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        return cursor
    }

    //modify item
    fun updateItem(dbo : DatabaseOperations, oldItem: TodoItem,newItem:TodoItem){
        val db = dbo.writableDatabase
        val itemName = newItem.name
        val isItemUrgent = newItem.isUrgent
        val itemUrgency = if(isItemUrgent)1 else 0
        val itemDate = newItem.getDateAsString()

        val contentValues = ContentValues().apply{
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME, itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY, itemUrgency)
            put(DatabaseInfo.TableInfo.COLUMN_DATE, itemDate)
        }

        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(oldItem.name)
        db.update(DatabaseInfo.TableInfo.TABLE_NAME,contentValues,selection,selectionArgs)
    }

    //delet item
    fun deleteItem(dbo : DatabaseOperations, todoItem: TodoItem){
        val db = dbo.writableDatabase
        val selection = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val selectionArgs = arrayOf(todoItem.name)

        db.delete(DatabaseInfo.TableInfo.TABLE_NAME, selection, selectionArgs)
    }
}