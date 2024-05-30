data class GameState(

    val cards: List<GameCard> = listOf(),
    val cardsToFind: List<Int> = listOf(), // List of indices in the cards list,
    val points: Int = 0,

    val gameStarted: Boolean = false,

)