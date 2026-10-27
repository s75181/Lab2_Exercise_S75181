package com.example.lab2nameanalyzer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.Period
import java.time.format.TextStyle
import java.util.Locale

class AnalysisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        val fullName = intent.getStringExtra("FULL_NAME").orEmpty()
        val dobText = intent.getStringExtra("DATE_OF_BIRTH").orEmpty()

        val nameWithoutSpaces = fullName.replace(" ", "")
        val characterCount = nameWithoutSpaces.length
        val vowelCount = fullName.count {
            it.lowercaseChar() in "aeiou"
        }

        val repeatedCharacters = fullName
            .filter { it != ' ' }
            .lowercase()
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .keys
            .sorted()
            .joinToString(", ")

        val dob = LocalDate.parse(dobText)
        val today = LocalDate.now()
        val age = Period.between(dob, today).years
        val dayOfBirth = dob.dayOfWeek.getDisplayName(
            TextStyle.FULL, Locale.ENGLISH
        )

        findViewById<TextView>(R.id.tvCharacterCount).text =
            "Total characters (excluding spaces): $characterCount"

        findViewById<TextView>(R.id.tvVowelCount).text =
            "Number of vowels: $vowelCount"

        findViewById<TextView>(R.id.tvRepeatedCharacters).text =
            "Repeated characters: ${repeatedCharacters.ifEmpty { "None" }}"

        findViewById<TextView>(R.id.tvAge).text =
            "Current age: $age years"

        findViewById<TextView>(R.id.tvDayOfBirth).text =
            "Day of birth: $dayOfBirth"

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
