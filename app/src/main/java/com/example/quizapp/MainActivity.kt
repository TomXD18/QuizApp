package com.example.quizapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerDifficulties: Spinner = findViewById(R.id.difficulty)
        val adapterDifficulties = ArrayAdapter.createFromResource(this, R.array.difficultyarray, android.R.layout.simple_spinner_item)
        adapterDifficulties.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerDifficulties.adapter = adapterDifficulties
        spinnerDifficulties.onItemSelectedListener = this

        val spinnerCategories: Spinner = findViewById(R.id.category)
        val adapterCategories = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item)
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerCategories.adapter = adapterCategories
        spinnerCategories.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // TODO: mach was
        // Another interface callback
    }

}