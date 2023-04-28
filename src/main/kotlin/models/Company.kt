package models

import utils.Utilities

/**
 * Company Model Object & VideoGame Model Object Controller
 *
 * This class is responsible for defining the Company object constructor parameters
 * along with managing the methods responsible for interacting with the VideoGame model
 * as VideoGame objects are stored in the games MutableSet ArrayList
 *
 * @param companyName, annual revenue, founding year, number of employees, and a MutableSet ArrayList for the VideoGame models
 */
data class Company(
    var companyId: Int = 0,
    var companyName: String,
    var annualRevenue: Int,
    var foundingYear: Int,
    var numOfEmployees: Int,
    var games: MutableSet<VideoGame> = mutableSetOf()
) {

    private var lastGameId = 0
    private fun getGameId() = lastGameId++

    /**
     * Adds a VideoGame object to the games ArrayList
     * @param videoGame; data entered by the user
     * @return determines successful add (true) or unsuccessful add (false)
     */
    fun addVideoGame(videoGame: VideoGame): Boolean {
        videoGame.gameId = getGameId()
        return games.add(videoGame)
    }

    /**
     * Counts the amount of VideoGame objects in the games ArrayList
     */
    fun numberOfGames() = games.size

    /**
     * Finds a VideoGame object based in the id entered by the user
     * @param id entered by the user
     * @return VideoGame object or null
     */
    fun findOne(id: Int): VideoGame? {
        return games.find { videoGame -> videoGame.gameId == id }
    }

    /**
     * Deletes a VideoGame object from the games ArrayList based on the id entered by the user
     * @param id entered by the user
     * @return Boolean to determine successful delete (true) or unsuccessful delete (false)
     */
    fun delete(id: Int): Boolean {
        return games.removeIf { videoGame -> videoGame.gameId == id }
    }

    /**
     * Updates an existing VideoGame object with the new data entered by the user
     * @param id entered by the user, which gets the VideoGame object
     * @return Boolean to determine successful update (true) or unsuccessful update (false)
     */
    fun update(id: Int, newGame: VideoGame): Boolean {
        val foundGame = findOne(id)

        if (foundGame != null) {
            foundGame.title = newGame.title
            foundGame.platform = newGame.platform
            foundGame.genre = newGame.genre
            foundGame.budget = newGame.budget
            foundGame.profit = newGame.profit
            return true
        }
        return false
    }

    /**
     * Lists all the VideoGame objects in the games ArrayList
     */
    fun listVideoGames() =
        if (games.isEmpty()) "\tNO GAMES ADDED"
        else Utilities.formatSetString(games)

    /**
     * Overrides the toString() to accommodate the new VideoGame object listing
     * @return String to be printed out by Main.kt
     */
    override fun toString(): String {
        return "$companyId: $companyName, $annualRevenue, $foundingYear, $numOfEmployees \n${listVideoGames()}"
    }
}
