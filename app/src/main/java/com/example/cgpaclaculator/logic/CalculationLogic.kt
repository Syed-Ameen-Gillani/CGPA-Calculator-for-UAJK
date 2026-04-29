package com.example.cgpaclaculator.logic

object CalculationLogic {
    // CS & IT Grading Scale for UAJK
    fun getGpaFromPercentage(percentage: Double): Double {
        return when (percentage) {
            in 80.0..100.0 -> 4.0
            in 78.5..79.9 -> 3.9
            in 77.0..78.5 -> 3.8
            in 75.5..77.0 -> 3.7
            in 74.0..75.5 -> 3.6
            in 72.5..74.0 -> 3.5
            in 71.0..72.5 -> 3.4
            in 69.5..71.0 -> 3.3
            in 68.0..69.5 -> 3.2
            in 65.5..68.0 -> 3.1
            in 65.0..65.5 -> 3.0
            in 63.5..65.0 -> 2.9
            in 62.0..63.5 -> 2.8
            in 60.5..62.0 -> 2.7
            in 59.0..60.5 -> 2.6
            in 57.5..59.0 -> 2.5
            in 56.0..57.5 -> 2.4
            in 54.5..56.0 -> 2.3
            in 53.0..54.5 -> 2.2
            in 51.5..53.0 -> 2.1
            in 50.0..51.5 -> 2.0
            else -> 0.0
        }
    }

    fun calculateSemesterGpa(courses: List<Pair<Double, Int>>): Double {
        if (courses.isEmpty()) return 0.0
        val totalCredits = courses.sumOf { it.second }
        if (totalCredits == 0) return 0.0
        val weightedSum = courses.sumOf { it.first * it.second }
        return String.format("%.2f", weightedSum / totalCredits).toDouble()
    }

    fun calculateCgpa(semesters: List<Pair<Double, Int>>): Double {
        return calculateSemesterGpa(semesters)
    }

    fun getOverallGrade(cgpa: Double): String {
        return when (cgpa) {
            in 3.66..4.0 -> "First Class Upper"
            in 3.0..3.659 -> "First Class Lower"
            in 2.5..2.99 -> "Second Class Upper"
            in 2.0..2.49 -> "Second Class Lower"
            else -> "Below Standard"
        }
    }

    fun getPercentageFromGpa(gpa: Double): Double {
        return when (gpa) {
            in 4.0..10.0 -> 80.0
            in 3.9..4.0 -> 78.5
            in 3.8..3.9 -> 77.0
            in 3.7..3.8 -> 75.5
            in 3.6..3.7 -> 74.0
            in 3.5..3.6 -> 72.5
            in 3.4..3.5 -> 71.0
            in 3.3..3.4 -> 69.5
            in 3.2..3.3 -> 68.0
            in 3.1..3.2 -> 65.5
            in 3.0..3.1 -> 65.0
            in 2.9..3.0 -> 63.5
            in 2.8..2.9 -> 62.0
            in 2.7..2.8 -> 60.5
            in 2.6..2.7 -> 59.0
            in 2.5..2.6 -> 57.5
            in 2.4..2.5 -> 56.0
            in 2.3..2.4 -> 54.5
            in 2.2..2.3 -> 53.0
            in 2.1..2.2 -> 51.5
            in 2.0..2.1 -> 50.0
            else -> 0.0
        }
    }

    fun getTotalMarks(credits: Int): Int {
        return credits * 50
    }
}
