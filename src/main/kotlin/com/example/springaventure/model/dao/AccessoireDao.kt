package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Accessoire
import org.springframework.data.jpa.repository.JpaRepository

interface AccessoireDao : JpaRepository<Accessoire, Long> {
}