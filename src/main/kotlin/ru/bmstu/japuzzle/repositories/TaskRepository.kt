package ru.bmstu.japuzzle.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.bmstu.japuzzle.entities.TaskEntity
import ru.bmstu.japuzzle.entities.UserEntity

@Repository
interface TaskRepository : JpaRepository<TaskEntity, Long> {
    fun findByUser(user: UserEntity): List<TaskEntity>?
}