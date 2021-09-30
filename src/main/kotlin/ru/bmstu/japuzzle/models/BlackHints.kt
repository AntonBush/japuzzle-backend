package ru.bmstu.japuzzle.models

import ru.bmstu.japuzzle.HintCreator

data class BlackHints(
    override val rows: List<List<BlackHint>>,
    override val columns: List<List<BlackHint>>
) : Hints