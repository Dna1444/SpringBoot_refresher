package com.ddcodeing.sprint_boot_refresher.controller

import com.ddcodeing.sprint_boot_refresher.database.model.Note
import com.ddcodeing.sprint_boot_refresher.database.repository.NoteRepository
import com.ddcodeing.sprint_boot_refresher.database.repository.UserRepository
import com.ddcodeing.sprint_boot_refresher.dto.NoteRequest
import com.ddcodeing.sprint_boot_refresher.dto.NoteResponse
import jakarta.persistence.GenerationType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NoteController(
    private val repository: NoteRepository,
    private val userRepository: UserRepository
) {

    @PostMapping
    fun save(
        @RequestBody body: NoteRequest
    ): NoteResponse {
        val userId = SecurityContextHolder.getContext().authentication.principal as Long

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val note = if (body.id != null) {
            // Update existing note
            val existingNote = repository.findById(body.id)
                .orElseThrow { IllegalArgumentException("Note with ID ${body.id} not found") }

            existingNote.copy(
                title = body.title,
                content = body.content,
                color = body.color
            )
        } else {
            // Create new note
            Note(
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                user = user
            )
        }

        val savedNote = repository.save(note)

        return savedNote.toResponse()
    }

    @GetMapping
    fun findByUserId(): List<NoteResponse> {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as Long
        return repository.findByUserId(ownerId).map{
            it.toResponse()
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: Long) {
        val note = repository.findById(id).orElseThrow {
            IllegalArgumentException("Note not found")
        }
        val ownerId = SecurityContextHolder.getContext().authentication.principal as Long

        if(note.user.id == ownerId) {
            repository.deleteById(id)
        }
    }

    private fun Note.toResponse(): NoteResponse {
        return NoteResponse(
            id = id,
            title = title,
            content = content,
            color = color,
            createdAt = createdAt
        )
    }
}