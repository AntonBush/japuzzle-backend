package ru.bmstu.japuzzle.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(nullable = false, unique = true)
    val name: String
)