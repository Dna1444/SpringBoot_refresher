package com.ddcodeing.sprint_boot_refresher.database.repository

import com.ddcodeing.sprint_boot_refresher.database.model.RefreshToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {

    @Query("DELETE FROM RefreshToken r WHERE r.user.id = :userId AND r.hashedToken = :hashedToken")
    @Modifying
    @Transactional
    fun deleteByUserIdAndHashedToken(userId: Long, hashedToken: String)
    fun findByUserIdAndHashedToken(userId: Long, hashedToken: String)
}