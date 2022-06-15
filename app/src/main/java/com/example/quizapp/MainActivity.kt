package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    var category : String = ""
    var difficulty: String = ""

    val categories = arrayOf("","9", "17", "18", "19", "22", "23", "26", "27")
    // val difficulties = arrayOf("easy", "medium", "hard")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigatorButton : Button = findViewById(R.id.navigator_button)
        navigatorButton.setOnClickListener(this)

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
        if (parent == findViewById(R.id.difficulty)) {
            difficulty = parent.getItemAtPosition(pos).toString()
        }  else if (parent == findViewById(R.id.category)){
            category = categories[pos]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }


    fun checkSettings() : Boolean {
        if (category.isNotEmpty() && difficulty.isNotEmpty()) {
            return true
        }
        if (category.isEmpty() && difficulty.isEmpty()) {
            showTheToast("Bitte suche dir eine Kategorie und eine Schwierigkeit aus.")
        } else if(difficulty.isEmpty()) {
            showTheToast("Bitte suche dir eine Schwierigkeit aus.")
        } else {
            showTheToast("Bitte suche dir eine Kategorie aus.")
        }
        return false
    }

    fun showTheToast (message: String) {
        val context : Context = getApplicationContext()
        val text : String = message
        val duration = Toast.LENGTH_SHORT;
        val toast: Toast = Toast.makeText(context,text,duration)
        toast.show()
    }

    override fun onClick(p0: View?) {
        if(checkSettings()) {
            val settings: Bundle = Bundle()
            settings.putString("CATEGORY", category)
            settings.putString("DIFFICULTY", difficulty)
            val intent: Intent = Intent(this, QuizQuestionActivtiy::class.java)
            intent.putExtras(settings)
            startActivity(intent)
        }
    }


}