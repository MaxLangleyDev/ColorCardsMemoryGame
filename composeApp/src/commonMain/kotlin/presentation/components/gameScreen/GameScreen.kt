package presentation.components.gameScreen

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
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GameDetailsBar(modifier = Modifier.weight(0.2f), gameState = gameState)

        GameBoard(
            modifier = Modifier.weight(0.8f),
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

}