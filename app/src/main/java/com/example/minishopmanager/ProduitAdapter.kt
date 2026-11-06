package com.example.minishopmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ProduitAdapter(
    context: Context,
    private val noms: Array<String>,
    private val icones: IntArray
) : ArrayAdapter<String>(context, R.layout.item_produit, noms) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_produit, parent, false)
        view.findViewById<ImageView>(R.id.imgProduit).setImageResource(icones[position])
        view.findViewById<TextView>(R.id.tvProduit).text = noms[position]
        return view
    }
}