package hr.kristiankliskovic.proba1_wearOs.utils

import android.content.Context
import hr.kristiankliskovic.proba1_wearOs.mainSomething

object preferenceManager {

    const val CurrentMACkey = "myCurrMac"
    const val PREFS_FILE = "myPreferences"

    fun getMac(): String {
        return try {
            val sharedPreferences = mainSomething.application.getSharedPreferences(
                PREFS_FILE, Context.MODE_PRIVATE
            )
            sharedPreferences.getString(CurrentMACkey, "")!!
        } catch (e: Throwable) {
            "";
        }
    }

    fun saveMac(mac: String){
        val sharedPreferences = mainSomething.application.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(CurrentMACkey, mac)
        editor.apply()
    }
}