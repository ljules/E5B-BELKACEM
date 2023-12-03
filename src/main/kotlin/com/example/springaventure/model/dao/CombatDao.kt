package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Combat
import org.springframework.data.jpa.repository.JpaRepository

interface CombatDao : JpaRepository<Combat, Long> {
}