import utils.ScannerInput
import kotlin.system.exitProcess
import controllers.CompanyAPI
import models.Company
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
                  > |$ansiRed No. of companies: $ansiReset                       |
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
            4 -> mainMenu()
            else -> println("Invalid option entered: $option")
        }
}

fun addCompany(){

    val companyName = ScannerInput.readNextLine("Enter the company name: ")
    val annualRevenue = ScannerInput.readNextInt("Enter the company's annual revenue: ")
    val foundingYear = ScannerInput.readNextInt("Enter the company's founding year: ")
    val numOfEmployees = ScannerInput.readNextInt("Enter the number of employees working there: ")
    val isAdded = companyAPI.addCompany(Company(companyName, annualRevenue, foundingYear, numOfEmployees))

    if (isAdded) println("Created Successfully")
    else println("Create Failed")
}

fun listAllCompanies() = println(companyAPI.listAllCompanies())

fun updateCompany() {

    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        val indexToUpdate = ScannerInput.readNextInt("Enter the index of the company you wish to update: ")
        if (companyAPI.isValidIndex(indexToUpdate)) {
            val companyName = ScannerInput.readNextLine("Enter the new company name: ")
            val annualRevenue = ScannerInput.readNextInt("Enter the new annual revenue: ")
            val foundingYear = ScannerInput.readNextInt("Enter the new founding year: ")
            val numOfEmployees = ScannerInput.readNextInt("Enter the new employee number count: ")

            if (companyAPI.updateCompany(indexToUpdate, Company(companyName, annualRevenue, foundingYear, numOfEmployees))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no companies for this index number")
        }
    }
}

fun deleteCompany(){

    listAllCompanies()
    if (companyAPI.numberOfCompanies() > 0) {
        val indexToDelete = ScannerInput.readNextInt("Enter the index of the company you wish to delete: ")
        if (companyAPI.isValidIndex(indexToDelete)){
            val companyToDelete = companyAPI.deleteCompany(indexToDelete)
            if (companyToDelete != null) {
                println("Delete Successful! Deleted company: ${companyToDelete.companyName}")
            } else {
                println("Delete unsuccessful")
            }
        } else {
            println("There are no companies for this index number")
        }
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