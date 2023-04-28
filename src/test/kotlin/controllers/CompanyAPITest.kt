package controllers

import models.Company
import models.VideoGame
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class CompanyAPITest {

    private var eaSports: Company? = null
    private var blizzard: Company? = null
    private var steam: Company? = null
    private var epicGames: Company? = null
    private var concernedApe: Company? = null

    private var fifa: VideoGame? = null
    private var portal: VideoGame? = null
    private var tf2: VideoGame? = null

    private var populatedCompanies: CompanyAPI? = CompanyAPI(XMLSerializer(File("test-data.xml")))
    private var emptyCompanies: CompanyAPI? = CompanyAPI(XMLSerializer(File("test-data.xml")))

    @BeforeEach
    fun setup() {
        eaSports = Company(0, "EA Sports", 100000, 1990, 2000)
        blizzard = Company(1, "Blizzard Entertainment", 10000, 1989, 1000)
        steam = Company(2, "Steam", 500000, 2000, 5100)
        epicGames = Company(3, "Epic Games", 350000, 2010, 1500)
        concernedApe = Company(4, "Concerned Ape", 50000, 2012, 5500)

        fifa = VideoGame(0, "FIFA 15", "XBox", "Sports", 100000, 90000)
        portal = VideoGame(0, "Portal 2", "PC", "Puzzle", 80000, 100000)
        tf2 = VideoGame(1, "Team Fortress 2", "PC", "FPS", 500000, 200000)

        populatedCompanies!!.addCompany(eaSports!!)
        populatedCompanies!!.addCompany(blizzard!!)
        populatedCompanies!!.addCompany(steam!!)
        populatedCompanies!!.addCompany(epicGames!!)
        populatedCompanies!!.addCompany(concernedApe!!)

        eaSports!!.addVideoGame(fifa!!)
        steam!!.addVideoGame(portal!!)
        steam!!.addVideoGame(tf2!!)
    }

    @AfterEach
    fun tearDown() {
        eaSports = null
        blizzard = null
        steam = null
        epicGames = null
        concernedApe = null
        fifa = null
        populatedCompanies = null
        emptyCompanies = null
    }

    @Nested
    inner class AddCompanies {
        @Test
        fun `adding a Company to a populated list adds to ArrayList`() {
            val newCompany = Company(5, "Spire", 75000, 1999, 8000)
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            assertTrue(populatedCompanies!!.addCompany(newCompany))
            assertEquals(6, populatedCompanies!!.numberOfCompanies())
            assertEquals(newCompany, populatedCompanies!!.findCompany(populatedCompanies!!.numberOfCompanies() - 1))
        }

        @Test
        fun `adding a Company to an empty list adds to ArrayList`() {
            val newCompany = Company(0, "Spire", 75000, 1999, 8000)
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
            assertFalse(emptyCompanies!!.deleteCompany(0))
            assertFalse(populatedCompanies!!.deleteCompany(-1))
            assertFalse(populatedCompanies!!.deleteCompany(5))
        }

        @Test
        fun `deleting a Company that exists delete and returns deleted object`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            assertTrue(populatedCompanies!!.deleteCompany(4))
            assertEquals(4, populatedCompanies!!.numberOfCompanies())
            assertTrue(populatedCompanies!!.deleteCompany(0))
            assertEquals(3, populatedCompanies!!.numberOfCompanies())
            assertEquals(blizzard, populatedCompanies!!.findCompany(0))
            assertEquals(steam, populatedCompanies!!.findCompany(1))
            assertEquals(epicGames, populatedCompanies!!.findCompany(2))
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a Company that does not exist returns false`() {
            assertFalse(populatedCompanies!!.updateCompany(6, Company(7, "Updating Company", 1, 1, 1)))
            assertFalse(populatedCompanies!!.updateCompany(-1, Company(7, "Updating Company", 1, 1, 1)))
            assertFalse(emptyCompanies!!.updateCompany(0, Company(7, "Updating Company", 1, 1, 1)))
        }

        @Test
        fun `updating a Company that exists returns true and updates`() {
            assertEquals(concernedApe, populatedCompanies!!.findCompany(4))
            assertEquals("Concerned Ape", populatedCompanies!!.findCompany(4)!!.companyName)
            assertEquals(50000, populatedCompanies!!.findCompany(4)!!.annualRevenue)
            assertEquals(2012, populatedCompanies!!.findCompany(4)!!.foundingYear)
            assertEquals(5500, populatedCompanies!!.findCompany(4)!!.numOfEmployees)

            assertTrue(populatedCompanies!!.updateCompany(4, Company(5, "Updating Company", 1, 1, 1)))
            assertEquals("Updating Company", populatedCompanies!!.findCompany(4)!!.companyName)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.annualRevenue)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.foundingYear)
            assertEquals(1, populatedCompanies!!.findCompany(4)!!.numOfEmployees)
        }
    }

    @Nested
    inner class AddVideoGames {
        @Test
        fun `adding a VideoGame to a Company with existing VideoGames`() {

            val getCompany = populatedCompanies!!.findCompany(0)
            val company: Company? = getCompany
            val newVideoGame = VideoGame(1, "The Sims", "PC", "Simulation", 80000, 120000)
            assertEquals(1, company!!.numberOfGames())
            assertTrue(company.addVideoGame(newVideoGame))
            assertEquals(2, company.numberOfGames())
            assertEquals(newVideoGame, company.findOne(company.numberOfGames() - 1))
        }

        @Test
        fun `adding a VideoGame to a Company with no VideoGames`() {
            val getCompany = populatedCompanies!!.findCompany(1)
            val company: Company? = getCompany
            val newVideoGame = VideoGame(0, "Over-watch", "PC", "FPS", 800000, 700000)
            assertEquals(0, company!!.numberOfGames())
            assertTrue(company.addVideoGame(newVideoGame))
            assertEquals(1, company.numberOfGames())
            assertEquals(newVideoGame, company.findOne(company.numberOfGames() - 1))
        }
    }

    @Nested
    inner class ListVideoGames {
        @Test
        fun `listVideoGames returns No VideoGames Stored message when no games present`() {
            val getCompany = populatedCompanies!!.findCompany(1)
            val company: Company? = getCompany
            assertEquals(0, company!!.numberOfGames())
            assertTrue(company.listVideoGames().lowercase().contains("no games"))
        }

        @Test
        fun `listVideoGames returns VideoGames when games are stored`() {
            val getCompany = populatedCompanies!!.findCompany(0)
            val company: Company? = getCompany
            assertEquals(1, company!!.numberOfGames())
            val gameString = company.listVideoGames().lowercase()
            assertTrue(gameString.contains("fifa"))
        }
    }

    @Nested
    inner class DeleteVideoGames {
        @Test
        fun `deleting a VideoGame that does not exist, returns null`() {
            val getCompany = populatedCompanies!!.findCompany(1)
            val company: Company? = getCompany
            assertFalse(company!!.delete(0))
            assertFalse(company.delete(-1))
            assertFalse(company.delete(5))
        }

        @Test
        fun `deleting a VideoGame that exists delete and returns deleted object`() {
            val getCompany = populatedCompanies!!.findCompany(2)
            val company: Company? = getCompany
            assertEquals(2, company!!.numberOfGames())
            assertTrue(company.delete(1))
            assertEquals(1, company.numberOfGames())
            assertEquals(portal, company.findOne(0))
        }
    }

    @Nested
    inner class UpdateVideoGames {
        @Test
        fun `updating a VideoGame that does not exist returns false`() {
            val getCompany = populatedCompanies!!.findCompany(1)
            val company: Company? = getCompany
            assertFalse(company!!.update(6, VideoGame(7, "Updating Game", "None", "None", 1, 1)))
            assertFalse(company.update(-1, VideoGame(7, "Updating Game", "None", "None", 1, 1)))
            assertFalse(company.update(0, VideoGame(7, "Updating Game", "None", "None", 1, 1)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            val getCompany = populatedCompanies!!.findCompany(0)
            val company: Company? = getCompany
            assertEquals(fifa, company!!.findOne(0))
            assertEquals("FIFA 15", company.findOne(0)!!.title)
            assertEquals("XBox", company.findOne(0)!!.platform)
            assertEquals("Sports", company.findOne(0)!!.genre)
            assertEquals(100000, company.findOne(0)!!.budget)
            assertEquals(90000, company.findOne(0)!!.profit)

            assertTrue(company.update(0, VideoGame(0, "Updating Company", "New", "New", 1, 1)))
            assertEquals("Updating Company", company.findOne(0)!!.title)
            assertEquals("New", company.findOne(0)!!.platform)
            assertEquals("New", company.findOne(0)!!.genre)
            assertEquals(1, company.findOne(0)!!.budget)
            assertEquals(1, company.findOne(0)!!.profit)
        }
    }

    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingCompanies = CompanyAPI(XMLSerializer(File("test-data.xml")))
            storingCompanies.store()

            val loadedCompanies = CompanyAPI(XMLSerializer(File("test-data.xml")))
            loadedCompanies.load()

            assertEquals(0, storingCompanies.numberOfCompanies())
            assertEquals(0, loadedCompanies.numberOfCompanies())
            assertEquals(storingCompanies.numberOfCompanies(), loadedCompanies.numberOfCompanies())
        }

        @Test
        fun `saving and loading a loaded collection in XML doesn't lose data`() {

            val storingCompanies = CompanyAPI(XMLSerializer(File("test-data.xml")))
            storingCompanies.addCompany(eaSports!!)
            storingCompanies.addCompany(blizzard!!)
            storingCompanies.addCompany(steam!!)
            storingCompanies.store()

            val loadedCompanies = CompanyAPI(XMLSerializer(File("test-data.xml")))
            loadedCompanies.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingCompanies.numberOfCompanies())
            assertEquals(3, loadedCompanies.numberOfCompanies())
            assertEquals(storingCompanies.numberOfCompanies(), loadedCompanies.numberOfCompanies())
            assertEquals(storingCompanies.findCompany(0), loadedCompanies.findCompany(0))
            assertEquals(storingCompanies.findCompany(1), loadedCompanies.findCompany(1))
            assertEquals(storingCompanies.findCompany(2), loadedCompanies.findCompany(2))
        }
    }

    @Nested
    inner class CountingTests {
        @Test
        fun `total number of employees returns the correct value in a populated array`() {
            assertEquals(15100, populatedCompanies!!.totalEmployees())
        }

        @Test
        fun `average employees counts correctly when counting a populated array`() {
            assertEquals(3020, populatedCompanies!!.totalEmployees() / populatedCompanies!!.numberOfCompanies())
        }

        @Test
        fun `average revenue counts correctly when counting a populated array`() {
            assertEquals(202000, populatedCompanies!!.averageRevenue())
        }
    }

    @Nested
    inner class ListBeforeTests {
        @Test
        fun `listAllBefore returns No Companies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.listAllBefore().lowercase().contains("no companies"))
        }

        @Test
        fun `listAllBefore returns companies when ArrayList has companies stored`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            val companiesString = populatedCompanies!!.listAllBefore().lowercase()
            assertTrue(companiesString.contains("ea sports"))
            assertTrue(companiesString.contains("blizzard entertainment"))
            assertFalse(companiesString.contains("steam"))
            assertFalse(companiesString.contains("epic games"))
            assertFalse(companiesString.contains("concerned ape"))
        }
    }

    @Nested
    inner class ListAfterTests {
        @Test
        fun `listAllAfter returns No Companies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.listAllAfter().lowercase().contains("no companies"))
        }

        @Test
        fun `listAllBefore returns companies when ArrayList has companies stored`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            val companiesString = populatedCompanies!!.listAllAfter().lowercase()
            assertFalse(companiesString.contains("ea sports"))
            assertFalse(companiesString.contains("blizzard entertainment"))
            assertTrue(companiesString.contains("steam"))
            assertTrue(companiesString.contains("epic games"))
            assertTrue(companiesString.contains("concerned ape"))
        }
    }

    @Nested
    inner class ListOverRevenueTests {
        @Test
        fun `listOverRevenue returns No Companies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.listOverRevenue().lowercase().contains("no companies"))
        }

        @Test
        fun `listOverRevenue returns companies when ArrayList has companies stored`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            val companiesString = populatedCompanies!!.listOverRevenue().lowercase()
            assertTrue(companiesString.contains("ea sports"))
            assertFalse(companiesString.contains("blizzard entertainment"))
            assertTrue(companiesString.contains("steam"))
            assertTrue(companiesString.contains("epic games"))
            assertFalse(companiesString.contains("concerned ape"))
        }
    }

    @Nested
    inner class ListOverEmployees {
        @Test
        fun `listOverEmployees returns No Companies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCompanies!!.numberOfCompanies())
            assertTrue(emptyCompanies!!.listOverEmployees().lowercase().contains("no companies"))
        }

        @Test
        fun `listOverEmployees returns companies when ArrayList has companies stored`() {
            assertEquals(5, populatedCompanies!!.numberOfCompanies())
            val companiesString = populatedCompanies!!.listOverEmployees().lowercase()
            assertFalse(companiesString.contains("ea sports"))
            assertFalse(companiesString.contains("blizzard entertainment"))
            assertTrue(companiesString.contains("steam"))
            assertFalse(companiesString.contains("epic games"))
            assertTrue(companiesString.contains("concerned ape"))
        }
    }
}
