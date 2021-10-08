package ru.bmstu.japuzzle.entities

import ru.bmstu.japuzzle.models.SecureTask
import ru.bmstu.japuzzle.models.Task
import javax.persistence.*

@Entity
@Table(name="TaskEntity")
class TaskEntity(
    @ManyToOne
    @Column(nullable = false)
    val user: UserEntity,

    @Column(nullable = false)
    var solved: Boolean,

    @Column(nullable = false)
    val gameField: GameFieldEmbeddable,

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun toTask(): Task {
        return SecureTask(
            id!!,
            user.toUser(),
            gameField.toGameField()
        )
    }
}