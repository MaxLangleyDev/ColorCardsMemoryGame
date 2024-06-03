import androidx.compose.ui.graphics.Color

data class CardState(
    val cardNumber: Int = -1,
    val cardName: String = "",
    val color: Color = Color.Red,
    val isFlipped: Boolean = false,
    val isCorrect: Boolean = false,
    val isSelectable: Boolean = true,
    val isSelected: Boolean = false
)