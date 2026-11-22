package com.example.myapplicationminishopmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class PanierAdapter(
    private val onChange: () -> Unit
) : RecyclerView.Adapter<PanierAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgPanier)
        val nom: TextView = v.findViewById(R.id.tvNomPanier)
        val prix: TextView = v.findViewById(R.id.tvPrixPanier)
        val qte: TextView = v.findViewById(R.id.tvQte)
        val moins: Button = v.findViewById(R.id.btnMoins)
        val plus: Button = v.findViewById(R.id.btnPlus)
        val supprimer: TextView = v.findViewById(R.id.btnSupprimer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_panier, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val l = Panier.lignes[position]
        holder.nom.text = l.produit.nom
        holder.prix.text = String.format(Locale.US, "%.2f DT", l.produit.prix * l.quantite)
        holder.qte.text = l.quantite.toString()

        val ctx = holder.itemView.context
        val resId = ctx.resources.getIdentifier(l.produit.image, "drawable", ctx.packageName)
        if (resId != 0) holder.img.setImageResource(resId)
        else holder.img.setImageResource(android.R.drawable.ic_menu_gallery)

        holder.plus.setOnClickListener {
            Panier.augmenter(l)
            notifyDataSetChanged()
            onChange()
        }
        holder.moins.setOnClickListener {
            Panier.diminuer(l)
            notifyDataSetChanged()
            onChange()
        }
        holder.supprimer.setOnClickListener {
            Panier.retirer(l)
            notifyDataSetChanged()
            onChange()
        }
    }

    override fun getItemCount(): Int = Panier.lignes.size
}