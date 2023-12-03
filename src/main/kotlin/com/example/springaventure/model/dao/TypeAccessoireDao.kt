package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.TypeAccessoire
import org.springframework.data.jpa.repository.JpaRepository

interface TypeAccessoireDao : JpaRepository<TypeAccessoire, Long> {
}