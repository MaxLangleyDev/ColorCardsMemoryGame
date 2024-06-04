package model

import androidx.compose.ui.graphics.Color

data class GameState(

    val cards: List<CardState> = listOf(),
    val colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow),
    val colorToFind: Color = Color(0x000000),
    val points: Int = 0,

    val showStartScreen: Boolean = true,
    val showGameScreen: Boolean = false,

    val phasePregame: Boolean = false,
    val phaseGame: Boolean = false,
    val phaseEnd: Boolean = false,

    var pregameCountdown: Float = 1f,

    val cardsSelectable: Boolean = false,

    )