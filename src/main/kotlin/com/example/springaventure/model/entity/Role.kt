package com.example.springaventure.model.entity

import jakarta.persistence.*

/**
 * Classe représentant un rôle attribué à un utilisateur dans le système.
 *
 * @property id Identifiant unique du rôle.
 * @property nom Nom du rôle.
 * @property utilisateurs Liste des utilisateurs ayant ce rôle.
 */
@Entity
class Role constructor(
    // Identifiant unique du rôle
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    // Nom du rôle
    var nom: String,

    // Liste des utilisateurs ayant ce rôle
    @ManyToMany(mappedBy = "roles")
    var utilisateurs: MutableList<Utilisateur> = mutableListOf()
)
