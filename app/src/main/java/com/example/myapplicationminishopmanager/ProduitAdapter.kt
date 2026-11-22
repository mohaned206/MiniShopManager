package com.example.myapplicationminishopmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ProduitAdapter(
    private var liste: List<Produit>,
    private val onClick: (Produit) -> Unit
) : RecyclerView.Adapter<ProduitAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgProduit)
        val nom: TextView = v.findViewById(R.id.tvNom)
        val prix: TextView = v.findViewById(R.id.tvPrix)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_produit, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = liste[position]
        holder.nom.text = p.nom
        holder.prix.text = String.format(Locale.US, "%.2f DT", p.prix)

        val ctx = holder.itemView.context
        val resId = ctx.resources.getIdentifier(p.image, "drawable", ctx.packageName)
        if (resId != 0) {
            holder.img.setImageResource(resId)
        } else {
            holder.img.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener { onClick(p) }
    }

    override fun getItemCount(): Int = liste.size

    fun filtrer(nouvelle: List<Produit>) {
        liste = nouvelle
        notifyDataSetChanged()
    }
}