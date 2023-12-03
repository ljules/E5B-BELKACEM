package com.example.springaventure.model.dao;

import com.example.springaventure.model.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleDao : JpaRepository<Role, Long> {
}