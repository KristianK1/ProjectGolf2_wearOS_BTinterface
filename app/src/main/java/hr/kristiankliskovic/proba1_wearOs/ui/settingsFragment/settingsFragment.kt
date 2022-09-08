package hr.kristiankliskovic.proba1_wearOs.ui.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.kristiankliskovic.proba1_wearOs.databinding.SettingsfragmentBinding
import hr.kristiankliskovic.proba1_wearOs.utils.preferenceManager.saveMac

class settingsFragment: Fragment() {
    lateinit var binding: SettingsfragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsfragmentBinding.inflate(layoutInflater)
        binding.saveMACbt.setOnClickListener {
            saveMAC()
        }
        return binding.root
    }

    private fun saveMAC(){
        saveMac(binding.macEt.text.toString())
        findNavController().navigateUp()
    }

    companion object {
        val Tag = "settings"

        fun create(): Fragment {
            return settingsFragment()
        }
    }
}