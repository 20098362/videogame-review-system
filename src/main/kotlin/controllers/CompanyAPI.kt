package controllers

import models.Company
import models.VideoGame
import persistence.Serializer
import utils.ScannerInput
import utils.Utilities.formatListString
import utils.Utilities.isValidListIndex

class CompanyAPI (serializerType: Serializer){

    private var serializer: Serializer = serializerType

    private var companies =ArrayList<Company>()

    private var lastId = 0
    private fun getId() = lastId++

    fun addCompany(company: Company): Boolean{
        company.companyId = getId()
        return companies.add(company)
    }

    fun deleteCompany(id: Int) = companies.removeIf { company -> company.companyId == id }

    fun updateCompany(id: Int, company: Company): Boolean {
        val foundCompany = findCompany(id)
        if ((foundCompany != null) && (company != null)) {
            foundCompany.companyName = company.companyName
            foundCompany.annualRevenue = company.annualRevenue
            foundCompany.foundingYear = company.foundingYear
            foundCompany.numOfEmployees = company.numOfEmployees
            return true
        }
        return false
    }

    fun listAllCompanies(): String =
        if  (companies.isEmpty()) "No companies stored"
        else formatListString(companies)

    fun listAllBefore(): String =
        if  (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.foundingYear < 2000})

    fun listAllAfter(): String =
        if  (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.foundingYear >= 2000})

    fun listOverRevenue(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.annualRevenue >=100000 })

    fun listOverEmployees(): String =
        if (numberOfCompanies() == 0) "No companies stored"
        else formatListString(companies.filter { company -> company.numOfEmployees >=5000 })

    fun findCompany(index: Int): Company? =
        if (isValidListIndex(index, companies)) companies[index]
        else null

    fun numberOfCompanies(): Int = companies.size

    fun totalEmployees(): Int {
        var employeeCount = 0
        for(obj in companies){
            employeeCount += obj.numOfEmployees
        }
        return employeeCount
    }

    fun averageRevenue(): Int {
        var count = 0
        for (obj in companies){
            count += obj.annualRevenue
        }
        return count/numberOfCompanies()
    }

    @Throws(Exception::class)
    fun load() {
        companies = serializer.read() as ArrayList<Company>
    }

    @Throws(Exception::class)
    fun store() = serializer.write(companies)
}