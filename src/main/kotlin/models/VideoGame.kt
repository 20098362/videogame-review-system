package models

data class VideoGame(
    var gameId: Int,
    var title: String,
    var platform: String,
    var genre: String,
    var budget: Int,
    var profit: Int
){
}
