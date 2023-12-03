package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Personnage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PersonnageDao : JpaRepository<Personnage, Long> {


    @Query("select p from Personnage p where p.utilisateur.id = ?1 order by p.id DESC")
    fun findByUtilisateur_IdOrderByIdDesc(id: Long): List<Personnage>

}