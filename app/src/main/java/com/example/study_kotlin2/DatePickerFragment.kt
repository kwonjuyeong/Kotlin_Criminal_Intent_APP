package com.example.study_kotlin2

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.telecom.Call
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*

//datePicker를 불러오는 Fragment 생성
private const val ARG_DATE = "date"
class DatePickerFragment : DialogFragment() {

    interface Callbacks{
        fun onDateSelected(date: Date)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dateListener = DatePickerDialog.OnDateSetListener{
                _: DatePicker, year : Int, month : Int, day : Int ->

            val resultDate : Date = GregorianCalendar(year, month, day).time

            targetFragment?.let {
                (it as Callbacks).onDateSelected(resultDate)
            }
        }

        //DatePickerDialog를 초기화하려면 월, 일, 년의 정수 값들이 필요하다. 그러나 Date객체는 타임 스탬프 형태이다.
        //따라서 Date 객체를 사용해서 Calendar 객체를 생성해야한다.
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        dateListener
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDate = calendar.get(Calendar.DATE)

        return DatePickerDialog(
            requireContext(), null, initialYear, initialMonth, initialDate
        )
    }

    //DateFragment에 현재 범죄 날짜를 받아오기 위해 newInstace 인자를 만들어준다.
    companion object{
        fun newInstance(date: Date) : DatePickerFragment{
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }

    }

}