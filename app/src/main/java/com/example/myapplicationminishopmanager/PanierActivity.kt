package com.example.myapplicationminishopmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class PanierActivity : AppCompatActivity() {

    private lateinit var tvTotal: TextView
    private lateinit var tvVide: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var btnCommander: Button
    private lateinit var btnVider: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panier)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mon panier"

        tvTotal = findViewById(R.id.tvTotal)
        tvVide = findViewById(R.id.tvPanierVide)
        recycler = findViewById(R.id.recyclerPanier)
        btnCommander = findViewById(R.id.btnCommander)
        btnVider = findViewById(R.id.btnVider)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = PanierAdapter { rafraichir() }

        btnVider.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Vider le panier")
                .setMessage("Supprimer tous les articles ?")
                .setPositiveButton("Oui") { _, _ ->
                    Panier.vider()
                    recycler.adapter?.notifyDataSetChanged()
                    rafraichir()
                }
                .setNegativeButton("Annuler", null)
                .show()
        }

        btnCommander.setOnClickListener {
            val total = String.format(Locale.US, "%.2f DT", Panier.total())
            AlertDialog.Builder(this)
                .setTitle("Confirmer la commande")
                .setMessage("Total à payer : $total")
                .setPositiveButton("Confirmer") { _, _ ->
                    Panier.vider()
                    recycler.adapter?.notifyDataSetChanged()
                    rafraichir()
                    Toast.makeText(this, "Commande validée, merci !", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Annuler", null)
                .show()
        }

        rafraichir()
    }

    private fun rafraichir() {
        val vide = Panier.lignes.isEmpty()
        tvVide.visibility = if (vide) View.VISIBLE else View.GONE
        recycler.visibility = if (vide) View.GONE else View.VISIBLE
        btnCommander.isEnabled = !vide
        btnVider.isEnabled = !vide
        tvTotal.text = String.format(Locale.US, "%.2f DT", Panier.total())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}