package model

import androidx.compose.ui.graphics.Color

data class GameState(

    val cards: List<CardState> = listOf(),
    val colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow),
    val colorsToFind: List<Color> = listOf(),
    val targetColor: Color = Color(0x000000),
    val points: Int = 0,

    val consecutiveFails: Int = 0,
    val consecutiveMatches: Int = 0,

    val showingStartScreen: Boolean = true,
    val showingGameScreen: Boolean = false,
    val showingGameLostScreen: Boolean = false,
    val showingGameWonScreen: Boolean = false,

    val pregamePhase: Boolean = false,
    val gamePhase: Boolean = false,
    val endPhase: Boolean = false,

    val gameWon: Boolean = false,
    val gameLost: Boolean = false,

    var pregameCountdown: Float = 1f,
    var gameCountdown: Float = 1f,

    )