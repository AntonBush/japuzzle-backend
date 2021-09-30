package ru.bmstu.japuzzle

object Utils {
    fun <E> matrixInvariant(matrix: List<List<E>>): Boolean {
        if (matrix.isEmpty()) {
            return false
        }
        val rowLength = matrix[0].size
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