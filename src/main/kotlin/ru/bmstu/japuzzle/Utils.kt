package ru.bmstu.japuzzle

object Utils {
    fun <E> matrixInvariant(matrix: List<List<E>>): Boolean {
        val rowLength = matrix.size
        if (rowLength < 1) {
            return false
        }
        for (row in matrix) {
            if (rowLength != row.size) {
                return false
            }
        }
        return true
    }
}