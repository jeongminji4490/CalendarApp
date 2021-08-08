package com.example.kt_calendar

import android.app.TimePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kt_calendar.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var sMonth:String
    lateinit var sDate:String
    lateinit var sHour:String
    lateinit var sMinute:String
    lateinit var alarm:String
    val dbName = "calendar3.db" /*db이름*/
    val dbVersion = 1 /*db 버전*/
    lateinit var db:SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val toolbar=findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar!!
        actionbar.setDisplayShowTitleEnabled(false)
        val materialCalendarView=findViewById(R.id.calendarView) as MaterialCalendarView

        //오늘 기준//
        var year = CalendarDay.today().year //년도, int
        var month = CalendarDay.today().month //월, int
        var date = CalendarDay.today().day //날짜, int
        sMonth = month.toString() //월 string 변환
        sDate = date.toString() //날짜 string 변환

        binding.monthText.text = setMonth(month) //오늘 기준 월 출력
        binding.dayText.text = sDate //오늘 기준 날짜 출력

        binding.calendarView.setSelectedDate(CalendarDay.today())

        binding.calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val month = date.month
            val day = date.day
            //datee = date
            sMonth = Integer.toString(month)
            sDate = Integer.toString(day)
            binding.monthText.text = setMonth(month)
            binding.dayText.text = sDate
        })

        //click alarm button -> timePickerDialog
        binding.alarmBtn.setOnClickListener(View.OnClickListener {
            val c = Calendar.getInstance()
            val mhour = c[Calendar.HOUR]
            val mMinute = c[Calendar.MINUTE]
            val timePickerDialog =
                    TimePickerDialog(
                            this@MainActivity,
                            { view, hourOfDay, minute ->
                                sHour = Integer.toString(hourOfDay)
                                sMinute = Integer.toString(minute)
                                binding.alarmText1.setText("$hourOfDay:$minute")
                                alarm = sHour + sMinute
                            }, mhour, mMinute, false
                    )
            timePickerDialog.show()
        })

        //click saveBtn -> datas are stored in sqlite db
        //조건문!!!
        binding.saveBtn.setOnClickListener {
            var dbHelper1=CalendarDBHelper(this, dbName, null, dbVersion, sMonth, sDate)
            db=dbHelper1.writableDatabase
            dbHelper1.onCreate(db)
            dbHelper1.insertDBcontent(db, binding.titleEdit.text.toString(), binding.contentEdit.text.toString(), binding.alarmText1.text.toString())
            Toast.makeText(this,"save schedule",Toast.LENGTH_SHORT).show()
        }

        binding.btn1.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btn2.setOnClickListener {
            setEventFragment()
        }
    }

    private fun setCalendarFragment(){
        val transaction=supportFragmentManager.beginTransaction().add(
                R.id.frameLayout1,
                CalendarFragment()
        )
        transaction.commit()
    }

    private fun setEventFragment() {
        val transaction=supportFragmentManager.beginTransaction().add(
                R.id.frameLayout1,
                EventFragment()
        )
        transaction.commit()
    }

    private fun setMonth(month: Int): String {
        return when(month){
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "?"
        }
    }
}