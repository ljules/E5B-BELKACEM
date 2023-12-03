package com.example.springaventure.model.dao

import com.example.springaventure.model.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemDao : JpaRepository<Item,Long> {
}