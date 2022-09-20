package hr.kristiankliskovic.proba1_wearOs

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import hr.kristiankliskovic.proba1_wearOs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        activity = this@MainActivity
        setContentView(binding.root)
    }

    companion object {
        lateinit var application: Application
        lateinit var activity: Activity
        const val BLUETOOTH_PERMISSION_CODE = 101
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        Log.i("perms", "onRequetResult")
        Log.i("perms", Gson().toJson(requestCode))
        Log.i("perms", Gson().toJson(permissions))
        Log.i("perms", Gson().toJson(grantResults))

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("perms", "after super")
        if (requestCode == BLUETOOTH_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity,
                    "Bluetooth Permission Granted",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity,
                    "Bluetooth Permission Denied",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}