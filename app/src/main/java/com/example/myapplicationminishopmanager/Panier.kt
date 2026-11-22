package com.example.myapplicationminishopmanager

data class LignePanier(val produit: Produit, var quantite: Int)

object Panier {
    val lignes = mutableListOf<LignePanier>()

    fun ajouter(p: Produit) {
        val l = lignes.find { it.produit.nom == p.nom }
        if (l != null) l.quantite++ else lignes.add(LignePanier(p, 1))
    }

    fun augmenter(l: LignePanier) {
        l.quantite++
    }

    fun diminuer(l: LignePanier) {
        if (l.quantite > 1) l.quantite-- else lignes.remove(l)
    }

    fun retirer(l: LignePanier) {
        lignes.remove(l)
    }

    fun vider() = lignes.clear()

    fun total(): Double = lignes.sumOf { it.produit.prix * it.quantite }

    fun nombreArticles(): Int = lignes.sumOf { it.quantite }
}