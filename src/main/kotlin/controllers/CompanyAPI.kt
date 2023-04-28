package controllers

import models.Company
import persistence.Serializer
import utils.Utilities.formatListString
import utils.Utilities.isValidListIndex

/**
 * Model controller for Company model
 *
 * This class is responsible for generating, deleting, updating, counting, and listing Company objects
 *
 * @param serializerType to allow persistence
 */
class CompanyAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType

    private var companies = ArrayList<Company>()

    private var lastId = 0

    private fun getId() = lastId++

    /**
     * Adds a new Company object to the companies ArrayList
     * @param company object details entered by the user
     * @return boolean to determine a success (true) or fail (false)
     */
    fun addCompany(company: Company): Boolean {
        company.companyId = getId()
        return companies.add(company)
    }

    /**
     * Deletes an existing Company object from the companies ArrayList
     * @param id entered by the user
     */
    fun deleteCompany(id: Int) = companies.removeIf { company -> company.companyId == id }

    /**
     * Updates an existing Company object with newly inputted data
     * @param id of the chosen Company object
     * @return boolean to determine a success (true) or fail (false)
     */
    fun updateCompany(id: Int, company: Company): Boolean {
        val foundCompany = findCompany(id)
        if (foundCompany != null) {
            foundCompany.companyName = company.companyName
            foundCompany.annualRevenue = company.annualRevenue
            foundCompany.foundingYear = company.foundingYear
            foundCompany.numOfEmployees = company.numOfEmployees
            return true
        }
        return false
    }

    /**
     * Lists all the Company objects
     * @return String to be printed in Main.kt
     */
    fun listAllCompanies(): String =
        if (companies.isEmpty()) "No companies stored"
        else formatListString(companies)

    /**
     * Lists all the Company objects where the founding date is before 2000
     * @return String to be printed in Main.kt
     */
    fun listAllBefore(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.foundingYear < 2000 })

    /**
     * Lists all the Company objects where the founding date is after 2000
     * @return String to be printed in Main.kt
     */
    fun listAllAfter(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.foundingYear >= 2000 })

    /**
     * Lists all the Company objects where the annual revenue is over 100,000
     * @return String to be printed in Main.kt
     */
    fun listOverRevenue(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.annualRevenue >= 100000 })

    /**
     * Lists all the Company objects where the number of employees is over 5000
     * @return String to be printed in Main.kt
     */
    fun listOverEmployees(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.numOfEmployees >= 5000 })

    /**
     * Finds the Company object as entered by the user via the index number
     * @param index entered by user
     * @return chosen Company object or null
     */
    fun findCompany(index: Int): Company? =
        if (isValidListIndex(index, companies)) companies[index]
        else null

    /**
     * Counts the amount of objects in the companies ArrayList
     * @return number of objects in the companies ArrayList
     */
    fun numberOfCompanies(): Int = companies.size

    /**
     * Counts the number of employees using the numOfEmployees data of each Company object
     * @return total number of employees counted in the system
     */
    fun totalEmployees(): Int {
        var employeeCount = 0
        for (obj in companies) {
            employeeCount += obj.numOfEmployees
        }
        return employeeCount
    }

    /**
     * Averages out the revenue data of each Company object
     * @return total annual revenue of each Company object / the number of Company objects
     */
    fun averageRevenue(): Int {
        var count = 0
        for (obj in companies) {
            count += obj.annualRevenue
        }
        return count / numberOfCompanies()
    }

    /**
     * Reads the XML file containing the system data
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(Exception::class)
    fun load() {
        companies = serializer.read() as ArrayList<Company>
    }

    /**
     * Writes to the XML file to store the existing data
     */
    @Throws(Exception::class)
    fun store() = serializer.write(companies)
}
