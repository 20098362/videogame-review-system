package models

data class VideoGame(
    var gameId: Int = 0,
    var title: String,
    var platform: String,
    var genre: String,
    var budget: Int,
    var profit: Int
){
}
