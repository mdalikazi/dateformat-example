package com.alikazi.dateformatexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        const val TIME_ZONE_GMT_0 = "GMT+00:00"
    }

    private var mDateCalendar: Calendar = Calendar.getInstance()
    private var mSelectedLocale: Locale = Locale.getDefault()
    private var mSortedLocaleList: List<Locale> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent != null) {
            mDateCalendar.timeZone = TimeZone.getTimeZone(TIME_ZONE_GMT_0)
            mDateCalendar.timeInMillis = intent.getLongExtra("INTENT_EXTRA_DATE_LONG", mDateCalendar.timeInMillis)
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = getSpinnerAdapter()
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("Main", "position: $position")
                mSelectedLocale = mSortedLocaleList[position]
                Log.d("Main", "selected locale name: " + mSelectedLocale.displayName)
            }
        }

        val refreshButton: Button = findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener({ refreshDatesAndTimes() })

        refreshDatesAndTimes()
    }

    private fun refreshDatesAndTimes() {

        var date = getDateFormat(DateFormat.DATE_FIELD)
        var month = getDateFormat(DateFormat.MONTH_FIELD)
        var year = getDateFormat(DateFormat.YEAR_FIELD)

        var fullDate = getDateFormat(DateFormat.FULL)
        var longDate = getDateFormat(DateFormat.LONG)
        var mediumDate = getDateFormat(DateFormat.MEDIUM)
        var shortDate = getDateFormat(DateFormat.SHORT)
        var defaultDate = getDateFormat(DateFormat.DEFAULT)

        var fullTime = getTimeFormat(DateFormat.FULL)
        var longTime = getTimeFormat(DateFormat.LONG)
        var mediumTime = getTimeFormat(DateFormat.MEDIUM)
        var shortTime = getTimeFormat(DateFormat.SHORT)
        var defaultTime = getTimeFormat(DateFormat.DEFAULT)

        var fullDateTime = getDateTimeFormat(DateFormat.FULL, DateFormat.SHORT)

//        var timezone = DateFormat.getTimeInstance(DateFormat.TIMEZONE_FIELD).format(mDateCalendar.time)
//        var dayOfWeek = DateFormat.getTimeInstance(DateFormat.DAY_OF_WEEK_FIELD).format(mDateCalendar.time)
//        var amPm = DateFormat.getTimeInstance(DateFormat.AM_PM_FIELD).format(mDateCalendar.time)
//        var hourOfDay0 = DateFormat.getTimeInstance(DateFormat.HOUR_OF_DAY0_FIELD).format(mDateCalendar.time)
//        var hourOfDay1 = DateFormat.getTimeInstance(DateFormat.HOUR_OF_DAY1_FIELD).format(mDateCalendar.time)
//        var minute = DateFormat.getTimeInstance(DateFormat.MINUTE_FIELD).format(mDateCalendar.time)
//        var second = DateFormat.getTimeInstance(DateFormat.SECOND_FIELD).format(mDateCalendar.time)
//        var millisecond = DateFormat.getTimeInstance(DateFormat.MILLISECOND_FIELD).format(mDateCalendar.time)

        val textView: TextView = findViewById(R.id.dateformat_test_text_view)
        textView.text =
                "date: " + date + "\n" + "\n" +
                "month: " + month + "\n" + "\n" +
                "year: " + year + "\n" + "\n" +
                "fullDate: " + fullDate + "\n" + "\n" +
                "longDate: " + longDate + "\n" + "\n" +
                "mediumDate: " + mediumDate + "\n" + "\n" +
                "shortDate: " + shortDate + "\n" + "\n" +
                "defaultDate: " + defaultDate + "\n" + "\n" + "\n" +

                "fullTime: " + fullTime + "\n" + "\n" +
                "longTime: " + longTime + "\n" + "\n" +
                "mediumTime: " + mediumTime + "\n" + "\n" +
                "shortTime: " + shortTime + "\n" + "\n" +
                "defaultTime: " + defaultTime + "\n" + "\n" +

                "fullDateTime: " + fullDateTime + "\n"
//                "dayOfWeek: " + dayOfWeek + "\n" +
//                "amPm: " + amPm + "\n" +
//                "hourOfDay0: " + hourOfDay0 + "\n" +
//                "hourOfDay1: " + hourOfDay1 + "\n" +
//                "minute: " + minute + "\n" +
//                "second: " + second + "\n" +
//                "millisecond: " + millisecond
    }

    private fun getDateFormat(dateStyle: Int) : String {
        var s = ""
        try {
            s = DateFormat.getDateInstance(dateStyle, mSelectedLocale).format(mDateCalendar.time)
        } catch (e: Exception) {
            Log.d("DateFormat", "Exception with date format: " + e.message)
        }
        return s
    }

    private fun getTimeFormat(timeStyle: Int) : String {
        var s = ""
        try {
            s = DateFormat.getTimeInstance(timeStyle, mSelectedLocale).format(mDateCalendar.time)
        } catch (e: Exception) {
            Log.d("DateFormat", "Exception with date format: " + e.message)
        }
        return s
    }

    private fun getDateTimeFormat(dateStyle: Int, timeStyle: Int) : String {
        var s = ""
        try {
            s = DateFormat.getDateTimeInstance(dateStyle, timeStyle, mSelectedLocale).format(mDateCalendar.time)
        } catch (e: Exception) {
            Log.d("DateFormat", "Exception with date format: " + e.message)
        }
        return s
    }

    private fun getSpinnerAdapter() : ArrayAdapter<String> {
        mSortedLocaleList = DateFormat.getAvailableLocales().asList().sortedWith(compareBy { it.displayName })
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.array_adapter_text_item)
        for(locale: Locale in mSortedLocaleList) {
            arrayAdapter.add(locale.displayName)
        }

        return arrayAdapter
    }
}
