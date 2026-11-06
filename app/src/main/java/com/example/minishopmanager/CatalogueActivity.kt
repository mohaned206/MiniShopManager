package com.example.minishopmanager

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val produits = resources.getStringArray(R.array.produits)

        val icones = intArrayOf(
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_recent_history,
            android.R.drawable.ic_menu_manage,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_edit
        )

        listView.adapter = ProduitAdapter(this, produits, icones)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Produit sélectionné : $item", Toast.LENGTH_SHORT).show()
        }
    }
}