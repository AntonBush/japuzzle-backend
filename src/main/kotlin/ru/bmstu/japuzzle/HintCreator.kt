package ru.bmstu.japuzzle

import ru.bmstu.japuzzle.models.Hint

interface HintCreator {
    fun createHint(maxCount: Int): Hint
}