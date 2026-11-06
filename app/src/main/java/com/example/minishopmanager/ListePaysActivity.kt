package com.example.minishopmanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListePaysActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_pays)

        val listView = findViewById<ListView>(R.id.listViewPays)

        // Charger le tableau de pays défini dans strings.xml
        val countries = resources.getStringArray(R.array.countries)

        // Adaptateur: lie les données au ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        listView.adapter = adapter

        // Gérer le clic sur un élément
        listView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Pays sélectionné : $item", Toast.LENGTH_SHORT).show()
        }
    }
}