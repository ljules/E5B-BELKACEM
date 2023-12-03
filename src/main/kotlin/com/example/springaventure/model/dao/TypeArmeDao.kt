package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.TypeArme
import org.springframework.data.jpa.repository.JpaRepository

interface TypeArmeDao : JpaRepository<TypeArme, Long> {
}