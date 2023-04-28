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

fun mainMenu() : Int {
    return ScannerInput.readNextInt(
        """ 
         > $ansiCyan--------------------------------------------$ansiReset
         > |       $ansiRed Video Game Review System $ansiReset         |
         > | $ansiRed $ansiReset                                        |
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

fun listStatsMenu() {
    val option = ScannerInput.readNextInt(
        """
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiRed No. of companies: ${companyAPI.numberOfCompanies()} $ansiReset                     |
                  > |$ansiRed Total employees: ${companyAPI.totalEmployees()} $ansiReset                     |
                  > |$ansiRed Average employees per company: ${companyAPI.totalEmployees()/companyAPI.numberOfCompanies()} $ansiReset                     |
                  > |$ansiRed Average revenue per company: ${companyAPI.averageRevenue()} $ansiReset                     |
                  > $ansiCyan--------------------------------------------$ansiReset
                  > |$ansiBlue   1) List all companies + games   $ansiReset               |
                  > |$ansiBlue   2) List companies formed before 2000 $ansiReset            |
                  > |$ansiBlue   3) List companies formed after 2000 $ansiReset            |
                  > |$ansiBlue   4) List companies above 100,000 in revenue $ansiReset            |
                  > |$ansiBlue   5) List companies with over 5,000 employees $ansiReset            |
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

private fun addVideoGameToCompany() {
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

fun listAllCompanies() = println(companyAPI.listAllCompanies())

fun listAllBefore() = println(companyAPI.listAllBefore())

fun listAllAfter() = println(companyAPI.listAllAfter())

fun listOverRevenue() = println(companyAPI.listOverRevenue())

fun listOverEmployees() = println(companyAPI.listOverEmployees())

private fun askUserToChooseCompany(): Company? {
    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        val company = companyAPI.findCompany(ScannerInput.readNextInt("\nEnter the index of the company: "))
        if (company != null) return company
    } else {
        println("Company index is not valid")
    }
    return null
}

private fun askUserToChooseVideoGame(company: Company): VideoGame? {
    return if (company.numberOfGames() > 0) {
        print(company.listVideoGames())
        company.findOne(ScannerInput.readNextInt("\nEnter the id of the video game: "))
    }
    else{
        println ("No video game for chosen company")
        null
    }
}

fun save() {
    try {
        companyAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        companyAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("Closing app")
    exitProcess(0)
}

fun main(args: Array<String>){
    runMenu()
}