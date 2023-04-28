/**
 * Video Game Review System
 * @version V1.0
 * @author Troy Barrett 20098362
 */

import utils.ScannerInput
import kotlin.system.exitProcess
import controllers.CompanyAPI
import models.Company
import models.VideoGame
import persistence.XMLSerializer
import java.io.File

private val companyAPI = CompanyAPI(XMLSerializer(File("reviewdata.xml")))

const val ansiReset = "\u001B[0m"
const val ansiRed = "\u001B[31m"
const val ansiGreen = "\u001B[32m"
const val ansiYellow = "\u001B[33m"
const val ansiBlue = "\u001B[34m"
const val ansiCyan = "\u001B[36m"

/**
 * This class is responsible for taking in an Int from the user to determine a menu choice
 * It also prints out a menu for the user to interact with
 *
 * @return Int;used as the menu controller option
 */
fun mainMenu() : Int {
    return ScannerInput.readNextInt(
        """ 
         > $ansiCyan--------------------------------------------$ansiReset
         > |       $ansiRed Video Game Review System $ansiReset         |
         > |$ansiRed Remember to add/load a company  $ansiReset         |
         > |$ansiRed before viewing statistics $ansiReset               |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Company Menu $ansiReset                           |
         > |  $ansiBlue 1) Company related inputs $ansiReset             |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Video Game Menu $ansiReset                        |
         > |  $ansiBlue 2) Video related related inputs $ansiReset       |
         > $ansiCyan--------------------------------------------$ansiReset
         > | $ansiRed Company & Video Game Listing Statistics $ansiReset|
         > |  $ansiBlue 3) List Statistics $ansiReset                    |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiBlue 20) Save Current Data$ansiReset                  |
         > |  $ansiBlue 21) Load Existing Data$ansiReset                 |
         > $ansiCyan--------------------------------------------$ansiReset
         > |  $ansiYellow 0) Exit App $ansiReset                           |
         > $ansiCyan--------------------------------------------$ansiReset
         > $ansiGreen==>> """.trimMargin(">")
    )
}

/**
 * Takes in the option from mainMenu to determine what function the user wishes to perform
 */
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> listCompanyMenu()
            2 -> listGamesMenu()
            3 -> listStatsMenu()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}

/**
 * Prints out a menu which deals with the Company functions
 * Also takes in an Int from the user to determine which function they wish to perform
 */
fun listCompanyMenu() {
        val option = ScannerInput.readNextInt(
            """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiRed No. of companies: ${companyAPI.numberOfCompanies()} $ansiReset                     |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) Create new company   $ansiReset               |
                  > |$ansiBlue   2) Update existing company $ansiReset            |
                  > |$ansiBlue   3) Delete existing company $ansiReset            |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   4) Back $ansiReset                               |
                  > $ansiCyan--------------------------------------------$ansiReset
         >$ansiGreen ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> addCompany()
            2 -> updateCompany()
            3 -> deleteCompany()
            4 -> runMenu()
            else -> println("Invalid option entered: $option")
        }
}

/**
 * Prints out a menu which deals with the VideoGame functions
 * Also takes in an Int from the user to determine which function they wish to perform
 */
fun listGamesMenu() {
    val option = ScannerInput.readNextInt(
        """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) Create new video game   $ansiReset               |
                  > |$ansiBlue   2) Update existing video game $ansiReset            |
                  > |$ansiBlue   3) Delete existing video game $ansiReset            |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   4) Back $ansiReset                               |
                  > $ansiCyan--------------------------------------------$ansiReset
         >$ansiGreen ==>> """.trimMargin(">")
    )

    when (option) {
        1 -> addVideoGameToCompany()
        2 -> updateVideoGameInCompany()
        3 -> deleteVideoGame()
        4 -> runMenu()
        else -> println("Invalid option entered: $option")
    }
}

/**
 * Prints out a menu which displays functions and statistics based on the data loaded in the system
 * Also takes in an Int from the user to determine which function they wish to perform
 */
fun listStatsMenu() {
    val option = ScannerInput.readNextInt(
        """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiRed No. of companies: ${companyAPI.numberOfCompanies()} $ansiReset                     |
                  > |$ansiRed Total employees: ${companyAPI.totalEmployees()} $ansiReset                  |
                  > |$ansiRed Average employees per company: ${companyAPI.totalEmployees()/companyAPI.numberOfCompanies()} $ansiReset     |
                  > |$ansiRed Average revenue per company: ${companyAPI.averageRevenue()} $ansiReset     |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) List all companies + games   $ansiReset       |
                  > |$ansiBlue   2) List companies formed before 2000 $ansiReset  |
                  > |$ansiBlue   3) List companies formed after 2000 $ansiReset   |
                  > |$ansiBlue   4) List companies 100,000+ in revenue $ansiReset |
                  > |$ansiBlue   5) List companies with 5,000+ employees$ansiReset|
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   0) Back $ansiReset                               |
                  > $ansiCyan--------------------------------------------$ansiReset
         >$ansiGreen ==>> """.trimMargin(">")
    )

    when (option) {
        1 -> listAllCompanies()
        2 -> listAllBefore()
        3 -> listAllAfter()
        4 -> listOverRevenue()
        5 -> listOverEmployees()
        0 -> runMenu()
        else -> println("Invalid option entered: $option")
    }
}

/**
 * Takes in the values entered by the user then creates a Company object which is then added to the companies ArrayList
 */
fun addCompany(){

    val companyName = ScannerInput.readNextLine("Enter the company name: ")
    val annualRevenue = ScannerInput.readNextInt("Enter the company's annual revenue: ")
    val foundingYear = ScannerInput.readNextInt("Enter the company's founding year: ")
    val numOfEmployees = ScannerInput.readNextInt("Enter the number of employees working there: ")
    val isAdded = companyAPI.addCompany(Company(companyName =  companyName,
        annualRevenue =  annualRevenue,
        foundingYear =  foundingYear,
        numOfEmployees = numOfEmployees))

    if (isAdded) println("Created Successfully")
    else println("Create Failed")
}

/**
 * User selects a Company object and then enters new data which overrides the existing data of the Company object
 */
fun updateCompany() {
    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        val id = ScannerInput.readNextInt("Enter the index of the company you wish to update: ")
        if (companyAPI.findCompany(id) != null) {
            val companyName = ScannerInput.readNextLine("Enter the new company name: ")
            val annualRevenue = ScannerInput.readNextInt("Enter the new annual revenue: ")
            val foundingYear = ScannerInput.readNextInt("Enter the new founding year: ")
            val numOfEmployees = ScannerInput.readNextInt("Enter the new employee number count: ")

            if (companyAPI.updateCompany(id, Company(0, companyName, annualRevenue, foundingYear, numOfEmployees))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

/**
 * User selects a Company object which is then removed from the companies ArrayList
 */
fun deleteCompany() {
    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = ScannerInput.readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val companyToDelete = companyAPI.deleteCompany(id)
        if (companyToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

/**
 * Takes in the values entered by the user then creates a VideoGame object which is then
 * added to the games ArrayList in the Company model
 */
fun addVideoGameToCompany() {
    val company: Company? = askUserToChooseCompany()
    if (company != null) {
        if (company.addVideoGame(VideoGame(title = ScannerInput.readNextLine("Enter the video game title: "),
            platform = ScannerInput.readNextLine("Enter the game's platform: "),
            genre = ScannerInput.readNextLine("Enter the game's genre: "),
            budget = ScannerInput.readNextInt("Enter the game's budget: "),
            profit = ScannerInput.readNextInt("Enter the game's profits: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

/**
 * User selects a VideoGame object and then enters the new data which then overrides the existing VideoGame object data
 */
fun updateVideoGameInCompany() {
    val company: Company? = askUserToChooseCompany()
    if (company != null) {
        val game: VideoGame? = askUserToChooseVideoGame(company)
        if (game != null) {
            val title = ScannerInput.readNextLine("Enter the video game title: ")
            val platform = ScannerInput.readNextLine("Enter the game's platform: ")
            val genre = ScannerInput.readNextLine("Enter the game's genre: ")
            val budget = ScannerInput.readNextInt("Enter the game's budget: ")
            val profit = ScannerInput.readNextInt("Enter the game's profits: ")
            if (company.update(game.gameId, VideoGame(title = title,
                platform = platform,
                genre = genre,
                budget = budget,
                profit = profit))) {
                println("Game contents updated")
            } else {
                println("Game contents NOT updated")
            }
        } else {
            println("Invalid gameId")
        }
    }
}

/**
 * User selects a VideoGame object which is then removed from the games ArrayList in the Company model
 */
fun deleteVideoGame() {
    val company: Company? = askUserToChooseCompany()
    if (company != null) {
        val game: VideoGame? = askUserToChooseVideoGame(company)
        if (game != null) {
            val isDeleted = company.delete(game.gameId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

/**
 * Prints the string of companies returned by the listAllCompanies method
 */
fun listAllCompanies() = println(companyAPI.listAllCompanies())

/**
 * Prints the string of companies who were founded before 2000
 */
fun listAllBefore() = println(companyAPI.listAllBefore())

/**
 * Prints the string of companies who were founded after 2000
 */
fun listAllAfter() = println(companyAPI.listAllAfter())

/**
 * Prints the string of companies who have a revenue of over 100,000
 */
fun listOverRevenue() = println(companyAPI.listOverRevenue())

/**
 * Prints the string of companies who have over 5,000 employees
 */
fun listOverEmployees() = println(companyAPI.listOverEmployees())

/**
 * Asks the user to select a company so Company model methods can be performed on the games ArrayList in the Company model
 *
 * @return chosen Company object or null
 */
fun askUserToChooseCompany(): Company? {
    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        val company = companyAPI.findCompany(ScannerInput.readNextInt("\nEnter the index of the company: "))
        if (company != null) return company
    } else {
        println("Company index is not valid")
    }
    return null
}

/**
 * Asks the user to choose a VideoGame object from a Company object which is then returned
 *
 * @param company object selected
 * @return VideoGame object from the Company object selected
 */
fun askUserToChooseVideoGame(company: Company): VideoGame? {
    return if (company.numberOfGames() > 0) {
        print(company.listVideoGames())
        company.findOne(ScannerInput.readNextInt("\nEnter the id of the video game: "))
    }
    else{
        println ("No video game for chosen company")
        null
    }
}

/**
 * Writes the data in the system to an external xml file
 */
fun save() {
    try {
        companyAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

/**
 * Reads the data from an external xml file and put it into memory
 */
fun load() {
    try {
        companyAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

/**
 * Closes the console app
 */
fun exitApp(){
    println("Closing app")
    exitProcess(0)
}

/**
 * Starts the console app
 */
fun main(args: Array<String>){
    runMenu()
}