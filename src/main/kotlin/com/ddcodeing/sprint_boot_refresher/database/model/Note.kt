package com.ddcodeing.sprint_boot_refresher.database.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "color", nullable = false)
    val color: Long,

    @Column(name = "content", nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant
)
