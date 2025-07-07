package com.ddcodeing.sprint_boot_refresher.dto

import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class NoteRequest(
    val id: Long?,
    @field:NotBlank(message = "Title can't be blank.")
    val title: String,
    val content: String,
    val color: Long,
    val ownerId: Long
)

data class NoteResponse(
    val id: Long?,
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: Instant
)
