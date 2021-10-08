package ru.bmstu.japuzzle.entities

import ru.bmstu.japuzzle.models.User
import javax.persistence.*

@Entity
@Table(name="UserEntity")
class UserEntity(
    @Column(nullable = false, unique = true)
    val name: String,

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun toUser(): User {
        return User(id!!, name)
    }
}