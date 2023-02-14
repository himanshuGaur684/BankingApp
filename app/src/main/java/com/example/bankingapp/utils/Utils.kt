package com.example.bankingapp.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun generateRandomNumber(count:Int):String{
    var randomNumber = ""
    repeat(count){
        randomNumber+= kotlin.random.Random.nextInt(0,9).toString()
    }
    return randomNumber
}

fun String?.parseDate(format: String): Date? {
    var parseDate: Date? = null
    if (TextUtils.isEmpty(this)) {
        return parseDate
    }
    val formatter = SimpleDateFormat(format)
    try {
        parseDate = formatter.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return parseDate
}
fun getCurrentDate(currentFormat: String?): String {
    val formatter = SimpleDateFormat(currentFormat)
    return formatter.format(Date())
}
@SuppressLint("SimpleDateFormat")
fun addNoOfDaysInCurrentDate(currentDate: String, noOfDays: Int): String {
    val presentDate = currentDate.parseDate(Constant.DATE_FORMAT_SERVER)?.time
    presentDate?.let {
        val millis = noOfDays * 86400000L
        val simpleDateFormat = SimpleDateFormat(Constant.DATE_FORMAT_SERVER);
        val sum = it + millis
        return simpleDateFormat.format(sum)
    }
    return ""
}