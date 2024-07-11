package model

import androidx.compose.ui.graphics.Color

data class GameState(

    val cards: List<CardState> = listOf(),
    val colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow),
    val colorsToFind: List<Color> = listOf(),
    val targetColor: Color = Color(0x000000),
    val points: Int = 0,
    val lastPointsChange: Int = 0,
    val totalCards: Int = 12,
    val correctChoices: Int = 0,

    val currentCardIndex: Int = 0,

    val consecutiveFails: Int = 0,
    val consecutiveMatches: Int = 0,

    val showingStartScreen: Boolean = true,
    val showingGameScreen: Boolean = false,
    val showingTransitionScreen: Boolean = false,
    val showingGameLostScreen: Boolean = false,
    val showingGameWonScreen: Boolean = false,
    val showingTutorialScreen: Boolean = false,
    val showingSettingsScreen: Boolean = false,


    val setupPhase: Boolean = true,
    val pregamePhase: Boolean = false,
    val gamePhase: Boolean = false,
    val endPhase: Boolean = false,

    val gameWon: Boolean = false,
    val gameLost: Boolean = false,
    val threeStrikes: Boolean = false,
    val timedOut: Boolean = false,
    val perfectGame: Boolean = true,

    var pregameCountdownProgress: Float = 1f,
    var gameCountdownProgress: Float = 1f,

    // Settings
    val pregameCountdownDuration: Int = 3,
    val gameCountdownDuration: Int = 15,

    // Cheats
    val showColorOnBack: Boolean = true,


    )