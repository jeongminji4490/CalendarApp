package com.example.kt_calendar

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CalendarDBHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int, month: String, day: String) : SQLiteOpenHelper(context, name, factory, version) {

    val TITLE = "Title"
    val CONTENT = "Content"
    val ALARM = "Alarm"
    var month = month
    var day = day

    override fun onCreate(db: SQLiteDatabase?) {
        var tableName = month + day
        val schedule = "schedule"
        /*테이블 이름 설정 방법*/
        /*데이터 삽입*/
        val sql = "CREATE TABLE if not exists" + " " + "schedule" + tableName + "(" + TITLE + " TEXT," + CONTENT + " TEXT," + ALARM + " TEXT);"
        db!!.execSQL(sql) /*sql문 실행*/
    }

    fun insertDBcontent(db: SQLiteDatabase, title: String?, content: String?, alarm: String?) { //데이터 삽입
        val tableName = "schedule" + month + day
        val values = ContentValues()
        values.put("Title", title)
        values.put("Content", content)
        values.put("Alarm", alarm)
        db.insert(tableName, null, values)
        Log.w("TAG", "Table row insert execute")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}