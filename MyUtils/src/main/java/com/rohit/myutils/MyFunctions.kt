import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.google.gson.Gson
import com.rohit.myutils.R

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