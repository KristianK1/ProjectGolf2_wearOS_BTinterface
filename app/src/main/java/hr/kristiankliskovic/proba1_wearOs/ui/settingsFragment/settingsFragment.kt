package hr.kristiankliskovic.proba1_wearOs.ui.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.kristiankliskovic.proba1_wearOs.databinding.SettingsfragmentBinding
import hr.kristiankliskovic.proba1_wearOs.utils.preferenceManager.saveMac

class settingsFragment : Fragment() {
    lateinit var binding: SettingsfragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SettingsfragmentBinding.inflate(layoutInflater)
        binding.saveMACbt.setOnClickListener {
            saveMAC()
        }
        return binding.root
    }

    private fun saveMAC() {
        val mac = binding.macEt.text.toString()
        var validMac = true;
        val validChars = arrayOf('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F')
        if (mac.length == 17) {
            for ((index, value) in mac.withIndex()) {
                if ((index - 2) % 3 == 0) {
                    if (value != ':') {
                        validMac = false
                    }
                }
                else{
                    val find = validChars.find { o -> o == value }
                    if(find == null){
                        validMac = false
                    }
                }
            }
        } else {
            validMac = false;
        }
        if(validMac == true){
            saveMac(mac)
            findNavController().navigateUp()
        }
        else{
            Toast.makeText(context, "MAC adresa nije validna", Toast.LENGTH_SHORT)
        }
    }

    companion object {
        val Tag = "settings"

        fun create(): Fragment {
            return settingsFragment()
        }
    }
}