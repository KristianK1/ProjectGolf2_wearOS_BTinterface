package hr.kristiankliskovic.proba1_wearOs.ui.mainFragment

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.kristiankliskovic.proba1_wearOs.MainActivity
import hr.kristiankliskovic.proba1_wearOs.databinding.MainscreenBinding
import hr.kristiankliskovic.proba1_wearOs.utils.preferenceManager.getMac
import java.util.*


class mainFragment : Fragment() {
    lateinit var binding: MainscreenBinding

//    private val Bluetooth_name = "ProjectGolf"
    private var Bluetooth_uuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainscreenBinding.inflate(layoutInflater)
        requestPermission(arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
            MainActivity.BLUETOOTH_PERMISSION_CODE)

        binding.topLeftButton.setOnClickListener {
            openSettings()
        }
        binding.centerButton.setOnClickListener {
            BTsend("T?")
        }
        binding.bottomRightButton.setOnClickListener {
            BTsend("T=222,00x")
        }
        binding.topRightButton.setOnClickListener {
            BTsend("T=111")
        }
        binding.bottomLeftButton.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Pick a command")
            builder.setItems(availableCommands) { dialog, which ->
                BTsend(availableCommands[which])
            }
            builder.show()
        }

        Bluetooth_uuid = getMac()

        return binding.root
    }


    private fun requestPermission(permissions: Array<String>, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(MainActivity.activity,
                permissions[0]) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.activity, permissions, requestCode)
        }
    }

    fun BTsend(send: String) {
        try {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val myDevice = bluetoothAdapter.getRemoteDevice(Bluetooth_uuid)

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.BLUETOOTH),
                    1001
                )

                return
            }
            myDevice.createBond()

            myDevice.uuids.size
            val socket = myDevice.createRfcommSocketToServiceRecord(myDevice.uuids[0].uuid)
            socket.connect()
            val outputStream = socket.outputStream
            val inStream = socket.inputStream
            outputStream.write(send.toByteArray())
            val time1: Long = current_miliseconds()
            var reply = ""
            while (current_miliseconds() - time1 < 3000) {  //PONOC fix this
                while (inStream.available() > 0) reply += inStream.read().toChar()
                if (reply.contains("T=111")) break
                if (reply.contains("T=222,") && reply.length == 12) break
                if (reply.contains("Error")) break
            }
            socket.close()
            var print = reply
            if (print.length === 0) print = "Bluetooth comm failed"
            Toast.makeText(context, print, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Bluetooth comm failed",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun current_miliseconds(): Long {
        val cal = Calendar.getInstance()
        return cal.timeInMillis
    }

    companion object {
        val Tag = "main"

        fun create(): Fragment {
            return mainFragment()
        }
    }

    fun openSettings() {
        val action = mainFragmentDirections.actionMainFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private val availableCommands = arrayOf(
        "T=222,001",
        "T=222,002",
        "T=222,005",
        "T=222,010",
        "T=222,020",
        "T=222,030",
        "T=222,060",
        "T=222,120",
        "T=222,360",
        "BATT_EMPTY",
        "RESET_DEVICE"
    );
}