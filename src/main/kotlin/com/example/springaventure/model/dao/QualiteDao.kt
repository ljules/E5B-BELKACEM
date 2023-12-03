package com.example.springaventure.model.dao

import com.example.springaventure.model.entity.Qualite
import org.springframework.data.jpa.repository.JpaRepository

interface QualiteDao :JpaRepository<Qualite,Long> {
}