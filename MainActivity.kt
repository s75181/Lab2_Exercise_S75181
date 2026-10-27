package com.example.lab2nameanalyzer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etDateOfBirth = findViewById<EditText>(R.id.etDateOfBirth)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)

        btnAnalyze.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val dobText = etDateOfBirth.text.toString().trim()

            if (fullName.isEmpty()) {
                etFullName.error = "Please enter your full name"
                return@setOnClickListener
            }

            if (dobText.isEmpty()) {
                etDateOfBirth.error = "Please enter your date of birth"
                return@setOnClickListener
            }

            val formatter = DateTimeFormatter
                .ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT)

            val dob = try {
                LocalDate.parse(dobText, formatter)
            } catch (e: DateTimeParseException) {
                etDateOfBirth.error = "Use dd/MM/yyyy, e.g. 15/08/2004"
                return@setOnClickListener
            }

            if (dob.isAfter(LocalDate.now())) {
                etDateOfBirth.error = "Date of birth cannot be in the future"
                return@setOnClickListener
            }

            val intent = Intent(this, AnalysisActivity::class.java)
            intent.putExtra("FULL_NAME", fullName)
            intent.putExtra("DATE_OF_BIRTH", dob.toString())
            startActivity(intent)
        }
    }
}
