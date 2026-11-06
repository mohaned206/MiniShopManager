package com.example.myapplicationminishopmanager
import android.content.ContentValues
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

data class Produit(val nom: String, val prix: Double)

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val produits = lireProduitsDepuisJSON()

        // Texte affiché : "Nom - prix DT"
        val affichage = produits.map { "${it.nom} - ${it.prix} DT" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, affichage)

        listView.setOnItemClickListener { _, _, position, _ ->
            val produit = produits[position]

            val values = ContentValues().apply {
                put("nom", produit.nom)
                put("prix", produit.prix)
            }

            val db = DBHelper(this).writableDatabase
            val resultat = db.insert("panier", null, values)
            db.close()

            if (resultat != -1L) {
                Toast.makeText(this, "${produit.nom} ajouté au panier", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun lireProduitsDepuisJSON(): List<Produit> {
        val liste = ArrayList<Produit>()
        val jsonString = assets.open("produits.json")
            .bufferedReader().use { it.readText() }

        val tableau = JSONObject(jsonString).getJSONArray("produits")
        for (i in 0 until tableau.length()) {
            val p = tableau.getJSONObject(i)
            liste.add(Produit(p.getString("nom"), p.getDouble("prix")))
        }
        return liste
    }
}