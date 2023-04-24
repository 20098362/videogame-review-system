package utils

object Utilities {
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)
}