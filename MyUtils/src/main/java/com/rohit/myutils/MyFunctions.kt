import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.developers.imagezipper.ImageZipper
import com.google.gson.Gson
import com.rohit.myutils.MyConstants.Companion.LOGO_PROGRESS_ENABLE
import com.rohit.myutils.MyConstants.Companion.LOGS_TAG
import com.rohit.myutils.MyConstants.Companion.MY_PREF_NAME
import com.rohit.myutils.MyConstants.Companion.PROGRESS_COLOR
import com.rohit.myutils.MyConstants.Companion.PROGRESS_LOGO
import com.rohit.myutils.R
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private var ON_CLICK_DELAY: Long = 700
private var lastTimeClicked = 0L
private var dialog: Dialog? = null
private var sharedPref: SharedPreferences? = null

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    capabilities.also {
        if (it != null) {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true
            else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            }
        }
    }
    return false
}


fun Context.showProgess() {
    if (dialog == null) {
        dialog = Dialog(this)
        dialog!!.setContentView(R.layout.dialog_progress)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        var progress = dialog!!.findViewById<ProgressBar>(R.id.myProgressBar)
        var imgLogo = dialog!!.findViewById<ImageView>(R.id.imgLogoProgress)
        var txtPleaseWait = dialog!!.findViewById<TextView>(R.id.txtPleaseWait)
        if (getBoolean(LOGO_PROGRESS_ENABLE)) {
            imgLogo.visibility = View.VISIBLE
            progress.visibility = View.INVISIBLE
            imgLogo.setBackgroundResource(getInt(PROGRESS_LOGO))
            var animFlip = AnimationUtils.loadAnimation(
                this,
                R.anim.anim_flip
            );
            imgLogo.startAnimation(animFlip)
        } else {
            imgLogo.visibility = View.INVISIBLE
            progress.visibility = View.VISIBLE
            txtPleaseWait.visibility = View.INVISIBLE
            if (!getString(PROGRESS_COLOR).equals("")) {
                progress.indeterminateTintList =
                    (ColorStateList.valueOf(Color.parseColor(getString(PROGRESS_COLOR))));
            }
        }
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()
    } else {
        if (!dialog!!.isShowing) {
            dialog!!.show()
        }
    }
}

fun stopProgress() {
    if (dialog != null) {
        dialog!!.cancel()
        dialog = null
    }
}


fun isNotFastClicks(): Boolean {
    if (System.currentTimeMillis() - lastTimeClicked > ON_CLICK_DELAY) {
        lastTimeClicked = System.currentTimeMillis()
        return true
    } else {
        return false
    }
}

fun Context.saveString(key: String, value: String) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putString(key, value)
    prefsEditor.apply()
}

fun Context.saveInt(key: String, value: Int) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putInt(key, value)
    prefsEditor.apply()
}


fun Context.saveLong(key: String, value: Long) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putLong(key, value)
    prefsEditor.apply()
}

fun Context.saveFloat(key: String, value: Float) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putFloat(key, value)
    prefsEditor.apply()
}


fun Context.saveBoolean(key: String, value: Boolean) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putBoolean(key, value)
    prefsEditor.apply()
}

fun Context.getInt(key: String): Int {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    return sharedPref!!.getInt(key, 0)
}

fun Context.getFloat(key: String): Float {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    return sharedPref!!.getFloat(key, 0.0f)
}

fun Context.getLong(key: String): Long {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    return sharedPref!!.getLong(key, 0)
}


fun Context.getString(key: String): String {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    return sharedPref!!.getString(key, "")!!
}

fun Context.getBoolean(key: String): Boolean {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    return sharedPref!!.getBoolean(key, false)!!
}

fun Context.clearPreferenceData() {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.clear()
    prefsEditor.apply()
}


fun <T> Context.getModel(key: String?, type: Class<T>?): T {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val gson = Gson()
    return gson.fromJson(sharedPref!!.getString(key, ""), type)
}

fun Context.saveModel(key: String?, modelClass: Any?) {
    sharedPref = getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    val gson = Gson()
    prefsEditor.putString(key, gson.toJson(modelClass))
    prefsEditor.apply()
}


fun Context.compressFile(file: File): File {
    val imageCompress = ImageZipper(this)
        .setQuality(80)
        .setMaxWidth(200)
        .setMaxHeight(200)
        .compressToFile(file)
    return imageCompress
}


fun ImageView.loadImage(path: Any, placeholders: Int = 0) {
    var placeholder=placeholders
    if(placeholder==0) {
        placeholder = R.color.gray
    }
    Glide.with(this).load(path).placeholder(placeholder).into(this)
}


fun Context.setProgressBarColor(colorHashCode: String) {
    saveString(PROGRESS_COLOR, colorHashCode)
}

fun Context.enableLogoProgress(isEnable: Boolean, logo: Int) {
    saveBoolean(LOGO_PROGRESS_ENABLE, isEnable)
    saveInt(PROGRESS_LOGO, logo)
}

fun Context.printHashKey() {
    try {
        val info: PackageInfo = packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val hashKey: String = String(Base64.encode(md.digest(), 0))
            Log.i("LogsRRUtils", "Hash Key: $hashKey")
        }
    } catch (e: NoSuchAlgorithmException) {
        Log.e("$LOGS_TAG", "Hash Key:", e)
    } catch (e: Exception) {
        Log.e("$LOGS_TAG", "Hash Key:", e)
    }
}


fun printLogE(tag: String, message: String) {
    Log.e("$LOGS_TAG $tag", message)
}

fun printLogD(tag: String, message: String) {
    Log.d("$LOGS_TAG $tag", message)
}

fun printLogV(tag: String, message: String) {
    Log.v("$LOGS_TAG $tag", message)
}

fun printLogI(tag: String, message: String) {
    Log.i("$LOGS_TAG $tag", message)
}





