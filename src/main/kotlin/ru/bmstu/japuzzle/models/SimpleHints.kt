package ru.bmstu.japuzzle.models

import ru.bmstu.japuzzle.HintCreator

class SimpleHints: Hints {
    override val lines: List<Hint>,
    override val columns: List<Hint>

    constructor(width: Int, height: Int, creator: HintCreator) {
        if (width < 1 || height < 1) {
            throw IllegalArgumentException("Width or height is less 1")
        }
        lines = List(height) { creator.createHint(height) }
        columns = List(width) { creator.createHint(width) }
    }
}