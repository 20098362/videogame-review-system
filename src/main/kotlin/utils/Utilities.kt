package utils

import models.Company
import models.VideoGame

/**
 * This object is responsible for providing methods which help validate certain user inputs or
 * helps format string for better UX.
 */
object Utilities {
    /**
     * Takes in the user's input and checks to see if it is a valid input
     * @param index entered by the user
     * @return Boolean to give feedback on if it's a valid input (true) or a false input (false)
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    /**
     * Takes in a string and organises it to fit a certain format
     * @param companiesToFormat string taken in
     * @return Ordered string returned
     */
    @JvmStatic
    fun formatListString(companiesToFormat: List<Company>): String =
        companiesToFormat
            .joinToString(separator = "\n") { company ->  "$company" }

    /**
     * Takes in a string and organises it to fit a certain format
     * @param videoGamesToFormat string taken in
     * @return Ordered string returned
     */
    @JvmStatic
    fun formatSetString(videoGamesToFormat: Set<VideoGame>): String =
        videoGamesToFormat
            .joinToString(separator = "\n") { videoGame ->  "\t$videoGame" }
}