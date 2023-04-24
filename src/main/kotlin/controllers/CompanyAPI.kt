package controllers

import models.Company

class CompanyAPI {
    private var companies =ArrayList<Company>()

    fun addCompany(company: Company): Boolean = companies.add(company)

    fun deleteCompany(indexToDelete: Int): Company? =
        if (isValidListIndex(indexToDelete, companies)) companies.removeAt(indexToDelete)
        else null

    fun updateCompany(indexToUpdate: Int, company: Company): Boolean {
        val foundCompany = findCompany(indexToUpdate)
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

    private fun formatListString(notesToFormat : List<Company>) : String =
        notesToFormat
            .joinToString (separator = "\n") { company ->
                companies.indexOf(company).toString() + ": " + company.toString() }
}