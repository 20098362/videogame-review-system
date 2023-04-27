package utils

import models.Company

object Utilities {
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    @JvmStatic
    fun formatListString(companiesToFormat: List<Company>): String =
        companiesToFormat
            .joinToString(separator = "\n") { company ->  "$company" }

    @JvmStatic
    fun formatSetString(videoGamesToFormat: Set<VideoGame>): String =
        videoGamesToFormat
            .joinToString(separator = "\n") { videoGame ->  "\t$videoGame" }
}