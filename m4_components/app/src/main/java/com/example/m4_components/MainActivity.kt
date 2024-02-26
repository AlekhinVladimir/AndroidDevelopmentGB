package com.example.m4_components


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.m4_components.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivityState()
    }

    private fun checkConditions(): Boolean {
        val conditionNameField = binding.textInputName.length() in 1..40
        val conditionPhoneField = binding.textInputPhone.text.toString().isNotBlank() &&
                binding.textInputPhone.toString().isNotEmpty()
        val conditionSexIsChecked = binding.radioButtonMale.isChecked ||
                binding.radioButtonFemale.isChecked

        val conditionNotificationSwitch = (binding.switchNotification.isChecked &&
                binding.checkBoxAuthorization.isChecked || binding.checkBoxNewItems.isChecked)
                || !binding.switchNotification.isChecked

        return conditionSexIsChecked && conditionNameField
                && conditionPhoneField && conditionNotificationSwitch
    }

    private fun changeSaveButtonState() {
        if (checkConditions()) {
            binding.buttonSave.isEnabled = true
        } else {
            binding.buttonSave.isEnabled = false
        }
    }


    private fun initActivityState() {
        val numberOfPoints = Random.nextInt(101)

        binding.progressBar.secondaryProgress = numberOfPoints

        binding.textViewPointsOfNumber.text =
            String.format(getString(R.string.points_of_number, numberOfPoints))

        binding.checkBoxNewItems.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                changeSaveButtonState()
            } else {
                changeSaveButtonState()
            }
        }

        binding.checkBoxAuthorization.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                changeSaveButtonState()
            } else {
                changeSaveButtonState()
            }
        }

        binding.textInputName.doAfterTextChanged {
            changeSaveButtonState()
        }

        binding.textInputPhone.doAfterTextChanged {
            changeSaveButtonState()
        }


        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxAuthorization.isEnabled = true
                binding.checkBoxNewItems.isEnabled = true
                changeSaveButtonState()
            } else {
                binding.checkBoxAuthorization.isEnabled = !binding.checkBoxAuthorization.isEnabled
                binding.checkBoxNewItems.isEnabled = !binding.checkBoxNewItems.isEnabled
                binding.checkBoxAuthorization.isChecked = false
                binding.checkBoxNewItems.isChecked = false
                changeSaveButtonState()
            }
        }


        binding.radioGroupForChoiceSex.setOnCheckedChangeListener { _, _ ->
            changeSaveButtonState()
        }

        binding.buttonSave.setOnClickListener {
            Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
        }
    }

}