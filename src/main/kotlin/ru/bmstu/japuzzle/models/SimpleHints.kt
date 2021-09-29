package ru.bmstu.japuzzle.models

import ru.bmstu.japuzzle.HintCreator

data class SimpleHints(
    override val rows: List<List<Hint>>,
    override val columns: List<List<Hint>>
) : Hints