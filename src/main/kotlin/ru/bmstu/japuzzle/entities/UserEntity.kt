package ru.bmstu.japuzzle.entities

import ru.bmstu.japuzzle.models.User
import javax.persistence.*

@Entity(name="User")
class UserEntity(
    @Column(nullable = false, unique = true)
    var name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun toUser(): User? {
        val i = id;
        val n = name;
        return when(i) {
            null -> null
            else -> User(i, n);
        }
    }
}