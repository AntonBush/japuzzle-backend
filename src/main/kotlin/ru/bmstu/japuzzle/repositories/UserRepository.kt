package ru.bmstu.japuzzle.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.bmstu.japuzzle.entities.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByName(name: String): UserEntity?
    fun existsByName(name: String): Boolean
}