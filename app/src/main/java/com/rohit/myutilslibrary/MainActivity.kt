package com.rohit.myutilslibrary

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import clearPreferenceData
import com.rohit.myutils.Data
import enableLogoProgress
import getBoolean
import getFloat
import getInt
import getLong
import getModel
import getString
import isNetworkConnected
import isNotFastClicks
import saveBoolean
import saveFloat
import saveInt
import saveLong
import saveModel
import saveString
import setProgressBarColor
import showProgess
import showToast
import stopProgress

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callLibraryFunctions()
    }

    private fun callLibraryFunctions() {

        setProgressBarColor("#000000")
        enableLogoProgress(true,R.mipmap.ic_launcher)

        /** to show toast **/
        showToast("hello everyone")

        /** for checking fast click **/
        if (isNotFastClicks()) {
            // Work here if not fast clicks
        } else {
            showToast("Fast Clicking")
        }


        /** check network connected or not **/
        if (isNetworkConnected()) {
            // work here if internet connected
        } else {
            showToast("Please check your internet connection")
        }


        /** show progess and stop progresss **/
        showProgess()
        Handler().postDelayed({
            stopProgress()
        }, 10000)


        /** Save Values in local storage **/
        saveString("myString", "rohit")
        saveInt("myInt", 1)
        saveFloat("myFloat", 1.0f)
        saveLong("myLong", 1)
        saveBoolean("myBoolean", true)
        saveModel("myModel", Data("rohit", 1))

        /** get Values from local storage **/
        var saveStringValue = getString("myString")
        var saveIntValue = getInt("myInt")
        var saveBooleanValue = getBoolean("myBoolean")
        var saveModelValue = getModel("myModel", Data::class.java)
        var saveLongValue = getLong("myLong")
        var saveFloatValue = getFloat("myFloat")

    }


    override fun onDestroy() {
        super.onDestroy()
        // clear all stored data when application kill
        clearPreferenceData()
    }
}