package com.example.m11_timer_data_storage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.m11_timer_data_storage.databinding.ActivityMainBinding
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

private const val FILE_NAME="testFile.txt"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedText = getTextFromSharedPreference()
        binding.textViewSaveData.text = savedText

        binding.saveDataButton.setOnClickListener {
            val inputText = binding.editText.text
            saveText(inputText.toString())
        }

        binding.getDataButton.setOnClickListener {
            val readedText = readText()
            binding.textViewSaveData.text = readedText
        }

        binding.clearDataButton.setOnClickListener{
            deleteFile(FILE_NAME)
            binding.editText.text.clear()
        }
    }
    private fun saveTextToSharedPreference(text: String) {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("savedText", text)
        editor.apply()
    }
    private fun getTextFromSharedPreference(): String {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        return sharedPref.getString("savedText", "") ?: ""
    }

    private fun saveText(textForSave: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            fos.write(textForSave.toByteArray())
            saveTextToSharedPreference(textForSave)

            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            fos?.close()
        }
    }

    private fun readText(): String {
        var fis: FileInputStream? = null
        return try {
            fis = openFileInput(FILE_NAME)
            val bytes = ByteArray(fis.available())
            fis.read(bytes)
            String(bytes)
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            ""
        } finally {
            fis?.close()
        }
    }


}
