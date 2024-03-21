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
//    private var _binding: ActivityMainBinding? = null
//    private val binding: ActivityMainBinding
//        get() {
//            return _binding!!
//        }
//    private lateinit var repository: Repository
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        repository = Repository(this)

//        binding.textViewSaveData.text=repository.getText()

        binding.saveDataButton.setOnClickListener {
            val inputText = binding.editText.text
            saveText(inputText.toString())
//            repository.saveText(outString)
//            binding.textViewSaveData.text=outString
        }

        binding.getDataButton.setOnClickListener {
            val readedText = readText()

//            repository.saveText(outString)
            binding.textViewSaveData.text = readedText
        }
    }
//    override fun onPause() {
//        super.onPause()
//        // Сохранение текста при приостановке активности
//        val currentText = binding.editText.text.toString()
//        repository.saveText(currentText)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    private fun saveText(textForSave: String) {
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            fos.write(textForSave.toByteArray())

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
