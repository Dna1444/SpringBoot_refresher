package com.ddcodeing.sprint_boot_refresher.database.repository

import com.ddcodeing.sprint_boot_refresher.database.model.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository: JpaRepository<Note, Long> {
    fun findByUserId(userID: Long) : List<Note>
}