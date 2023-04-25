package controllers

import models.Company
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class CompanyAPITest {

    private var eaSports: Company? = null
    private var blizzard: Company? = null
    private var steam: Company? = null
    private var epicGames: Company? = null
    private var concernedApe: Company? = null

    private var populatedCompanies: CompanyAPI? = CompanyAPI()
    private var emptyCompanies: CompanyAPI? = CompanyAPI()

    @BeforeEach
    fun setup(){
        eaSports = Company("EA Sports", 100000, 1990, 2000)
        blizzard = Company("Blizzard Entertainment", 150000, 1989, 1000)
        steam = Company("Steam", 500000, 2000, 5000)
        epicGames = Company("Epic Games", 350000, 2010, 1500)
        concernedApe = Company("Concerned Ape", 50000, 2012, 500)

        populatedCompanies!!.addCompany(eaSports!!)
        populatedCompanies!!.addCompany(blizzard!!)
        populatedCompanies!!.addCompany(steam!!)
        populatedCompanies!!.addCompany(epicGames!!)
        populatedCompanies!!.addCompany(concernedApe!!)
    }

    @AfterEach
    fun tearDown(){
        eaSports = null
        blizzard = null
        steam = null
        epicGames = null
        concernedApe = null
        populatedCompanies = null
        emptyCompanies = null
    }

    @Nested
    inner class AddCompanies {
        @Test
        fun `adding a Company to a populated list adds to ArrayList`() {
            val newCompany = Company("Konami", 75000, 1999, 8000)
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            assertTrue(populatedCompanies!!.addCompany(newCompany))
            assertEquals(6, populatedCompanies!!.numberOfCompanies())
            assertEquals(newCompany, populatedCompanies!!.findCompany(populatedCompanies!!.numberOfCompanies() - 1))
        }

        @Test
        fun `adding a Company to an empty list adds to ArrayList`() {
            val newCompany = Company("Konami", 75000, 1999, 8000)
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.addCompany(newCompany))
            assertEquals(1, emptyCompanies!!.numberOfCompanies())
            assertEquals(newCompany, emptyCompanies!!.findCompany(emptyCompanies!!.numberOfCompanies() - 1))
        }
    }

    @Nested
    inner class ListCompanies {
        @Test
        fun `listAllCompanies returns No Companies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.listAllCompanies().lowercase().contains("no companies"))
        }

        @Test
        fun `listAllCompanies returns companies when ArrayList has companies stored`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            val companiesString = populatedCompanies!!.listAllCompanies().lowercase()
            assertTrue(companiesString.contains("ea sports"))
            assertTrue(companiesString.contains("blizzard entertainment"))
            assertTrue(companiesString.contains("steam"))
            assertTrue(companiesString.contains("epic games"))
            assertTrue(companiesString.contains("concerned ape"))
        }
    }

    @Nested
    inner class DeleteCompanies {

        @Test
        fun `deleting a Company that does not exist, returns null`() {
            assertNull(emptyCompanies!!.deleteCompany(0))
            assertNull(populatedCompanies!!.deleteCompany(-1))
            assertNull(populatedCompanies!!.deleteCompany(5))
        }

        @Test
        fun `deleting a Company that exists delete and returns deleted object`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            assertEquals(concernedApe, populatedCompanies!!.deleteCompany(4))
            assertEquals(4, populatedCompanies!!.numberOfCompanies())
            assertEquals(eaSports, populatedCompanies!!.deleteCompany(0))
            assertEquals(3, populatedCompanies!!.numberOfCompanies())
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a Company that does not exist returns false`(){
            assertFalse(populatedCompanies!!.updateCompany(6, Company("Updating Company", 1, 1, 1)))
            assertFalse(populatedCompanies!!.updateCompany(-1, Company("Updating Company", 1, 1, 1)))
            assertFalse(emptyCompanies!!.updateCompany(0, Company("Updating Company", 1, 1, 1)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            assertEquals(concernedApe, populatedCompanies!!.findCompany(4))
            assertEquals("Concerned Ape", populatedCompanies!!.findCompany(4)!!.companyName)
            assertEquals(50000, populatedCompanies!!.findCompany(4)!!.annualRevenue)
            assertEquals(2012, populatedCompanies!!.findCompany(4)!!.foundingYear)
            assertEquals(500, populatedCompanies!!.findCompany(4)!!.numOfEmployees)

            assertTrue(populatedCompanies!!.updateCompany(4, Company("Updating Company", 1, 1, 1)))
            assertEquals("Updating Company", populatedCompanies!!.findCompany(4)!!.companyName)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.annualRevenue)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.foundingYear)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.numOfEmployees)
        }
    }
}