package com.example.myapplicationminishopmanager

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)
        title = "Catalogue"

        val json = assets.open("produits.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<Produit>>() {}.type
        val tous: List<Produit> = Gson().fromJson(json, type)

        val tvVide = findViewById<TextView>(R.id.tvVide)

        val produitAdapter = ProduitAdapter(tous) { p ->
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("nom", p.nom)
            i.putExtra("prix", p.prix)
            i.putExtra("image", p.image)
            i.putExtra("description", p.description)
            startActivity(i)
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = produitAdapter

        findViewById<EditText>(R.id.etRecherche).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s.toString().trim()
                val resultat = tous.filter { it.nom.contains(q, ignoreCase = true) }
                produitAdapter.filtrer(resultat)
                tvVide.visibility = if (resultat.isEmpty()) View.VISIBLE else View.GONE
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_catalogue, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_panier)?.title = "Panier (${Panier.nombreArticles()})"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_panier -> {
                startActivity(Intent(this, PanierActivity::class.java))
                true
            }
            R.id.action_logout -> {
                AlertDialog.Builder(this)
                    .setTitle("Déconnexion")
                    .setMessage("Voulez-vous vraiment vous déconnecter ?")
                    .setPositiveButton("Oui") { _, _ ->
                        Panier.vider()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    .setNegativeButton("Annuler", null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}