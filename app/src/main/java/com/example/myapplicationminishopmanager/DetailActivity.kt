package com.example.myapplicationminishopmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Détail produit"

        val nom = intent.getStringExtra("nom") ?: ""
        val prix = intent.getDoubleExtra("prix", 0.0)
        val image = intent.getStringExtra("image") ?: ""
        val desc = intent.getStringExtra("description") ?: ""

        findViewById<TextView>(R.id.tvNomDetail).text = nom
        findViewById<TextView>(R.id.tvPrixDetail).text = String.format(Locale.US, "%.2f DT", prix)
        findViewById<TextView>(R.id.tvDescDetail).text = desc

        val resId = resources.getIdentifier(image, "drawable", packageName)
        if (resId != 0) {
            findViewById<ImageView>(R.id.imgDetail).setImageResource(resId)
        }

        findViewById<Button>(R.id.btnPanier).setOnClickListener {
            Panier.ajouter(Produit(nom, prix, image, desc))
            Snackbar.make(it, "$nom ajouté au panier", Snackbar.LENGTH_LONG)
                .setAction("VOIR") {
                    startActivity(Intent(this, PanierActivity::class.java))
                }
                .show()
        }

        findViewById<Button>(R.id.btnRetour).setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}