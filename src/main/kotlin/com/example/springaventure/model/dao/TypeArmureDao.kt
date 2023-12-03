package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.TypeArmure
import org.springframework.data.jpa.repository.JpaRepository

interface TypeArmureDao : JpaRepository<TypeArmure, Long> {
}