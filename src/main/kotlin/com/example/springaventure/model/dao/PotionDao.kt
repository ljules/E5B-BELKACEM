package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Potion
import org.springframework.data.jpa.repository.JpaRepository

interface PotionDao : JpaRepository<Potion, Long> {
}