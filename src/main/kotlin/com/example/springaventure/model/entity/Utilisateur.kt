package com.example.springaventure.model.entity

import jakarta.persistence.*
/**
 * Classe représentant un utilisateur du système.
 *
 * @property id Identifiant unique de l'utilisateur.
 * @property email Adresse e-mail de l'utilisateur (unique).
 * @property mdp Mot de passe de l'utilisateur.
 * @property roles Liste des rôles associés à l'utilisateur.
 * @property campagnes Liste des campagnes associées à l'utilisateur.
 * @property personnages Liste des personnages associés à l'utilisateur.
 */
@Entity
class Utilisateur constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    // Adresse e-mail de l'utilisateur (unique)
    @Column(unique = true)
    var email:String,

    // Mot de passe de l'utilisateur
    var mdp:String,

    // Liste des rôles associés à l'utilisateur
    @ManyToMany
    @JoinTable(
        name = "Utilisateur_roles",
        joinColumns = [JoinColumn(name = "utilisateur_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")]
    )
    var roles: MutableList<Role> = mutableListOf()
) {
    // Liste des campagnes associées à l'utilisateur
    @OneToMany(mappedBy = "utilisateur", cascade = [CascadeType.REMOVE])
     var campagnes: MutableList<Campagne> = mutableListOf()

    // Liste des personnages associés à l'utilisateur
    @OneToMany(mappedBy = "utilisateur", orphanRemoval = true)
     var personnages: MutableList<Personnage> = mutableListOf()

    // Vous pouvez ajouter d'autres méthodes ou propriétés au besoin
}
