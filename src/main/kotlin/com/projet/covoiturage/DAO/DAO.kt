package com.projet.covoiturage.DAO

interface DAO<T> {
    fun chercherTous(): List<T>
    fun chercherParId(id: Int): T?
    fun ajouter(t:T): T?
    fun supprimer(id:Int): Boolean
}