package com.rohit.myutilslibrary

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import clearPreferenceData
import com.rohit.android.permissions.PermissionHandler
import com.rohit.android.permissions.Permissions
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
import loadImage
import printHashKey
import printLogD
import printLogE
import printLogI
import printLogV
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
    lateinit var imgApp: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callLibraryFunctions()
    }

    private fun callLibraryFunctions() {
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


        /**Progress functionality**/
        progressBarFunctionality()

        /** Print Hash key **/
        printHashKey()

        /** Print Logs **/
        printLogD("mylog", "bro")
        printLogE("mylog", "bro")
        printLogV("mylog", "bro")
        printLogI("mylog", "bro")

        /** Load image in imageView **/
        imgApp = findViewById(R.id.imgApp)
        var imageUri =
            "data:image/jpeg;base64,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"
        imgApp.loadImage(imageUri)
        imgApp.loadImage(imageUri,R.drawable.ic_launcher_background)
        imgApp.loadImage(R.drawable.ic_launcher_background)


    }

    private fun progressBarFunctionality() {
        /** By default progress bar is circular round progress **/

        /** show progess and stop progresss **/
        showProgess()
        Handler().postDelayed({
            stopProgress()
            /** Permissions **/
            permissions()
        }, 5000)

        /** To change color of Progress Bar , call the below function and pass the hash code of color in first screen of your app **/
        setProgressBarColor("#000000")

        /** If you want to change a progress to a logo progress, call the below function and  pass your logo in below funtion in first screen of your app **/
        enableLogoProgress(true, R.mipmap.ic_launcher)
    }

    private fun permissions() {
        /** request Runtime permission **/

        lateinit var perms: Array<String>
        var alertType = "This app needs access to your camera and mic to make video calls"
        perms = arrayOf<String>(
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )

        val rationale = alertType
        val options: Permissions.Options = Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning")

        Permissions.check(
            this /*context*/,
            perms,
            rationale,
            options,
            object : PermissionHandler() {
                override fun onGranted() {
                    showToast("All permissions granted")
                    // permission grant work
                }

                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
                    // permission denied, block the feature.
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
        // clear all stored data when application kill
        clearPreferenceData()
    }


}