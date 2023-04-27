package models

import utils.Utilities

data class Company(
    var companyId: Int = 0,
    var companyName: String,
    var annualRevenue: Int,
    var foundingYear: Int,
    var numOfEmployees: Int,
    var games : MutableSet<VideoGame> = mutableSetOf()) {

    private var lastGameId = 0
    private fun getGameId() = lastGameId++

    fun addVideoGame(videoGame: VideoGame) : Boolean {
        videoGame.gameId = getGameId()
        return games.add(videoGame)
    }

    fun numberOfGames() = games.size

    fun findOne(id: Int): VideoGame?{
        return games.find{ videoGame -> videoGame.gameId == id }
    }

    fun delete(id: Int): Boolean {
        return games.removeIf { videoGame -> videoGame.gameId == id}
    }

    fun update(id: Int, newGame : VideoGame): Boolean {
        val foundGame = findOne(id)

        if (foundGame != null){
            foundGame.title = newGame.title
            foundGame.platform = newGame.platform
            foundGame.genre = newGame.genre
            foundGame.budget = newGame.budget
            foundGame.profit = newGame.profit
            return true
        }
        return false
    }

    fun listVideoGames() =
        if (games.isEmpty())  "\tNO GAMES ADDED"
        else  Utilities.formatSetString(games)

    override fun toString(): String {
        return "$companyName: $annualRevenue, $foundingYear, $numOfEmployees \n${listVideoGames()}"
    }
}