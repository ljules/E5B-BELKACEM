package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Arme
import org.springframework.data.jpa.repository.JpaRepository

interface ArmeDao : JpaRepository<Arme, Long> {
}