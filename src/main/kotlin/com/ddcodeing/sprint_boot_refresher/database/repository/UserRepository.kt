package com.ddcodeing.sprint_boot_refresher.database.repository

import com.ddcodeing.sprint_boot_refresher.database.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}