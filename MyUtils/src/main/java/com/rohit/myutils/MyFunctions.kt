import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.developers.imagezipper.ImageZipper
import com.google.gson.Gson
import com.rohit.myutils.R
import java.io.File

private var ON_CLICK_DELAY: Long = 700
private var lastTimeClicked = 0L
private var dialog: Dialog? = null
private var sharedPref: SharedPreferences? = null

fun Context.showToast(message:String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
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
        var progress=dialog!!.findViewById<ProgressBar>(R.id.myProgressBar)
        if(!getString("progressBarColorRR").equals("")){
            progress.indeterminateTintList=(ColorStateList.valueOf(Color.parseColor(getString("progressBarColorRR"))));
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

fun Context.stopProgress() {
    if (dialog != null) {
        dialog!!.cancel()
        dialog = null
    }
}


fun Context.isNotFastClicks(): Boolean {
    if (System.currentTimeMillis() - lastTimeClicked > ON_CLICK_DELAY) {
        lastTimeClicked = System.currentTimeMillis()
        return true
    } else {
        return false
    }
}

fun Context.saveString(key: String, value: String) {
    sharedPref =getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putString(key, value)
    prefsEditor.apply()
}

fun Context.saveInt( key: String, value: Int) {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putInt(key, value)
    prefsEditor.apply()
}


fun Context.saveLong( key: String, value: Long) {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putLong(key, value)
    prefsEditor.apply()
}

fun Context.saveFloat( key: String, value: Float) {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putFloat(key, value)
    prefsEditor.apply()
}


fun Context.saveBoolean( key: String, value: Boolean) {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.putBoolean(key, value)
    prefsEditor.apply()
}

fun Context.getInt(key: String): Int {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    return sharedPref!!.getInt(key, 0)
}
fun Context.getFloat(key: String): Float {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    return sharedPref!!.getFloat(key, 0.0f)
}
fun Context.getLong(key: String): Long {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    return sharedPref!!.getLong(key, 0)
}


fun Context.getString( key: String): String {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    return sharedPref!!.getString(key, "")!!
}
fun Context.getBoolean( key: String): Boolean {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    return sharedPref!!.getBoolean(key, false)!!
}

fun Context.clearPreferenceData() {
    sharedPref =getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    prefsEditor.clear()
    prefsEditor.apply()
}


fun <T> Context.getModel(key: String?, type: Class<T>?): T {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val gson = Gson()
    return gson.fromJson(sharedPref!!.getString(key, ""), type)
}

fun Context.saveModel(key: String?, modelClass: Any?) {
    sharedPref = getSharedPreferences("myrrPref", Context.MODE_PRIVATE)
    val prefsEditor = sharedPref!!.edit()
    val gson = Gson()
    prefsEditor.putString(key, gson.toJson(modelClass))
    prefsEditor.apply()
}


fun Context.compressFile(file: File): File {
    val imageCompress= ImageZipper(this)
        .setQuality(80)
        .setMaxWidth(200)
        .setMaxHeight(200)
        .compressToFile(file)
    return imageCompress
}


fun ImageView.loadUrl(path:String){
    Glide.with(this).load(path).into(this)
}


fun Context.setProgressBarColor(colorHashCode:String){
saveString("progressBarColorRR",colorHashCode)
}

