package ru.bmstu.japuzzle.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.bmstu.japuzzle.models.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
}