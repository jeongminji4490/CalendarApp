package com.example.kt_calendar

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kt_calendar.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.*

class MainActivity : AppCompatActivity() {

    //val : 불변, var : 가변, 타입은 변경 불가
    //lateinit : 변수 생성은 미리 하고 초기화는 나중에, 초기화 전까지 변수 사용 금지
    //커밋 푸시 재시도
    lateinit var binding:ActivityMainBinding
    lateinit var sMonth:String
    lateinit var sDate:String
    lateinit var sHour:String
    lateinit var sMinute:String
    lateinit var alarm:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val toolbar=findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar!!
        actionbar.setDisplayShowTitleEnabled(false)
        //val MaterialCalendarView=findViewById(R.id.calendarView) as MaterialCalendarView

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

        binding.btn1.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btn2.setOnClickListener {
            setEventFragment()
        }
    }

    //fun : 함수임을 나타내는 키워드
    //매개변수는 변수명:타입
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