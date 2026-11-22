package com.example.myapplicationminishopmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "minishop.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE NOT NULL, " +
                    "password TEXT NOT NULL)"
        )
        db.execSQL("INSERT INTO users (username, password) VALUES ('admin', '1234')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun register(username: String, password: String): Boolean {
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return writableDatabase.insert("users", null, values) != -1L
    }

    fun login(username: String, password: String): Boolean {
        val c = readableDatabase.rawQuery(
            "SELECT id FROM users WHERE username = ? AND password = ?",
            arrayOf(username, password)
        )
        val ok = c.count > 0
        c.close()
        return ok
    }
}