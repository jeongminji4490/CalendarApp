package com.example.kt_calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import com.example.kt_calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //val : 불변, var : 가변, 타입은 변경 불가
    //lateinit : 변수 생성은 미리 하고 초기화는 나중에, 초기화 전까지 변수 사용 금지
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val toolbar=findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar!!
        actionbar.setDisplayShowTitleEnabled(false)

        binding.btn1.setOnClickListener{
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            //setCalendarFragment()
        }
        binding.btn2.setOnClickListener {
            setEventFragment()
        }
    }

    //fun : 함수임을 나타내는 키워드
    //매개변수는 변수명:타입
    private fun setCalendarFragment(){
        val transaction=supportFragmentManager.beginTransaction().add(R.id.frameLayout1,CalendarFragment())
        transaction.commit()
    }

    private fun setEventFragment() {
        val transaction=supportFragmentManager.beginTransaction().add(R.id.frameLayout1,EventFragment())
        transaction.commit()
    }
}