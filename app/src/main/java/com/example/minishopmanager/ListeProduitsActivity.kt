package com.example.minishopmanager

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class ListeProduitsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_produits)

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val noms = ArrayList<String>()
        val prix = ArrayList<Int>()
        lireProduitsDepuisJSON(noms, prix)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, noms)
        listView.adapter = adapter

        // Lorsqu'on clique sur un produit, on l'ajoute au panier (SQLite)
        listView.setOnItemClickListener { _, _, position, _ ->
            val produitSelectionne = noms[position]
            Toast.makeText(this, "$produitSelectionne ajouté au panier", Toast.LENGTH_SHORT).show()

            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("nom", produitSelectionne)
                put("prix", prix[position])
            }
            db.insert("panier", null, values)
            db.close()
        }
    }

    private fun lireProduitsDepuisJSON(noms: ArrayList<String>, prix: ArrayList<Int>) {
        val inputStream = assets.open("produits.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("produits")
        for (i in 0 until jsonArray.length()) {
            val p = jsonArray.getJSONObject(i)
            noms.add(p.getString("nom"))
            prix.add(p.getInt("prix"))
        }
    }
}