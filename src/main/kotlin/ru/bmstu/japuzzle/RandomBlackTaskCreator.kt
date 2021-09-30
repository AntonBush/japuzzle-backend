package ru.bmstu.japuzzle

import ru.bmstu.japuzzle.models.BlackColor
import ru.bmstu.japuzzle.models.BlackTask
import ru.bmstu.japuzzle.models.Player
import ru.bmstu.japuzzle.models.Task
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class RandomBlackTaskCreator(
    private val maxWidth: Int,
    private val maxHeight: Int
) : TaskCreator {

    init {
        if (maxWidth < 1 || maxHeight < 1) {
            throw IllegalArgumentException("Width or height is less 1")
        }
    }

    override fun createTask(id: Long, player: Player): Task {
        val w = (1..maxWidth).random()
        val gameField = List((0..maxHeight).random()) { i ->
            val row = ArrayList<BlackColor?>()

            var free = w
            while (free > 0) {
                
            }
        }
        return BlackTask(
            id,
            player,
            gameField
        )
    }
}
}