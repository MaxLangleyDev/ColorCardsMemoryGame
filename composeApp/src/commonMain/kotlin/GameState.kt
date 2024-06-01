import androidx.compose.ui.graphics.Color

data class GameState(

    val cards: List<GameCard> = listOf(),
    val colorToFind: Color = Color(0x000000),
    val points: Int = 0,

    val showStartScreen: Boolean = true,
    val showGameScreen: Boolean = false,

    val cardsSelectable: Boolean = false,

    )