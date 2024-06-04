package model

import androidx.compose.ui.graphics.Color
import model.CardState

data class GameState(

    val cards: List<CardState> = listOf(),
    val colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow),
    val colorToFind: Color = Color(0x000000),
    val points: Int = 0,

    val showStartScreen: Boolean = true,
    val showGameScreen: Boolean = false,

    val cardsSelectable: Boolean = false,

    )