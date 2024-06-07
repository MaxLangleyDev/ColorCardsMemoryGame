package model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates


data class CardState(
    val cardNumber: Int = -1,
    val cardName: String = "",
    val color: Color = Color.Red,
    val isFlippedDown: Boolean = false,
    val isCorrect: Boolean = false,
    val isSelectable: Boolean = false,
    val isSelected: Boolean = false,
//    val coordinates: LayoutCoordinates? = null
)