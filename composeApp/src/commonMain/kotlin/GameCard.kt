import androidx.compose.ui.graphics.Color

data class GameCard(
    val cardNumber: Int = -1,
    val cardName: String = "",
    val color: Color = Color.Red,
    val isFlipped: Boolean = false
)