package presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import presentation.components.gameScreen.GameScreen
import presentation.components.StartScreen
import presentation.components.TutorialScreen

@Composable
fun GameContainer(){

    val viewModel = viewModel { GameViewModel() }
    val gameState by viewModel.gameState.collectAsState()

    AnimatedVisibility(
        visible = gameState.showingStartScreen,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { -it })
                + fadeOut(animationSpec = tween(150))

    ){
        StartScreen(
            modifier = Modifier.fillMaxSize(),
            gameState = gameState,
            setupGame = viewModel::setupGame,
            startGame = viewModel::startGame,
            showTutorial = viewModel::showTutorial
        )
    }

    AnimatedVisibility(
        visible = gameState.showingGameScreen,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ){
        GameScreen(
            modifier = Modifier.fillMaxSize(),
            gameState = gameState,
            onCardFlipped = viewModel::playerFlipCardUp,
            onRestart = viewModel::restartGame,
            onReturnToMenu = viewModel::returnToMenu,
        )
    }

    AnimatedVisibility(
        visible = gameState.showingTutorialScreen,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        TutorialScreen(
            modifier = Modifier.fillMaxSize(),
            gameState = gameState,
            onRestart = viewModel::restartGame,
            onReturnToMenu = viewModel::returnToMenu,
            dismissTutorial = viewModel::dismissTutorial
        )
    }

    AnimatedVisibility(
        visible = gameState.showingTransitionScreen,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { -it })
                + fadeOut(animationSpec = tween(150))

    ){
        TransitionScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun TransitionScreen(modifier: Modifier) {
    Box(modifier = modifier){

    }
}
