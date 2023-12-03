package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Campagne
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CampagneDao : JpaRepository<Campagne, Long> {

    // Requête SQL équivalente: SELECT * FROM Campagne WHERE LOWER(nom) LIKE LOWER(CONCAT('%', :nom, '%'))
    fun findByNomContainingIgnoreCaseAndUtilisateur_Email(nom: String, email: String, pageable: Pageable): Page<Campagne>


    // Requête SQL équivalente: SELECT * FROM Campagne ORDER BY dateMaj DESC
    fun findAllByOrderByDateMajDesc(): List<Campagne>

    @Query("select c from Campagne c where upper(c.utilisateur.email) = upper(?1)")
    fun findByUtilisateur_EmailIgnoreCase(email: String): List<Campagne>

}