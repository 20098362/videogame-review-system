package models

/**
 * VideoGame Model Object
 *
 * This class is responsible for defining the VideoGame model parameters
 *
 * @param title, platform, genre, budget, and profit
 */
data class VideoGame(
    var gameId: Int = 0,
    var title: String,
    var platform: String,
    var genre: String,
    var budget: Int,
    var profit: Int
){
}
