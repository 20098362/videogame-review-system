package persistence

/**
 * This interface acts as the template to allow other classes to utilise the persistence functions
 */
interface Serializer {
    /**
     * Takes in the object to be written to the chosen file using the chose file format
     * @param obj to be saved (Company)
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Returns the contents of the chosen file to memory to be manipulated
     * @return Any file found or null
     */
    @Throws(Exception::class)
    fun read(): Any?
}
