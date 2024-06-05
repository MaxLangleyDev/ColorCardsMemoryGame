package presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.GameState

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    gameState: GameState,
    onCardFlipped: (Int) -> Unit,
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit,
    dismissTutorial: () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GameDetailsBar(modifier = Modifier.weight(0.15f), gameState = gameState)

        GameBoard(
            modifier = Modifier.weight(0.85f),
            rows = rows,
            columns = columns,
            gameState = gameState,
            onCardFlipped = onCardFlipped
        )
    }

    AnimatedVisibility(
        visible = gameState.gameWon,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        WinScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() }
        )
    }

    AnimatedVisibility(
        visible = gameState.gameLost,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        LoseScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() }
        )
    }

    AnimatedVisibility(
        visible = gameState.showingTutorialMessage,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        TutorialScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() },
            dismissTutorial = { dismissTutorial() }
        )
    }
}