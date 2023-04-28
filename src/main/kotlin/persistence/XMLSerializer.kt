package persistence

import models.Company
import models.VideoGame
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver

/**
 * XMLSerializer Persistence Manager
 *
 * This class is responsible for managing the reading and writing the xml file which stores the system data
 * and implements the Serializer interface
 *
 * @param file uses the designated file for storing data
 */
class XMLSerializer(private val file: File) : Serializer {
    /**
     * Reads the data from the xml file and puts it into the system
     * @return Any file found
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Company::class.java))
        xStream.allowTypes(arrayOf(VideoGame::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes the data currently in the system to the xml file
     * @param obj taken in to be stored (Company)
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}