package controllers

import models.Company
import persistence.Serializer
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

    fun findCompany(index: Int): Company? =
        if (isValidListIndex(index, companies)) companies[index]
        else null

    fun numberOfCompanies(): Int = companies.size

    fun isValidIndex(index: Int): Boolean = isValidListIndex(index, companies)

    @Throws(Exception::class)
    fun load() {
        companies = serializer.read() as ArrayList<Company>
    }

    @Throws(Exception::class)
    fun store() = serializer.write(companies)
}