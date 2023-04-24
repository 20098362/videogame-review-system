package models

data class Company(
    var companyName: String,
    var annualRevenue: Int,
    var foundingYear: Int,
    var numOfEmployees: Int
) {
}