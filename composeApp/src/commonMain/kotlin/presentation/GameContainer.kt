package presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import presentation.components.GameScreen
import presentation.components.StartScreen

@Composable
fun GameContainer(){

    val viewModel = viewModel { GameViewModel() }
    val gameState by viewModel.gameState.collectAsState()

    AnimatedVisibility(
        visible = gameState.showStartScreen,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { -it })
                + fadeOut(animationSpec = tween(150))

    ){
        StartScreen(
            modifier = Modifier.fillMaxSize(),
            gameState = gameState,
            setupGame = viewModel::setupGame,
            startGame = viewModel::startGame
        )
    }

    AnimatedVisibility(
        visible = gameState.showGameScreen,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ){
        GameScreen(
            modifier = Modifier.fillMaxSize(),
            gameState = gameState,
            onCardFlipped = viewModel::userFlipCardUp
        )
    }
}