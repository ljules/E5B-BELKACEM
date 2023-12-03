package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Armure
import org.springframework.data.jpa.repository.JpaRepository

interface ArmureDao : JpaRepository<Armure, Long> {
}