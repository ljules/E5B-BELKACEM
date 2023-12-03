package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Bombe
import org.springframework.data.jpa.repository.JpaRepository

interface BombeDao : JpaRepository<Bombe, Long> {
}