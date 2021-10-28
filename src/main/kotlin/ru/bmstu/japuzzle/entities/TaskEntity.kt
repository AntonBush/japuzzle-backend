package ru.bmstu.japuzzle.entities

import ru.bmstu.japuzzle.models.SecureTask
import ru.bmstu.japuzzle.models.Task
import ru.bmstu.japuzzle.models.gamefield.GameField
import ru.bmstu.japuzzle.rgbToHex
import javax.persistence.*

@Entity
@Table(name="TaskEntity")
class TaskEntity(
    @ManyToOne
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
        val gf = gameField.toGameField()
        val t = SecureTask(
            id!!,
            user.toUser(),
            gf
        )
        if (solved) {
            t.check(gf)
        }
        return t
    }

    companion object {
        fun newInstance(gf: GameField, user: UserEntity): TaskEntity {
            return TaskEntity(
                user,
                false,
                GameFieldEmbeddable.newInstance(gf),
            )
        }
    }
}