package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Utilisateur
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UtilisateurDao : JpaRepository<Utilisateur, Long> {

    @Query("select u from Utilisateur u where upper(u.email) = upper(?1)")
    fun findByEmail(email: String): Utilisateur?

}